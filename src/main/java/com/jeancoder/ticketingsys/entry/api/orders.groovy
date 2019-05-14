package com.jeancoder.ticketingsys.entry.api
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.OrderWithSeatDto
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Page
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

def token_key = JC.request.param('token')?.trim();
if(token_key==null) {
	return SimpleAjax.notAvailable('token_invalid,请登录');
}

//SimpleAjax account_result = RemoteUtil.connect(SimpleAjax, 'crm', '/h5/token_validate', ['token':token_key]);
SimpleAjax account_result = JC.internal.call(SimpleAjax, 'crm', '/h5/token_validate', ['token':token_key,pid:GlobalHolder.proj.id]);

if(!account_result.available) {
	return SimpleAjax.notAvailable('token_invalid,请登录');
}
def user_session = account_result?.data;
BigInteger user_id = user_session['ap_id'];
BigInteger basic_id = user_session['basic_id'];

try {
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
	
	List<OrderWithSeatDto> orders = OrderService.INSTANCE.getOrdersByApId(page,user_id,order_no)
	
	result.setData(Res.Success(orders));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}