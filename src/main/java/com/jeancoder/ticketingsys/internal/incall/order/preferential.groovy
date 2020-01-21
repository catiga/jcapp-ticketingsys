package com.jeancoder.ticketingsys.internal.incall.order

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
import com.jeancoder.ticketingsys.ready.prefer.Prefer
import com.jeancoder.ticketingsys.ready.prefer.PreferFactory
import com.jeancoder.ticketingsys.ready.prep.PrepData
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.util.MoneyUtil

JCLogger LOGGER = JCLoggerFactory.getLogger('GOODS_ORDER_NOTIFY');

def card_code = JC.internal.param('unicode');
def oid = JC.internal.param('o_id');
def pref = JC.internal.param('pref');
def pid = JC.internal.param('pid');
def op = JC.internal.param('op')?.toString()?.trim();

def ct = JC.internal.param('ct')?.toString()?.trim();
def mobile = JC.internal.param('mobile')?.toString()?.trim();

def market_id = JC.internal.param('market_id');
def ap_id = JC.internal.param('ap_id');

if(pref!='100'&&pref!='200'&&pref!='300') {
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
List<OrderSeatItemDto> items = OrderService.INSTANCE.getOrderSeats(Long.parseLong(oid.toString()));
def param = [];

def store_id = tcss_order.store_id;
def hall_id = tcss_order.hall_id;

BigDecimal pref_amount = new BigDecimal(0);

for(x in items) {
	def sale_fee = x.sale_fee;
	def pub_fee = x.pub_fee;
	
	pref_amount = pref_amount.add(new BigDecimal(x.sale_fee).subtract(new BigDecimal(sale_fee)));
	param.add([x.seat_no, sale_fee,pub_fee]);
}
StoreInfo store = StoreService.INSTANCE.getById(tcss_order.store_id);
LOGGER.info('preferential market_id====' + market_id);
if(market_id && market_id.toString().trim()!='') {
	//优先计算活动
	pref = '300';
	card_code = market_id;
}
LOGGER.info('preferential pref_code=' + pref);
Prefer computer = PreferFactory.generate(pref);

def order_param = [:];
order_param['on'] = tcss_order.order_no;
order_param['oc'] = tcss_order.o_c;
order_param['pid'] = store.proj_id;
order_param['id'] = tcss_order.id;
order_param['sid'] = store.store_basic;
order_param['lock_flag'] = remote.lock_flag;
order_param['hall_id'] = tcss_order.hall_id;
order_param['film_no'] = tcss_order.film_no;
order_param['film_dimen'] = tcss_order.film_dimensional;
order_param['mobile'] = mobile;
order_param['ap_id'] = ap_id;


SimpleAjax ret_obj = computer.compute(order_param, card_code, op, param);

if(store_id==5) {
	//针对包头需要重置优惠金额
	if(ret_obj.available) {
		if(ret_obj['data']['prefcode']=='100') {
			ret_obj['data']['pref_amount'] = pref_amount;
		}
	}
}

//临时代码开始//
//def mobile_limits = ['13980349218', '13308141980', '18116518288', '15984588919',
//	'18089572520', '13684268996', '15328996655', '18089590838', '13778556060', '15008110205', '18623328288', '18180158277', '13501020884'];
//
//if(ct && ct=='C_PSBC') {
//	if(mobile && ( mobile in mobile_limits)) {
//		if(ret_obj && ret_obj.available) {
//			ret_obj['data']['pref_amount'] = new BigDecimal('900');
//		} else {
//			//开始计算总价
//			def stand_seat_price = new BigDecimal('900');
//			def ticket_num = new BigDecimal(items.size());
//			
//			PrepData for_trade_prep = new PrepData(order_id:oid,oc:tcss_order.o_c,prefcode:'100');
//			for_trade_prep.pref_amount = new BigDecimal(tcss_order.pay_amount).subtract(stand_seat_price.multiply(ticket_num));
//			for_trade_prep.pay_amount = stand_seat_price.multiply(ticket_num);
//			ret_obj = SimpleAjax.available('', for_trade_prep);
//		}
//	}
//}
//临时代码结束//

return ret_obj;




