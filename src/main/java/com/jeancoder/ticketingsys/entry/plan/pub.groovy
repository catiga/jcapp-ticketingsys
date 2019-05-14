package com.jeancoder.ticketingsys.entry.plan
import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderSeat
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SeatPub
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.RemoteTicketResult
import com.piaodaren.ssc.model.SscAuthModel

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	DatabaseSource.getDatabasePower().beginTransaction();
	String order_no = req.getParameter("order_no");
	
	DataTcSsSaleOrderInfo order = OrderService.INSTANCE.getOrderByNo(order_no);
	DataTcSsSaleOrderRemote remote = OrderService.INSTANCE.getRemoteByOrderNo(order_no);
	List<DataTcSsSaleOrderSeat> seats = OrderService.INSTANCE.getSeatsByOrderNo(order_no);
	
	if(order == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		result.setData(Res.Failed(Codes.CINEMA_GENORDER_ORDERNO_NOTFOUND));
		return result;
	}
		
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(order.getStore_id());
	
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
	
	SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
	
	SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
	Date start_time = datesdf.parse(order.getPlan_date());
	Calendar c = Calendar.getInstance(TimeZone.getDefault());
	c.setTime(start_time);
	c.add(Calendar.DATE, 1);
	Date end_time = c.getTime();
	CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
	CinemaPlan matchPlan = null;
	for(CinemaPlan cplan : planResult.getResult()) {
		if(cplan.getId().equals(order.getPlan_id())) {
			matchPlan = cplan;
		}
	}
	
	List<SeatPub> seat_nos = new ArrayList<SeatPub>();
	for(DataTcSsSaleOrderSeat oseat : seats) {
		SeatPub seat = new SeatPub(); 
		seat.setAllowance("0.00")
		seat.setHandle_fee(oseat.getHandle_fee())
		seat.setPay_amount(oseat.getSale_fee())
		seat.setSeat_id(oseat.getSeat_no())
		seat.setSettlePrice(oseat.getPub_fee())
		seat_nos.add(seat);
	}
	
	RemoteTicketResult remoteResult = ssc_op.process_ticket(cinemaAuthInfo.getCinemaCode(), order.getPlan_id(), order_no, order_no, remote.getLock_flag(), seat_nos, matchPlan.getCineUpdateTime(), null, MoneyUtil.divide(order.getTotal_amount(), "100"));
	
	if(!"0".equals(remoteResult.getCode())) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		result.setData(Res.Failed(Codes.CINEMA_CONN_FAILED,remoteResult.getMsg()));
		return result;
	}
	
	order.setOrder_status(OrderConstants._order_status_delivering_);
	remote.setTicket_flag_1(remoteResult.getValue1())
	remote.setTicket_flag_2(remoteResult.getValue2())
	OrderService.INSTANCE.addOuUpdateOrder(order);
	OrderService.INSTANCE.addOrUpdateRemote(remote);
	
	DatabaseSource.getDatabasePower().commitTransaction();
	
	result.setData(Res.Success(remoteResult));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	DatabaseSource.getDatabasePower().rollbackTransaction();
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}