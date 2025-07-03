package com.jeancoder.ticketingsys.internal.api
import com.jeancoder.ticketingsys.entry.store.handle_fee

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleRemote
import com.jeancoder.ticketingsys.ready.entity.SaleSeat
import com.jeancoder.ticketingsys.ready.entity.TicketHandleFee
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieFullInfo
import com.jeancoder.ticketingsys.ready.film.service.FilmService
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.order.dto.OrderSeatItemDto
import com.jeancoder.ticketingsys.ready.order.dto.OrderWithSeatDto
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderSeat
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.dto.HallSchemaWithItem
import com.jeancoder.ticketingsys.ready.schema.dto.PlanSchema
import com.jeancoder.ticketingsys.ready.schema.dto.TicketPriceDto
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
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanMovie
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.HallSeat
import com.piaodaren.ssc.model.HallSeatResult
import com.piaodaren.ssc.model.SscAuthModel
import com.piaodaren.ssc.request.SeatBuy

def apid = BigInteger.valueOf(Long.valueOf(JC.internal.param('apid')));
BigInteger user_id = apid;

//根据 apid 查找一下用户信息
JCLogger Logger = JCLoggerFactory.getLogger('genorder');
def pid = JC.internal.param('pid');
def mobile = JC.internal.param('mobile')//获取手机号

Logger.info('mobile_2==========' + mobile);

