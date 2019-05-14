package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat

def get_code = JC.internal.param('get_code');
def validate_code = JC.internal.param('validate_code');
def pid = JC.internal.param('pid');

if(!get_code) {
	return SimpleAjax.notAvailable('param_error,取票验证码为空');
}

def sql = 'select * from SaleOrder where id in (select order_id from SaleCode where ticket_flag_1=? and ticket_flag_2=?) and proj_id=?';

List<SaleOrder> result = JcTemplate.INSTANCE().find(SaleOrder, sql, get_code, validate_code, pid);

if(!result) {
	return SimpleAjax.notAvailable('obj_not_found,订单信息不存在');
}

SaleOrder order = result.get(0);
def order_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', order.id);
if(!order_seats) {
	return SimpleAjax.notAvailable('obj_not_found,订单座位信息为空');
}

if(!order.order_status.startsWith('2')) {
	return SimpleAjax.notAvailable('status_invalid,该订单已取票');
}

//去检查这笔订单的交易状态
//SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/take_trade_order', [oid:order.id,onum:order.order_no,oc:'2000', token:token_key]);

//if(trade&&trade.available) {
//	//开始修改订单状态，并通知交易中心订单状态修改
//	order.order_status = '3000';
//	JcTemplate.INSTANCE().update(order);
//}

//return trade;

if(order.order_status=='2900' || order.order_status=='2901') {
	order.order_status = '3900';
} else if(order.order_status=='2000') {
	order.order_status = '3000';
}

JcTemplate.INSTANCE().update(order);

return SimpleAjax.available('', [order, order_seats]);

