package com.jeancoder.ticketingsys.internal.ticketing

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat

// order_id, order_seat_id 这样入参
JCLogger Logger = LoggerSource.getLogger();
def get_code = JC.internal.param('get_code')?.toString();
if(!get_code) {
	return SimpleAjax.notAvailable('param_error,验票信息为空');
}
get_code = get_code.split(',');
def order_id = get_code[0];
def order_seat_id = get_code[1];

def modify_status = JC.internal.param('modify_status')?.toString();

def sql = 'select * from SaleSeat where id=?';
SaleSeat seat_info = JcTemplate.INSTANCE().get(SaleSeat, sql, order_seat_id);
if(seat_info==null) {
	return SimpleAjax.notAvailable('obj_not_found,未找到座位信息');
}
if(seat_info.order_id.toString()!=order_id.toString()) {
	return SimpleAjax.notAvailable('obj_not_found,订单座位信息不匹配');
}

SaleOrder order = JcTemplate.INSTANCE().get(SaleOrder, 'select * from SaleOrder where id=?', seat_info.order_id);
if(order==null) {
	return SimpleAjax.notAvailable('obj_not_found,未找到订单信息');
}
if(order.order_status.startsWith('0')) {
	return SimpleAjax.notAvailable('status_invalid,订单状态不支持进场操作');
}
if(seat_info.went_status!='00') {
	return SimpleAjax.notAvailable('status_invalid,该座位已经入场');
}
if(modify_status && modify_status.equals('modify')) {
	//修改为已入场状态
	seat_info.went_status = '10';
	seat_info.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	JcTemplate.INSTANCE().update(seat_info);
}

return SimpleAjax.available('', [order, seat_info]);
