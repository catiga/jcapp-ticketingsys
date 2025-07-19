package com.jeancoder.ticketingsys.entry.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.store.StoreGeneral
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.entity.CinemaConfig
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.location.LocationService
import com.jeancoder.ticketingsys.ready.location.dto.Extensive
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.system.TicketingSystemService
import com.jeancoder.ticketingsys.ready.system.dto.SystemMinInfo
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

JCLogger logger = JCLoggerFactory.getLogger('')
List<Extensive> provs = LocationService.INSTANCE.getAllProvinces();

List<SystemMinInfo> systems = TicketingSystemService.INSTANCE.getAllMinInfo();

def pid = GlobalHolder.proj.id
//List<StoreInfo> stores = StoreService.INSTANCE.getAll();
List<Cinema> stores = JcTemplate.INSTANCE().find(Cinema, 'select * from Cinema where flag!=? and proj_id = ?', -1, pid);
if(stores) {
	for(x in stores) {
		if(x.config_id) {
			CinemaConfig cinema_config = JcTemplate.INSTANCE().get(CinemaConfig, 'select * from CinemaConfig where id=? and flag!=?', x.config_id, -1);
			if(cinema_config) {
				SystemMinInfo min = new SystemMinInfo();
				min.id = cinema_config.id;
				min.config_name = cinema_config.config_name;
				x.systemInfo = min;
			}
		}
	}
}

def json_real_stores = JC.internal.call('project', '/incall/mystore', [pid:pid]);

List<StoreGeneral> real_stores = [];
try {
	real_stores = JackSonBeanMapper.jsonToList(json_real_stores, StoreGeneral);
} catch(any) {
	
}

def all_result = [];
for(x in real_stores) {
	Cinema add = null;
	for(y in stores) {
		if(x.id==y.store_basic) {
			add = y; break;
		}
	}
	if(add==null) {
		add = new Cinema();
		add.address = x.address;
		add.city = x.city;
		add.city_no = x.city_no;
		add.province = x.province;
		add.province_no = x.province_no;
		add.store_basic = x.id;
		add.store_name = x.store_name;
		add.store_no = x.store_no;
		add.zone = x.zone;
		add.zone_no = x.zone_no;
		add.systemInfo = new SystemMinInfo();
	}
	if(add.systemInfo==null) {
		add.systemInfo = new SystemMinInfo();
	}
	all_result.add(add);
}
logger.info("cinema list result {}", JackSonBeanMapper.toJson(all_result))
Result result = new Result();
result.addObject("systems", systems);
result.addObject("provs", provs);
//result.addObject("stores", stores);
result.addObject('stores', all_result);
result.setView("store/list");

return result;
