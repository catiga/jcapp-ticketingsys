package com.jeancoder.ticketingsys.entry.api

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.film.MaoyanHelper
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

import groovy.json.JsonSlurper

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	String s = MaoyanHelper.INSTANCE.getComming();
	
	def data = new JsonSlurper().parseText(s);
	
	result.setData(Res.Success(data));
	return result;
}catch(Exception e) {
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}