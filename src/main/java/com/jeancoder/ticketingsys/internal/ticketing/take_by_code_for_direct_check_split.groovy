package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.ticketingsys.ready.entity.SaleRemote
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants

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
def ticket_flag_1 = get_code[0];
def ticket_flag_2 = get_code[1];

def modify_status = JC.internal.param('modify_status')?.toString();

def sql = 'select * from SaleRemote where flag!=? and ticket_flag_1=? and ticket_flag_2=?';
SaleRemote seat_code = JcTemplate.INSTANCE().get(SaleRemote, sql, -1, ticket_flag_1, ticket_flag_2);
if(seat_code==null) {
	return SimpleAjax.notAvailable('obj_not_found,未找到取票信息');
}

SaleOrder order = JcTemplate.INSTANCE().get(SaleOrder, 'select * from SaleOrder where id=?', seat_code.order_id);
if(order==null) {
	return SimpleAjax.notAvailable('obj_not_found,未找到订单信息');
}
if(!order.order_status.startsWith('2') && !order.order_status.startsWith("3")) {
	return SimpleAjax.notAvailable('status_invalid,订单状态不支持进场操作');
}

List<SaleSeat> sale_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where flag!=? and order_id=?', -1, order.id);
if (sale_seats == null || sale_seats.isEmpty()) {
	return SimpleAjax.notAvailable('status_invalid,未找到订单座位信息');
}
SaleSeat seat_info = sale_seats.get(0);

//if(seat_info.went_status!='00') {
//	return SimpleAjax.notAvailable('status_invalid,该座位已经入场');
//}
if(modify_status && modify_status.equals('modify')) {
	//修改为已入场状态
	for (SaleSeat ss in sale_seats) {
		if (ss.went_status.equals('00')) {
			ss.went_status = '10';
			ss.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
			JcTemplate.INSTANCE().update(ss);
		}
	}

	def need_update = false;
	if (order.order_status.equals("2000")) {
		order.order_status = OrderConstants._order_status_taked_tcss_by_elec_;
		need_update = true;
	} else if (order.order_status.equals("2900")) {
		order.order_status = "3900";
		need_update = true;
	} else if (order.order_status.equals("2901")) {
		order.order_status = "3901";
		need_update = true;
	}
	if (need_update) {
		JcTemplate.INSTANCE().update(order);
	}
}

return SimpleAjax.available('', [order, sale_seats]);
