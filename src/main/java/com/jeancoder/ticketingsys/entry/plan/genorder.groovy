package com.jeancoder.ticketingsys.entry.plan;

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.order.dto.OrderSeatItemDto
import com.jeancoder.ticketingsys.ready.order.dto.OrderWithSeatDto
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderSeat
import com.jeancoder.ticketingsys.ready.plan.dto.GenorderResultDto
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.dto.SchemaChildItem
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.support.OrderNoHelper
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.HallSeat
import com.piaodaren.ssc.model.HallSeatResult
import com.piaodaren.ssc.model.SscAuthModel
import com.piaodaren.ssc.request.SeatBuy

import groovy.json.JsonSlurper

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	DatabaseSource.getDatabasePower().beginTransaction();
	Long id = req.getLong("cinema_id");
	String plan_id = req.getParameter("plan_id");
	String last_update_time = req.getParameter("last_update_time");
	String seat_ids = req.getParameter("seat_ids");
//	Long schema_item_id = req.getLong("schema_item_id");
	String plan_date = req.getParameter("plan_date");
//	String custom_price = req.getParameter("custom_price");
	String phone_number = req.getParameter("phone_number");
	
	String order_no = OrderNoHelper.gene();
	
	def seat_items = new JsonSlurper().parseText(seat_ids);
	
	println "genorder___String seat_ids _______" + seat_ids;
	println "genorder___String seat_ids _______" + com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper.toJson(seat_items);
	StoreInfo store = StoreService.INSTANCE.getById(id);
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
	
	if(cinemaAuthInfo == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		result.setData(Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR));
		return result;
	}
	
	String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
	String channel_url = cinemaAuthInfo.getAuthChannelUrl()
	String channel_num = cinemaAuthInfo.getAuthChannelNo()
	String channel_code = cinemaAuthInfo.getAuthChannelCode()
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
	
	SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
	Date start_time = datesdf.parse(plan_date);
	Calendar c = Calendar.getInstance(TimeZone.getDefault());
	c.setTime(start_time);
	c.add(Calendar.DATE, 1);
	Date end_time = c.getTime();
	CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
	CinemaPlan matchPlan = null;
	for(CinemaPlan cplan : planResult.getResult()) {
		if(cplan.getId().equals(plan_id)) {
			matchPlan = cplan;
		}
	}
	
	if(matchPlan == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		result.setData(Res.Failed(Codes.COMMON_PARAM_ERROR,"所选日期不存在该排期"));
		return result;
	}
	
	SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
	
	String total_amount = "0";
	List<SeatBuy> seats = new ArrayList<SeatBuy>();
	for(def seat_item : seat_items) {
		SchemaChildItem schemaItem = SchemaService.INSTANCE.getSchemaItemById(Long.valueOf(seat_item["schema_item_id"]));
		String unitPrice = MoneyUtil.multiple(""+schemaItem.getPriceYuan(), "100");
		if(schemaItem.getIs_custom()) {
			unitPrice = MoneyUtil.multiple(String.valueOf(seat_item["custom_price"]), "100");
		}
		
		total_amount = MoneyUtil.add(total_amount, unitPrice);
		
		SeatBuy seat = new SeatBuy();
		seat.setBuy_amount(unitPrice);
		seat.setDiscount(null)
		seat.setHandle_fee("0")
		seat.setSeat_no(String.valueOf(seat_item["seat_id"]))
		seat.setSubmitPrice(unitPrice);
		seats.add(seat);
	}
	
	GenorderResultDto resdata = new GenorderResultDto();
	resdata.setOrderNo(order_no);
	resdata.setTotalAmount(total_amount);
	
	
	SimpleDateFormat clocksdf = new SimpleDateFormat("HH:mm:ss");
	
	DataTcSsSaleOrderInfo order = new DataTcSsSaleOrderInfo();
	order.setOrder_no(order_no);
	order.setTotal_amount(total_amount);
	order.pay_amount = total_amount;
	order.setOrder_status(OrderConstants._order_status_create_);
	order.setStore_id(store.getId().intValue())
	order.setStore_name(store.getStore_name())
	order.setHall_id(matchPlan.getHallId())
	order.setHall_name(matchPlan.getHallName())
	order.setPlan_date(plan_date);
	order.setPlan_time(matchPlan.getStartTime().split(" ")[1])
	order.setPlan_id(plan_id)
	order.setFilm_name(matchPlan.getMovieInfo().get(0).getMovieName())
	order.setTicket_sum(seats.size());
	order.setMobile(phone_number)
	
	OrderService.INSTANCE.addOuUpdateOrder(order);
	
	HallSeatResult hallseats = SssHelper.INSTANCE.getHallSeats(cinemaAuthInfo, matchPlan.getHallId());
	List<HallSeat> matchSeats = new ArrayList<HallSeat>();
	for(def seat_item : seat_items) {
		seatloop:
		for(HallSeat gseat : hallseats.getResult()) {
			if(String.valueOf(seat_item["seat_id"]).equals(gseat.getCineSeatId())) {
				matchSeats.add(gseat);
				break seatloop;
			}
		}
	}
	
	for(HallSeat gseat : matchSeats) {
		def match_seat_item = null;
		for(def seat_item : seat_items) {
			if(gseat.getCineSeatId().equals(String.valueOf(seat_item["seat_id"]))) {
				match_seat_item = seat_item;
			}
		}
		
		SchemaChildItem schemaItem = SchemaService.INSTANCE.getSchemaItemById(Long.valueOf(match_seat_item["schema_item_id"]));
		String unitPrice = MoneyUtil.multiple(""+schemaItem.getPriceYuan(), "100");
		if(schemaItem.getIs_custom()) {
			unitPrice = MoneyUtil.multiple(String.valueOf(match_seat_item["custom_price"]), "100");
		}
		
		//查找最低出票价
		def low_price = matchPlan.lowestPrice;
		def settle_price = matchPlan.settlePrice;
		
		def pub_fee = MoneyUtil.multiple(low_price, '100');
		if(settle_price) {
			pub_fee = MoneyUtil.multiple(settle_price, '100');
		}
		
		DataTcSsSaleOrderSeat seat = new DataTcSsSaleOrderSeat();
		seat.setSeat_gr(gseat.getxCoord())
		seat.setSeat_gc(gseat.getyCoord())
		seat.setSeat_sr(gseat.getRow())
		seat.setSeat_sc(gseat.getColumn())
		seat.setSeat_no(gseat.getCineSeatId())
		seat.setOrder_id(order.getId())
		seat.setSale_fee(unitPrice)
		//seat.setPub_fee(unitPrice)
		seat.setPub_fee(pub_fee);
		seat.setHandle_fee("0")
		OrderService.INSTANCE.addOrUpdateSeat(seat);
	}
	
	String remoteResult = ssc_op.batch_lock(order_no, order_no, cinemaAuthInfo.getCinemaCode(), plan_id, last_update_time, seats);
	
	if(remoteResult == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		result.setData(Res.Failed(Codes.CINEMA_CHOOSE_SEAT_SELLED));
		return result;
	}
	
	DataTcSsSaleOrderRemote remote = new DataTcSsSaleOrderRemote();
	remote.setLock_flag(remoteResult)
	remote.setOrder_id(order.getId())
	OrderService.INSTANCE.addOrUpdateRemote(remote);
	
	DatabaseSource.getDatabasePower().commitTransaction();
	
	def order_data = JackSonBeanMapper.toJson(order);
	println order_data;
	order_data = URLEncoder.encode(order_data);
	order_data = URLEncoder.encode(order_data);
	def tnum = JC.request.param('tnum')?.trim();
	//开始去交易中心注册订单
	SimpleAjax trade = RemoteUtil.connect(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'2000','od':order_data, tnum:tnum]);
	if(!trade.available||trade.data==null) {
		result.setData(Res.Failed(Codes.COMMON_PARAM_ERROR,"交易创建失败，请检查：" + trade.messages.join(",")));
		return result;
	}
	def tradeNum = trade.data?.tnum;
	
	
	//组装返回数据
	OrderWithSeatDto oforder = new OrderWithSeatDto();
	oforder.setId(order.getId())
	oforder.setOrder_no(order.getOrder_no());
	//oforder.setTotal_amount(order.getTotal_amount())
	oforder.setTotal_amount(trade?.data?.total_amount?.toString());
	
	oforder.setOrder_status(order.getOrder_status());
	oforder.setStore_id(order.getStore_id())
	oforder.setStore_name(order.getStore_name())
	oforder.setHall_id(order.getHall_id())
	oforder.setHall_name(order.getHall_name())
	oforder.setPlan_date(order.getPlan_date());
	oforder.setPlan_time(order.getPlan_time())
	oforder.setPlan_id(order.getPlan_id())
	oforder.setFilm_name(order.getFilm_name())
	oforder.setTicket_sum(order.getTicket_sum());
	oforder.setMobile(order.getMobile())
	oforder.setSeats(new ArrayList<OrderSeatItemDto>());
	for(HallSeat gseat : matchSeats) {
		def match_seat_item = null;
		for(def seat_item : seat_items) {
			if(gseat.getCineSeatId().equals(String.valueOf(seat_item["seat_id"]))) {
				match_seat_item = seat_item;
			}
		}
		
		SchemaChildItem schemaItem = SchemaService.INSTANCE.getSchemaItemById(Long.valueOf(match_seat_item["schema_item_id"]));
		String unitPrice = MoneyUtil.multiple(""+schemaItem.getPriceYuan(), "100");
		if(schemaItem.getIs_custom()) {
			unitPrice = MoneyUtil.multiple(String.valueOf(match_seat_item["custom_price"]), "100");
		}
		
		OrderSeatItemDto seat = new OrderSeatItemDto();
		seat.setSeat_gr(gseat.getxCoord())
		seat.setSeat_gc(gseat.getyCoord())
		seat.setSeat_sr(gseat.getRow())
		seat.setSeat_sc(gseat.getColumn())
		seat.setSeat_no(gseat.getCineSeatId())
		seat.setOrder_id(order.getId())
		seat.setSale_fee(unitPrice)
		seat.setPub_fee(unitPrice)
		seat.setHandle_fee("0")
		oforder.getSeats().add(seat)
	}
	
	result.setData(Res.Success([tradeNum, oforder]));
	
	return result;
}catch(Exception e) {
	e.printStackTrace()
	DatabaseSource.getDatabasePower().rollbackTransaction();
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}