package com.jeancoder.ticketingsys.internal.api

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.film.MaoyanHelper
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

import groovy.json.JsonSlurper

JCLogger logger = JCLoggerFactory.getLogger('comming_list');

try {
	String s = MaoyanHelper.INSTANCE.getComming();
	logger.info('comming_str=' + s);
	def data = new JsonSlurper().parseText(s);
	return Res.Success(data);
}catch(any) {
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}