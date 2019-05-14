package com.jeancoder.ticketingsys.entry.ticketSeatsDetails

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	String cinema_id = request.getParameter("cinema_id");
	result.addObject("cinema_id", cinema_id);
	result.setView("seatDetails/index" );
	return result;
}catch(Exception e){
	Logger.error("获取信息失败",e);
	return result.setData("error_msg","获取信息失败");
}
