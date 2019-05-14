package com.jeancoder.ticketingsys.entry.pay

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	String order_no = req.getParameter("order_no");
	String log_id = req.getParameter("log_id");
	DataTcSsReserveOrderInfo order = OrderService.INSTANCE.getReserveByNo(order_no)
	
	if(order == null) {
		result.setData(Res.Failed(Codes.CINEMA_GENORDER_ORDERNO_NOTFOUND));
		return result;
	}
	
	def order_data = JackSonBeanMapper.toJson(order);
	order_data = URLEncoder.encode(order_data);
	order_data = URLEncoder.encode(order_data);
	SimpleAjax trade = RemoteUtil.connect(SimpleAjax.class, 'trade', '/incall/create_trade', ['oc':'2010','od':order_data,'log_id':log_id]);
	
	println "genreservepay_______" + JackSonBeanMapper.toJson(trade);
	
	result.setData(Res.Success(trade.data.tnum));
	return result;
}catch(Exception e) {
	Logger.error("预约订单向交易中心注册失败");
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}