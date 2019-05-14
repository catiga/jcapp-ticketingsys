package com.jeancoder.ticketingsys.entry.store
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	String idStr = req.getParameter("id");
	
	Long id = null;
	if(idStr != null && !"".equals(idStr)) {
		id = Long.valueOf(idStr);
	}
	
	StoreService.INSTANCE.del(id);
	
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}