package com.jeancoder.ticketingsys.internal.api
import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.DicCity
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.City
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res


try {
	def pid = JC.internal.param('pid');
	
	def sql = 'select b.* from Cinema a join DicCity b on a.city_no = b.`city_no` where a.flag != ? and a.proj_id = ? order by b.city_no asc';
	List<DicCity> citys = JcTemplate.INSTANCE().find(DicCity, sql, -1, pid);
	
	return Res.Success(citys);
}catch(Exception e) {
	e.printStackTrace()
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}