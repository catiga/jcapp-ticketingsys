package com.jeancoder.ticketingsys.entry.location
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.location.LocationService
import com.jeancoder.ticketingsys.ready.location.dto.Extensive
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import java.util.List;

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	String pidStr = req.getParameter("city_id");
	Long pid = null;
	if(pidStr != null && !"".equals(pidStr)) {
		pid = Long.valueOf(pidStr);
	}else {
		result.setData(Res.Failed(Codes.COMMON_PARAM_ERROR));
		return result;
	}
	
	List<Extensive> areas = LocationService.INSTANCE.getAllAreasByCityId(pid);
	result.setData(Res.Success(areas));
	return result;
}catch(Exception e) {
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}