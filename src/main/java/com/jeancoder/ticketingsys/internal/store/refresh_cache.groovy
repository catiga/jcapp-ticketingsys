package com.jeancoder.ticketingsys.internal.store

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

def store_basic = JC.request.param('store_basic');
def end_date = JC.request.param('end_date');	//yyyy-MM-dd

List<Cinema> cinemas = JcTemplate.INSTANCE().find(Cinema, 'select * from Cinema where store_basic=? and flag!=?', store_basic, -1);

if(cinemas==null || cinemas.empty) {
	return SimpleAjax.notAvailable(Codes.COMMON_CINEMA_CONFIG_ERROR.code + ',' + Codes.COMMON_CINEMA_CONFIG_ERROR.msg);
}

for(cinema in cinemas) {
	def cinema_id = cinema.id.longValue();
	def pid = cinema.proj_id;
	
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(cinema_id);
	
	if(cinemaAuthInfo == null) {
		return SimpleAjax.notAvailable(Codes.COMMON_CINEMA_CONFIG_ERROR.code + ',' + Codes.COMMON_CINEMA_CONFIG_ERROR.msg);
	}
	
	Calendar now = Calendar.getInstance(TimeZone.getDefault());
	now.setTime(new Date());
	
	now.set(Calendar.HOUR,0);
	now.set(Calendar.MINUTE,0);
	now.set(Calendar.SECOND,0);
	now.set(Calendar.MILLISECOND,0);
	
	Date start_time = now.getTime();
	
	SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd');
	
	Date end_time = null;
	try {
		end_time = _sdf_.parse(end_date);
	} catch(any) {
		return SimpleAjax.notAvailable('date_format_error,日期格式错误');
	}
	if(end_time.before(start_time)) {
		return SimpleAjax.notAvailable('date_after_error,选择日期不可早于当前日期');
	}
	
	Calendar end_now = Calendar.getInstance();
	end_now.setTime(end_time);
	end_now.set(Calendar.HOUR, 23);
	end_now.set(Calendar.MINUTE, 59);
	end_now.set(Calendar.SECOND, 59);
	end_time = end_now.getTime();
	
	SssHelper.INSTANCE.refresh_cinema_plans(cinemaAuthInfo, start_time, end_time);
	
	def result = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
}

return SimpleAjax.available();

