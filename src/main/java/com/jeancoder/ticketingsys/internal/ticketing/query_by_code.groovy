package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat

def get_code = JC.internal.param('get_code');

if(!get_code) {
	return SimpleAjax.notAvailable('param_error,取票验证码为空');
}
def flag_1 = get_code.substring(0, 6);
def flag_2 = get_code.substring(6);

def sql = 'select * from SaleOrder where id in (select order_id from SaleCode where ticket_flag_1=? and ticket_flag_2=?)';

List<SaleOrder> result = JcTemplate.INSTANCE().find(SaleOrder, sql, flag_1, flag_2);

if(!result) {
	return SimpleAjax.notAvailable('obj_not_found,订单信息不存在');
}

SaleOrder order = result.get(0);
def order_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', order.id);
if(!order_seats) {
	return SimpleAjax.notAvailable('obj_not_found,订单座位信息为空');
}

return SimpleAjax.available('', [order, order_seats]);

