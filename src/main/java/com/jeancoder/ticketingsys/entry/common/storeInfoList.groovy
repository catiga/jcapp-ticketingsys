package com.jeancoder.ticketingsys.entry.common

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.store.StoreGeneral
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
StoreService store_service = new StoreService();
try {
	def pid=GlobalHolder.getProj().getId();
	def page = store_service.find_stores(pid);
	return result.setData(AvailabilityStatus.available(page));
	
} catch (Exception e) {
	Logger.error("查询门店列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询门店列表失败"));
}
