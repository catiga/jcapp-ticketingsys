package com.jeancoder.ticketingsys.entry.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.store.StoreGeneral
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.system.TicketingSystemService
import com.jeancoder.ticketingsys.ready.system.dto.SystemMinInfo

Result result = new Result();

def pid = GlobalHolder.proj.id;
try {
//	println 'pid=======' + pid;
//	String idStr = JC.request.param("id");
//	
//	Long id = null;
//	if(idStr != null && !"".equals(idStr)) {
//		id = Long.valueOf(idStr);
//	}
//	
//	StoreInfo store = StoreService.INSTANCE.getById(id);
	
	def store_basic = JC.request.param('id');
	
	Cinema store = JcTemplate.INSTANCE().get(Cinema, 'select * from Cinema where flag!=? and store_basic=?', -1, store_basic);
	
	if(store) {
		if(store.getConfig_id() != null) {
			SystemMinInfo systemInfo = TicketingSystemService.INSTANCE.getMinInfoById(store.getConfig_id().longValue());
			store.setSystemInfo(systemInfo);
		}
		result.setData(Res.Success(store));
		return result;
	}
	
	def json_real_stores = JC.internal.call('project', '/incall/mystore', [pid:pid]);
	
	def real_store = null;
	List<StoreGeneral> real_stores = JackSonBeanMapper.jsonToList(json_real_stores, StoreGeneral);
	for(x in real_stores) {
		if(x.id.toString()==store_basic.toString()) {
			real_store = x;
			break;
		}
	}
	if(real_store==null) {
		result.setData(Res.Failed(Codes.CINEMA_EMPTY_CINEMA));
		return result;
	}
	store = new Cinema();
	store.address = real_store.address;
	store.city = real_store.city;
	store.city_no = real_store.city_no;
	store.latitude = real_store.latitude;
	store.longitude = real_store.longitude;
	store.physics_name = real_store.store_name;
	store.proj_id = real_store.proj_id;
	store.province = real_store.province;
	store.province_no = real_store.province_no;
	store.store_basic = real_store.id;
	store.store_logo = real_store.store_logo;
	store.store_name = real_store.store_name;
	//store.store_no = real_store.store_no;
	store.zone = real_store.zone;
	store.zone_no = real_store.zone_no;
	store.systemInfo = new SystemMinInfo();
	result.setData(Res.Success(store));
	return result;
}catch(Exception e) {
	e.printStackTrace();
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}