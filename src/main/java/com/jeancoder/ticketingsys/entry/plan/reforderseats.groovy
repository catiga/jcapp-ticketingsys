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
import com.piaodaren.ssc.model.TicketRefundResult

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	DatabaseSource.getDatabasePower().beginTransaction();
	String order_no = req.getParameter("order_no");
	String seat_ids = req.getParameter("seat_ids");
	
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
	
	TicketRefundResult ticket_flag = ssc_op.ticket_refund(cinemaAuthInfo.getCinemaCode(), order.getOrder_no(),remote.getTicket_flag_1(),remote.getTicket_flag_2(),seat_ids);
	
	if(!"0".equals(ticket_flag.getCode())) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		result.setData(Res.Failed(Codes.CINEMA_CONN_FAILED,ticket_flag.getRmMsg()));
		return result;
	}
	
	
	for(String sid : seat_ids.split(",")) {
		int leftSeatNum = OrderService.INSTANCE.refOrderSeat(order_no, sid);
		if(leftSeatNum == 0) {
			order.setOrder_status(OrderConstants._order_status_drawback_ok_);
			OrderService.INSTANCE.addOuUpdateOrder(order);
		}
	}

	
	DatabaseSource.getDatabasePower().commitTransaction();
	
	result.setData(Res.Success(ticket_flag));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	DatabaseSource.getDatabasePower().rollbackTransaction();
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}