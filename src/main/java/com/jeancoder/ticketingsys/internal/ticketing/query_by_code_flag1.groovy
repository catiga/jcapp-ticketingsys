package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat

def flag_1 = JC.internal.param('flag_1');
def pid = JC.internal.param('pid');

if(!flag_1) {
	return SimpleAjax.notAvailable('param_error,取票码为空');
}

def sql = 'select * from SaleOrder where id in ( select order_id from SaleCode where ticket_flag_1=? ) and proj_id=?';

List<SaleOrder> result = JcTemplate.INSTANCE().find(SaleOrder, sql, flag_1, pid);

if(!result) {
	return SimpleAjax.notAvailable('obj_not_found,订单信息不存在');
}

SaleOrder order = result.get(0);
def order_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', order.id);
if(!order_seats) {
	return SimpleAjax.notAvailable('obj_not_found,订单座位信息为空');
}

return SimpleAjax.available('', [order, order_seats]);

