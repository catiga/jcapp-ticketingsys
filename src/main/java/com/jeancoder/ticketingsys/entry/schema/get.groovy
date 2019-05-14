package com.jeancoder.ticketingsys.entry.schema

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	Long id = req.getLong("id");
	
	if(id == null) {
		result.setData(Res.Failed(Codes.COMMON_PARAM_ERROR));
		return result;
	}
	
	def schema = SchemaService.INSTANCE.getSchemaWithItemById(id)
	
	result.setData(Res.Success(schema));
	return result;
}catch(Exception e) {
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}

