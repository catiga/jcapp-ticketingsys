package com.jeancoder.ticketingsys.entry.store.aj

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

JCLogger logger = JCLoggerFactory.getLogger('');
def store_id = JC.request.param('store_id');
def end_date = JC.request.param('end_date');	//yyyy-MM-dd

def cinema_id = 0;
try {
	cinema_id = Long.valueOf(store_id);
}catch(any) {
	return SimpleAjax.notAvailable('cinema_id_error,影城选择错误');
}

Cinema cinema = JcTemplate.INSTANCE().get(Cinema, 'select * from Cinema where id=?', cinema_id);

if(cinema==null) {
	//return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
	return SimpleAjax.notAvailable(Codes.COMMON_CINEMA_CONFIG_ERROR.code + ',' + Codes.COMMON_CINEMA_CONFIG_ERROR.msg);
}
def pid = cinema.proj_id;

CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(cinema_id);

if(cinemaAuthInfo == null) {
	//return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
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

return SimpleAjax.available();

