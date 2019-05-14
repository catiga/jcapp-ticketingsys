package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat

def on = JC.internal.param('on');
def token_key = JC.internal.param('token');
def seat = JC.internal.param('seat');			//暂时不校验座位信息，全部订单包含的座位都返回，让客户撕掉相应的票做处理
if(!on) {
	return SimpleAjax.notAvailable('param_error,订单编号参数不可为空');
}
if(!seat) {
	return SimpleAjax.notAvailable('param_error,未选择座位信息');
}

def sql = 'select * from SaleOrder where order_no=?';
List<SaleOrder> result = JcTemplate.INSTANCE().find(SaleOrder, sql, on);

if(!result) {
	return SimpleAjax.notAvailable('obj_not_found,订单信息不存在');
}

SaleOrder order = result.get(0);
def order_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', order.id);
if(!order_seats) {
	return SimpleAjax.notAvailable('obj_not_found,订单座位信息为空');
}

if(!order.order_status.startsWith('2')) {
	return SimpleAjax.notAvailable('status_invalid,订单状态不支持取票操作');
}

//去检查这笔订单的交易状态
SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/take_trade_order', [oid:order.id,onum:order.order_no,oc:'2000', token:token_key]);

if(trade&&trade.available) {
	//开始修改订单状态，并通知交易中心订单状态修改
	order.order_status = '3000';
	JcTemplate.INSTANCE().update(order);
}

return trade;

