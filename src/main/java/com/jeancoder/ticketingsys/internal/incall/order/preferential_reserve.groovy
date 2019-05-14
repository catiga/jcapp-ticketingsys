package com.jeancoder.ticketingsys.internal.incall.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.OrderSeatItemDto
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderRemote
import com.jeancoder.ticketingsys.ready.prefer.PreferFactory
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo

JCLogger LOGGER = JCLoggerFactory.getLogger('GOODS_ORDER_NOTIFY');

def card_code = JC.internal.param('unicode');
def oid = JC.internal.param('o_id');
def pref = JC.internal.param('pref');
def pid = JC.internal.param('pid');
def op = JC.internal.param('op')?.trim();
if(pref!='100'&&pref!='200') {
	return SimpleAjax.notAvailable('unsupport_type,不支持的优惠类型');
}

DataTcSsReserveOrderInfo tcss_order = OrderService.INSTANCE.getReserveOrderById(oid);

if(tcss_order==null) {
	return SimpleAjax.notAvailable('order_not_found,票务订单未找到');
}
if(!tcss_order.order_status.startsWith('0')) {
	return SimpleAjax.notAvailable('order_status_invalid,订单类型不合法');
}
DataTcSsReserveOrderRemote remote = OrderService.INSTANCE.getReserveRemoteByOrderNo(tcss_order.order_no);
List<OrderSeatItemDto> items = OrderService.INSTANCE.getReserveSeats(tcss_order.id);

//println "preferential___" + JackSonBeanMapper.toJson(items);
// [goods_id, [cat_ids, cat_ids], price, num]
def param = [];

for(x in items) {
	param.add([x.seat_no, x.sale_fee,x.pub_fee]);
}

StoreInfo store = StoreService.INSTANCE.getById(tcss_order.store_id);

def computer = PreferFactory.generate(pref, '2010');
def order_param = [:];
order_param['on'] = tcss_order.order_no;
order_param['oc'] = tcss_order.o_c;
order_param['pid'] = store.proj_id;
order_param['id'] = tcss_order.id;
order_param['sid'] = store.store_basic;
order_param['lock_flag'] = remote.lock_flag;
order_param['hall_id'] = tcss_order.hall_id;
order_param['film_no'] = tcss_order.film_no;
SimpleAjax ret_obj = computer.compute(order_param, card_code, op, param);
return ret_obj;




