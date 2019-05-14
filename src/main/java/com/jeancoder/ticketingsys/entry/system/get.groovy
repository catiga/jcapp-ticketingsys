package com.jeancoder.ticketingsys.entry.system
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.CinemaConfig
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.system.TicketingSystemService
import com.jeancoder.ticketingsys.ready.system.dto.SystemCodeInfo

Result result = new Result();

String idStr = JC.request.param("id");

try {
	Long id = null;
	if(idStr != null && !"".equals(idStr)) {
		id = Long.valueOf(idStr);
	}
	
	if(id == null) {
		result.setData(false);
		return result;
	}
	
	//SystemCodeInfo info = TicketingSystemService.INSTANCE.getById(id);
	CinemaConfig info = JcTemplate.INSTANCE().get(CinemaConfig, 'select * from CinemaConfig where id=?', id);
	
	result.setData(Res.Success(info));
	return result;
}catch(Exception e) {
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}