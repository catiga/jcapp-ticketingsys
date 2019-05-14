package com.jeancoder.ticketingsys.entry.plan
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SeatPub
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.RemoteTicketResult
import com.piaodaren.ssc.model.SscAuthModel

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	String order_no = req.getParameter("order_no");
	Long id = req.getLong("cinema_id");
	String plan_id = req.getParameter("plan_id");
	String last_update_time = req.getParameter("last_update_time");
	String seat_ids = req.getParameter("seat_ids");
	String lock_flag = req.getParameter("lock_flag");
	
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
	
	if(cinemaAuthInfo == null) {
		result.setData(Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR));
		return result;
	}
	
	String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
	String channel_url = cinemaAuthInfo.getAuthChannelUrl()
	String channel_num = cinemaAuthInfo.getAuthChannelNo()
	String channel_code = cinemaAuthInfo.getAuthChannelCode()
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
	
	SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
	
	List<SeatPub> seat_nos = new ArrayList<SeatPub>();
	for(String seat_id : seat_ids.split(",")) {
		SeatPub seat = new SeatPub(); 
		seat.setAllowance("0.00")
		seat.setHandle_fee("0.00")
		seat.setPay_amount("8000")
		seat.setSeat_id(seat_id)
		seat.setSettlePrice("8000")
		seat_nos.add(seat);
	}
	
	RemoteTicketResult remoteResult = ssc_op.process_ticket(cinemaAuthInfo.getCinemaCode(), plan_id, order_no, order_no, lock_flag, seat_nos, last_update_time, null, "80.00")
	
	if(!"0".equals(remoteResult.getCode())) {
		result.setData(Res.Failed(Codes.CINEMA_CONN_FAILED,remoteResult.getMsg()));
		return result;
	}
	
	result.setData(Res.Success(remoteResult));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}