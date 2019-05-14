package com.jeancoder.ticketingsys.entry.schema
import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.category.CategoryService
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.entity.TicketPriceSchemaGroup
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
	
	DatabaseSource.getDatabasePower().beginTransaction();
	
	SchemaService.INSTANCE.deleteSchema(id);
	
	DatabaseSource.getDatabasePower().commitTransaction();
	
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	e.printStackTrace()
	DatabaseSource.getDatabasePower().rollbackTransaction();
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}