def order_config_rule = null;
try {
	//在这里获取配置的订单前缀
	SimpleAjax ajax = JC.internal.call(SimpleAjax, 'project', '/sys/get_conf_by_code', [code:'ORNU']);
	
	if(ajax && ajax.available && ajax.data) {
		order_config_rule = (ajax.data);
	}
} catch(any) {
}
try {
	DatabaseSource.getDatabasePower().beginTransaction();
	Long id = Long.valueOf(JC.internal.param("cinema_id"));
	String plan_id = JC.internal.param("plan_id");
	String last_update_time = JC.internal.param("last_update_time");
	String seat_ids = JC.internal.param("seat_ids");
	String plan_date = JC.internal.param("plan_date");

	String order_no = OrderNoHelper.gene();
	def prefix = null; def store_with = false;
	
	if(order_config_rule) {
		for(x in order_config_rule) {
			Logger.info(x['key'] + '---' + x['value']);
			if(x['key']=='prefix') {
				prefix = x['value'];
			} else if(x['key']=='withstore' && x['value'].toString()=='1') {
				store_with = true;
			}
		}
	}

	StoreInfo store = StoreService.INSTANCE.getById(id);
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);

	if(cinemaAuthInfo == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
	}
	
	if(store_with && store.store_no!=null) {
		def store_basic_id = store.store_basic;
		def real_store_info = JC.internal.call('project', '/incall/store_by_id', [id:store_basic_id, pid:pid]);
		try {
			real_store_info = JackSonBeanMapper.jsonToMap(real_store_info);
			order_no = real_store_info['store_no'].trim() + order_no;
		} catch(any) {}
	}
	if(prefix) {
		order_no = prefix.toString().trim() + order_no;
	}
	
	//该影城支持的默认分类
	List<HallSchemaWithItem> cinemaSchemas = SchemaService.INSTANCE.getCinemaSchemas_online(id,pid);

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
			break;
		}
	}

	if(matchPlan == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		return Res.Failed(Codes.COMMON_PARAM_ERROR,"所选日期不存在该排期");
	}

	SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
	def halls = ssc_op.get_cinema_hall_list(cinemaAuthInfo.getCinemaCode());

	for(x in halls.getResult()) {
		if(matchPlan.hallId==x.id) {
			matchPlan.hallName = x.name;
		}
	}

	HallSeatResult hallseats = SssHelper.INSTANCE.getHallSeats(cinemaAuthInfo, matchPlan.getHallId());
	List<HallSeat> matchSeats = new ArrayList<HallSeat>();
	for(def seat_id : seat_ids.split(",")) {
		seatloop:
		for(HallSeat gseat : hallseats.getResult()) {
			seat_id = URLDecoder.decode(seat_id, 'UTF-8');
			if(seat_id.equals(gseat.getCineSeatId())) {
				matchSeats.add(gseat);
				break seatloop;
			}
		}
	}
	
	/**获取影城手续费配置相关**/
	List<TicketHandleFee> handle_fees = JcTemplate.INSTANCE().find(TicketHandleFee, 'select * from TicketHandleFee where flag!=? and store_id=? and hall_num in(?,?)', -1, store.id, matchPlan.hallId, -1);
	def store_handle_fee = new BigDecimal('0');
	def hall_service_fee = new BigDecimal('0');
	if(handle_fees) {
		for(x in handle_fees) {
			if(x.hall_num==null || x.hall_num=='-1') {
				//影城默认手续费
				store_handle_fee = x.fee;
				break;
			}
		}
		for(x in handle_fees) {
			if(x.hall_num==matchPlan.hallId) {
				hall_service_fee = x.fee;
				break;
			}
		}
	}
	// Logger.info('handle_fees===' + store.id + ", " + handle_fees.toString());
	// Logger.info('store_handle_fee type is : ' + store_handle_fee.class + ' and value is: ' + store_handle_fee);
	/**获取影城手续费配置相关**/
	
	
	DataTcSsMovieFullInfo movieInfo = FilmService.INSTANCE.getMovieInfoByNoWithoutPid(matchPlan.getMovieInfo().get(0).getCineMovieNum());
	List<SeatBuy> seats = new ArrayList<SeatBuy>();
	// 查询网售票类
	List<PlanSchema> planSchemalist = SchemaService.INSTANCE.matchPlanSchemas(matchPlan,matchPlan.getMovieInfo().get(0), cinemaSchemas);
	// Logger.info("matched schemas: {}\n, {}", JackSonBeanMapper.listToJson(cinemaSchemas), JackSonBeanMapper.listToJson(planSchemalist));
	PlanSchema match_schema = null;
	if (planSchemalist != null && !planSchemalist.isEmpty()) {
		//unitPrice = planSchemalist.get(0).getPrice();
		match_schema = planSchemalist.get(0);
	}
	// 计算总价
	/** old code
	List<DataTcSsSaleOrderSeat> seatList = new ArrayList<DataTcSsSaleOrderSeat>();
	*/
	List<SaleSeat> seatList = new ArrayList<SaleSeat>();
	
	String total_amount = "0";
	BigDecimal total_handle_fee = new BigDecimal(0);
	BigDecimal total_service_fee = new BigDecimal(0);
	for(HallSeat gseat : matchSeats) {
		// 好时光H5端显示价格 优先显示网售票类的价格
		String unitPrice = MoneyUtil.multiple(matchPlan.getMarketPrice(), "100");
		
		if(match_schema!=null) {
			unitPrice = match_schema.getPrice();
		}
		//查找最低出票价
		def low_price = matchPlan.lowestPrice;
		def settle_price = matchPlan.settlePrice;

		def pub_fee = MoneyUtil.multiple(low_price, '100');
		if(settle_price) {
			pub_fee = MoneyUtil.multiple(settle_price, '100');
		}
		
		CinemaPlanMovie pmovie = matchPlan.getMovieInfo().get(0);
		
		TicketPriceDto item = new TicketPriceDto();
		item.running_time_by_plan(matchPlan.getStartTime());
		
		def dimension = movieInfo!=null?movieInfo.film_dimensional:pmovie.movieDimensional;
		dimension = dimension.toUpperCase();	//转换大写
		if(dimension.indexOf('3D')>-1) {
			//优先匹配3D情况
			dimension = '3D';
		} else {
			dimension = '2D';
		}
		Logger.info('dimension======' + dimension + ', pmovie=' + pmovie.getMovieName());
		
		item.movie_dimensional = dimension;
		item.movie_size = movieInfo.film_size;
		item.store_limit = id + '';
		
//		item.movie_limit = movieInfo.id + '';
		//传入影片编码
		item.movie_limit = movieInfo.film_no;
		
		item.price = new BigDecimal(unitPrice);
		item.hall_limit = matchPlan.getHallId();
		item.min_price = new BigDecimal(pub_fee);

		SimpleAjax market_info = null;
		try {
			// 只取在线选座
			market_info = JC.internal.call(SimpleAjax.class, 'market', 'market/get_tcss_market_rule', ["pid":pid, "mc_type":"2000"])
		} catch (Exception e) {
			Logger.error("无获取可用的票务营销活动失败，或获取活动失败");
		}

		//根据网售规则筛选价格
		String filter_price = SchemaService.INSTANCE.filterPriceRlues(item,pid);
		try {
			if(market_info != null && market_info.available && market_info.data != null){
				item.currt_running_time = matchPlan.getStartTime();
				item.hall_limit = matchPlan.getHallId();
				filter_price = SchemaService.INSTANCE.filter_price_with_rules(pid, item, market_info.data);
			}
		} catch (Exception e) {
			Logger.error("获取可用的票务营销活动失败", e);
		}
		if(filter_price != null&&filter_price != ''){
			if (new BigDecimal(filter_price).compareTo(new BigDecimal(0))<0) {
				filter_price = '0';
			}
		}

		total_amount = MoneyUtil.add(total_amount, filter_price?filter_price:unitPrice);
		
		SaleSeat seat = new SaleSeat();
		seat.setSeat_gr(gseat.getxCoord())
		seat.setSeat_gc(gseat.getyCoord())
		seat.setSeat_sr(gseat.getRow())
		seat.setSeat_sc(gseat.getColumn())
		seat.setSeat_no(gseat.getCineSeatId())
		seat.setSale_fee(filter_price?filter_price:unitPrice)
		seat.setPub_fee(pub_fee);
		seat.setHandle_fee(store_handle_fee.toString());
		seat.setService_fee(hall_service_fee);
		seatList.add(seat);
		
		//计算服务费和手续费的总价，针对订单维度
//		total_amount = MoneyUtil.add(total_amount, store_handle_fee.toString());
//		total_amount = MoneyUtil.add(total_amount, hall_service_fee.toString());
		total_handle_fee = total_handle_fee.add(store_handle_fee);
		total_service_fee = total_service_fee.add(hall_service_fee);

		if (new BigDecimal(unitPrice).compareTo(new BigDecimal(pub_fee)) == -1) {
			//锁座需要传实际销售
			// 实际销售 小于最低价 则回传最低价
			unitPrice = pub_fee;
		}
		SeatBuy seatBuy = new SeatBuy();
		seatBuy.setBuy_amount(filter_price?filter_price:unitPrice);
		seatBuy.setDiscount(null)
		seatBuy.setHandle_fee(store_handle_fee.toString());
		seatBuy.setService_fee(hall_service_fee.toString());
		seatBuy.setSeat_no(gseat.getCineSeatId());
		seatBuy.setSubmitPrice(unitPrice);
		seats.add(seatBuy);
	}

	// 保存订单
	SimpleDateFormat clocksdf = new SimpleDateFormat("HH:mm:ss");
	SaleOrder order = new SaleOrder();
	//先设置票类
	if(match_schema!=null && match_schema.id!=null) {
		order.setTclass_id(new BigInteger(match_schema.id));
	}
	order.setOrder_no(order_no);
	order.setTotal_amount(total_amount);
	order.pay_amount = total_amount;
	order.setOrder_status(OrderConstants._order_status_create_);
	order.setStore_id(store.getId().intValue())
	order.setStore_name(store.getStore_name())
	order.setHall_id(matchPlan.getHallId())
	order.setHall_name(matchPlan.getHallName())
	order.setPlan_date(matchPlan.getStartTime().split(" ")[0]);
	order.setPlan_time(matchPlan.getStartTime().split(" ")[1])
	order.setPlan_id(plan_id)
	order.setFilm_name(matchPlan.getMovieInfo().get(0).getMovieName())
	order.setTicket_sum(seatList.size());
	order.user_id = user_id;		//ap_id
	order.proj_id = store.proj_id;
	order.film_dimensional = movieInfo.film_dimensional;
	order.film_no = matchPlan.getMovieInfo().get(0).getCineMovieNum();
	order.store_basic = store.store_basic;
	order.o_c = "2000"
	//order.handle_fee = total_handle_fee;	//增加保存手续费		订单总金额加上手续费才是真实的总支付金额
	//20190314修改，手续费与服务费分开保存
	order.handle_fee = total_handle_fee;
	order.service_fee = total_service_fee;
	order.id = JcTemplate.INSTANCE().save(order);
	// 保存座位详情
	for(SaleSeat seat:  seatList) {
		seat.setOrder_id(order.getId())
		//OrderService.INSTANCE.addOrUpdateSeat(seat);
		JcTemplate.INSTANCE().save(seat);
	}

	String remoteResult = ssc_op.batch_lock(order_no, order_no, cinemaAuthInfo.getCinemaCode(), plan_id, last_update_time, seats);
	if(remoteResult == null) {
		// 锁座失败 订单自单释放
		order.setOrder_status(OrderConstants._order_status_remote_lock_failed_)
		JcTemplate.INSTANCE().update(order);
		return Res.Failed(Codes.CINEMA_CHOOSE_SEAT_SELLED);
	}

