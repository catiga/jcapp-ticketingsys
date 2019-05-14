package com.jeancoder.ticketingsys.entry.incall.order

/**
 * 统一优惠计算服务，接收参数如下
 *
 * @param o_id :  order_id;		//传入订单id
 * @param unicode : 统一代码		//会员／优惠券码／活动编码
 * @param pref : 计算类型;		// 100:会员；200:优惠券
 */

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.OrderSeatItemDto
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderRemote
import com.jeancoder.ticketingsys.ready.prefer.PreferFactory
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo

JCLogger LOGGER = JCLoggerFactory.getLogger('GOODS_ORDER_NOTIFY');

def card_code = JC.request.param('unicode');
def oid = JC.request.param('o_id');
def pref = JC.request.param('pref');
def op = JC.request.param('op')?.trim();
if(pref!='100'&&pref!='200') {
	return SimpleAjax.notAvailable('unsupport_type,不支持的优惠类型');
}

DataTcSsSaleOrderInfo tcss_order = OrderService.INSTANCE.getOrderById(oid);
if(tcss_order==null) {
	return SimpleAjax.notAvailable('order_not_found,票务订单未找到');
}
if(!tcss_order.order_status.startsWith('0')) {
	return SimpleAjax.notAvailable('order_status_invalid,订单类型不合法');
}

DataTcSsSaleOrderRemote remote = OrderService.INSTANCE.getRemoteByOrderNo(tcss_order.order_no);
List<OrderSeatItemDto> items = OrderService.INSTANCE.getOrderSeats(Long.parseLong(oid));
//println "preferential___" + JackSonBeanMapper.toJson(items);
// [goods_id, [cat_ids, cat_ids], price, num]
def param = [];

def store_id = tcss_order.store_id;
def hall_id = tcss_order.hall_id;

BigDecimal pref_amount = new BigDecimal(0);

for(x in items) {
	//param.add([x.seat_no, x.sale_fee,x.pub_fee]);
	def sale_fee = x.sale_fee;
	def pub_fee = x.pub_fee;
	if(store_id==5 && "100".equals(pref)) {
		//证明是包头的优惠券计算 传原价计算。
		//证明是包头的
		//增加传入影厅编号，针对包头执行特殊的价格计算
		if(hall_id=='0000000000000008') {
			sale_fee = '2500';
		} else {
			sale_fee = '2000';
		}
		pub_fee = sale_fee;
	}
	pref_amount = pref_amount.add(new BigDecimal(x.sale_fee).subtract(new BigDecimal(sale_fee)));
	param.add([x.seat_no, sale_fee,pub_fee]);
}

StoreInfo store = StoreService.INSTANCE.getById(tcss_order.store_id);
def computer = PreferFactory.generate(pref);
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
if(store_id==5) {
	//针对包头需要重置优惠金额
	if(ret_obj.available) {
		if(ret_obj['data']['prefcode']=='100') {
			ret_obj['data']['pref_amount'] = pref_amount;
		}
	}
}
return ret_obj;




