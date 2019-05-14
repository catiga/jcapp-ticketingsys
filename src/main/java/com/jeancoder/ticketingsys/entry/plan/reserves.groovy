package com.jeancoder.ticketingsys.entry.plan
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.ReserveWithSeatDto
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Page
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	String phone_number = req.getParameter("phone_number")
	String order_no = req.getParameter("order_no")
	String pageSizeStr= req.getParameter("ps");
	String pageNoStr= req.getParameter("pn");
	Page page = new Page();
	if(pageSizeStr != null && !"".equals(pageSizeStr)) {
		try {page.setPageSize(Integer.parseInt(pageSizeStr))} catch (Exception e) {}
	}
	if(pageNoStr != null && !"".equals(pageNoStr)) {
		try {page.setPageNo(Integer.parseInt(pageNoStr))} catch (Exception e) {}
	}
	
	List<ReserveWithSeatDto> reserves = OrderService.INSTANCE.getReserves(page,phone_number,order_no);
	
	result.setData(Res.Success(reserves));
	return result;
}catch(Exception e) {
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}