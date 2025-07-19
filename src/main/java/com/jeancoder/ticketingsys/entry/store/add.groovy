package com.jeancoder.ticketingsys.entry.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.store.StoreGeneral
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.entity.CinemaConfig
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

JCRequest req = JC.request.get();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

JCLogger logger = JCLoggerFactory.getLogger('ticket add store')
try {
	String idStr = req.getParameter("id");
	String store_basic_str = req.getParameter('store_basic');
	String cinema_code = req.getParameter("cinema_code");
	String config_id = req.getParameter("config_id");
	String cinema_name = req.getParameter("cinema_name");
	String cinema_physics_name = req.getParameter("cinema_physics_name");
	String prov_no = req.getParameter("prov_no");
	String prov_name = req.getParameter("prov_name");
	String city_no = req.getParameter("city_no");
	String city_name = req.getParameter("city_name");
	String area_no = req.getParameter("area_no");
	String area_name = req.getParameter("area_name");
	String cinema_address = req.getParameter("cinema_address");
	
	String inner_cinema_id = JC.request.param('inner_cinema_id');
	
//	Long id = null;
//	if(idStr != null && !"".equals(idStr)) {
//		id = Long.valueOf(idStr);
//	}
	
	Long id = null;
	Long store_basic = null;
	if(store_basic_str != null && !"".equals(store_basic_str)) {
		store_basic = Long.valueOf(store_basic_str);
	}
	def pid = GlobalHolder.proj.id;
	def json_real_stores = JC.internal.call('project', '/incall/mystore', [pid:pid]);

	logger.info("fetch store for config cinema ticket system: {}", json_real_stores);
	
	StoreGeneral real_store = null;
	List<StoreGeneral> real_stores = JackSonBeanMapper.jsonToList(json_real_stores, StoreGeneral);
	for(x in real_stores) {
		if(x.id.longValue() == store_basic) {
			real_store = x; break;
		}
	}
	if(real_store==null) {
		result.setData(Res.Failed(Codes.CINEMA_EMPTY_CINEMA));
		return result;
	}
	
	Long projId = real_store.proj_id;
	
	Cinema store = JcTemplate.INSTANCE().get(Cinema, 'select * from Cinema where flag!=? and store_basic=?', -1, BigInteger.valueOf(store_basic));
	if(!store) {
		id = null;
	} else {
		id = store.id.longValue();
	}
	
	//StoreService.INSTANCE.addOrUpdate(id, cinema_code, config_id, cinema_name, cinema_physics_name, prov_no, prov_name, city_no, city_name, area_no, area_name, cinema_address,projId);
	StoreService.INSTANCE.addOrUpdate(id, store_basic, cinema_code, config_id, cinema_name, cinema_physics_name, prov_no, prov_name, city_no, city_name, area_no, area_name, cinema_address,projId);
	
	//获取config
	CinemaConfig cinema_config = JcTemplate.INSTANCE().get(CinemaConfig, 'select * from CinemaConfig where id=?', config_id);
	if(cinema_config) {
		cinema_config.store_cinema_num = inner_cinema_id?inner_cinema_id:cinema_code;
		JcTemplate.INSTANCE().update(cinema_config);
	}
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	logger.info("error:::", e)
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}


