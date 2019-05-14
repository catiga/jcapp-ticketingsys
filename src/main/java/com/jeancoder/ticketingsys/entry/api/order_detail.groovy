package com.jeancoder.ticketingsys.entry.api
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.OrderWithTicketDto
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	String order_no = req.getParameter("order_no")
	
	OrderWithTicketDto order = OrderService.INSTANCE.getOrderWithTicketByNo(order_no);
	
	if(order == null) {
		result.setData(Res.Failed(Codes.CINEMA_GENORDER_ORDERNO_NOTFOUND));
		return result;
	}
	
	result.setData(Res.Success(order));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}