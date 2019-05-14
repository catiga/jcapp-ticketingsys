package com.jeancoder.ticketingsys.internal.api
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.OrderWithSeatDto
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Page
import com.jeancoder.ticketingsys.ready.support.Res

Result result = new Result();

def pid = JC.internal.param('pid');
def apid = JC.internal.param('apid');

JCLogger logger = LoggerSource.getLogger(this.class);

//def token_key = JC.internal.param('token')?.trim();
//if(token_key==null) {
//	return SimpleAjax.notAvailable('token_invalid,请登录');
//}
//
//SimpleAjax account_result = JC.internal.call(SimpleAjax, 'crm', '/h5/token_validate', ['token':token_key,pid:pid]);
//
//if(!account_result.available) {
//	return SimpleAjax.notAvailable('token_invalid,请登录');
//}
//def user_session = account_result?.data;
//BigInteger user_id = user_session['ap_id'];
//BigInteger basic_id = user_session['basic_id'];

try {
	apid = new BigInteger(apid);
} catch(any) {
	apid = null;	//不做强校验
}

def user_id = apid;

try {
	String order_no =  JC.internal.param("order_no")
	String pageSizeStr=  JC.internal.param("ps");
	String pageNoStr=  JC.internal.param("pn");
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
	logger.info("查询失败",e);
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}