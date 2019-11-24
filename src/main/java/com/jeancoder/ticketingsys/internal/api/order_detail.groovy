package com.jeancoder.ticketingsys.internal.api
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.OrderSeatItemDto
import com.jeancoder.ticketingsys.ready.order.dto.OrderWithTicketDto
import com.jeancoder.ticketingsys.ready.prefer.CouponPrefer
import com.jeancoder.ticketingsys.ready.prefer.Prefer
import com.jeancoder.ticketingsys.ready.prefer.PreferFactory
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();
JCLogger LOGGER = LoggerSource.getLogger();

String order_no = JC.internal.param("order_no");
def coupon_id = JC.internal.param('coupon_id');
def market_id = JC.internal.param('market_id');

def mobile = JC.internal.param('mobile');
def ap_id = JC.internal.param('ap_id');

try {
	OrderWithTicketDto order = OrderService.INSTANCE.getOrderWithTicketByNo(order_no);
	if(order == null) {
		return Res.Failed(Codes.CINEMA_GENORDER_ORDERNO_NOTFOUND);
	}
	def proj_id = order.proj_id;
	def store_id = order.store_id;
	def order_id = order.id;
	def op = "";
	Cinema cinema = JcTemplate.INSTANCE().get(Cinema, 'select * from Cinema where id=?', store_id);
	store_id = cinema.store_basic;
	def pref_data = null;
	if(market_id) {
		//首先判断是否选择了活动
		//活动逻辑先行
		List<OrderSeatItemDto> items = OrderService.INSTANCE.getOrderSeats(order.id);
		def param = [];
		for(x in items) {
			param.add([x.seat_no,x.sale_fee, x.pub_fee, order.hall_id]);
		}
		Prefer computer = PreferFactory.generate('300', '2000');	//创建券的活动价格计算器
		def order_param = [:];
		order_param['on'] = order.order_no;
		order_param['oc'] = order.o_c;
		order_param['pid'] = cinema.proj_id;
		order_param['id'] = order.id;
		order_param['sid'] = store_id;
		order_param['hall_id'] = order.hall_id;
		order_param['film_no'] = order.film_no;
		order_param['mobile'] = mobile;
		SimpleAjax ret_obj = computer.compute(order_param, market_id, op, param);
		pref_data = ret_obj;
	} else if(coupon_id) {
		//没有活动的情况，判断是否选择了券
		
		List<OrderSeatItemDto> items = OrderService.INSTANCE.getOrderSeats(order.id);
		def param = [];
		for(x in items) {
			param.add([x.seat_no, x.sale_fee]);
			param.add([x.seat_no,x.sale_fee, x.pub_fee, order.hall_id]);
		}
		CouponPrefer computer = new CouponPrefer(oc:'2010');
		def order_param = [:];
		order_param['on'] = order.order_no;
		order_param['oc'] = order.o_c;
		order_param['pid'] = cinema.proj_id;
		order_param['id'] = order.id;
		order_param['sid'] = store_id;
		order_param['hall_id'] = order.hall_id;
		order_param['film_no'] = order.film_no;
		SimpleAjax ret_obj = computer.compute(order_param, coupon_id, op, param);
		pref_data = ret_obj;
	}
	return Res.Success([order, pref_data]);
}catch(Exception e) {
	LOGGER.error("系统错误",e)
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}

