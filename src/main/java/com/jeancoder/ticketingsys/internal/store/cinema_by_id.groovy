package com.jeancoder.ticketingsys.internal.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.support.Res

def store_id = JC.internal.param('store_id');

if(!store_id) {
	return SimpleAjax.notAvailable('counter_set_error,请绑定收银台的门店信息');
}

List<Cinema> stores = JcTemplate.INSTANCE().find(Cinema, 'select * from Cinema where store_basic=? and flag!=?', store_id, -1);
//return Res.Success(stores);
if(!stores) {
	return SimpleAjax.notAvailable('store_not_cinema,当前收银台不属于影城');
}
return SimpleAjax.available('', stores);

