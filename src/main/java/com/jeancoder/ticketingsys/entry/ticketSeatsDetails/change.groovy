package com.jeancoder.ticketingsys.entry.ticketSeatsDetails

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.common.SimpleAjax

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String cinema_id = request.getParameter("cinema_id");
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	String day = format.format(new Date());
	SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'ticketingsys', '/plan/movies', [cinema_id:cinema_id, day:day]);
	println ret_result.data;
	return result.setData(AvailabilityStatus.available(ret_result.data));
}catch(Exception e){
	Logger.error("更改座位信息失败",e);
	return result.setData("error_msg","更改座位信息失败");
}
