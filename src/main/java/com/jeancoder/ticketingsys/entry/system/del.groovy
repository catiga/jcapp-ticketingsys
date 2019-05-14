package com.jeancoder.ticketingsys.entry.system
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.system.TicketingSystemService

JCRequest req = RequestSource.getRequest();
Result result = new Result();

try {
	String idStr = req.getParameter("id");
	
	Long id = null;
	if(idStr != null && !"".equals(idStr)) {
		id = Long.valueOf(idStr);
	}
	
	if(id == null) {
		result.setData(false);
		return result;
	}
	
	TicketingSystemService.INSTANCE.delete(id)
	
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}