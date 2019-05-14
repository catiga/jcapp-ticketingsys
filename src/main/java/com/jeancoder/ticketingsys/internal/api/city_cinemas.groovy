package com.jeancoder.ticketingsys.internal.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

def cityNo = JC.internal.param("city_no");
def cityName = JC.internal.param("city_name");
def pid = JC.internal.param('pid');

try {
	//List<StoreInfo> stores = StoreService.INSTANCE.getStoresByCity(cityNo, cityName)
	
	def params = [];
	def sql = 'select * from Cinema where flag!=?'; params.add(-1);
	if(pid!=null&&pid!='null') {
		sql = sql + ' and proj_id=?';
		params.add(pid);
	}
	List<Cinema> cinemas = JcTemplate.INSTANCE().find(Cinema, sql, params.toArray());
	
	return Res.Success(cinemas);
}catch(Exception e) {
	e.printStackTrace()
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}