//	DataTcSsSaleOrderRemote remote = new DataTcSsSaleOrderRemote();
//	remote.setLock_flag(remoteResult);
//	remote.setOrder_id(order.getId().longValue());
//	OrderService.INSTANCE.addOrUpdateRemote(remote);
	
	SaleRemote remote = new SaleRemote();
	remote.setLock_flag(remoteResult);
	remote.setOrder_id(order.getId());
	JcTemplate.INSTANCE().save(remote);

	DatabaseSource.getDatabasePower().commitTransaction();

	def order_data = JackSonBeanMapper.toJson(order);
	def tnum = JC.internal.param('tnum')?.trim();
	if(tnum==null) {
		tnum = '';
	}
	//开始去交易中心注册订单
	//SimpleAjax trade = RemoteUtil.connect(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'2000','od':order_data, tnum:tnum]);
	SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'2000','od':order_data, tnum:tnum, pid:pid]);
	
	if(!trade.available||trade.data==null) {
		return Res.Failed(Codes.COMMON_PARAM_ERROR,"交易创建失败，请检查：" + trade.messages.join(","));
	}
	def tradeNum = trade.data?.tnum;


	//组装返回数据
	OrderWithSeatDto oforder = new OrderWithSeatDto();
	oforder.setId(order.getId().longValue())
	oforder.setOrder_no(order.getOrder_no());
	oforder.setTotal_amount(MoneyUtil.get_yuan_from_cent(trade?.data?.total_amount?.toString()));
	oforder.setPay_amount(MoneyUtil.get_yuan_from_cent(trade?.data?.pay_amount?.toString()));
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
	//oforder.setMobile(order.getMobile())
	oforder.setSeats(new ArrayList<OrderSeatItemDto>());
	for(SaleSeat gseat : seatList) {

		OrderSeatItemDto seat = new OrderSeatItemDto();
		seat.setSeat_gr(gseat.setSeat_gr())
		seat.setSeat_gc(gseat.getSeat_gc())
		seat.setSeat_sr(gseat.getSeat_sr())
		seat.setSeat_sc(gseat.getSeat_sc())
		seat.setSeat_no(gseat.getSeat_no())
		seat.setOrder_id(gseat.getOrder_id().longValue())
		seat.setSale_fee(MoneyUtil.get_yuan_from_cent(gseat.getSale_fee()));
		seat.setPub_fee(MoneyUtil.get_yuan_from_cent(gseat.getPub_fee()))
		seat.setHandle_fee(MoneyUtil.get_yuan_from_cent(gseat.getHandle_fee()))
		seat.setService_fee(MoneyUtil.get_yuan_from_cent(gseat.getService_fee().toString()));
		oforder.getSeats().add(seat)
	}

	return Res.Success([tradeNum, oforder]);
}catch(Exception e) {
	Logger.error("下订单失败",e);
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}