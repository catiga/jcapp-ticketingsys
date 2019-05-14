package com.jeancoder.ticketingsys.entry.api

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	String cityNo = req.getParameter("city_no");
	String cityName = req.getParameter("city_name");
	
	List<StoreInfo> stores = StoreService.INSTANCE.getStoresByCity(cityNo, cityName)
	
	result.setData(Res.Success(stores));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}
