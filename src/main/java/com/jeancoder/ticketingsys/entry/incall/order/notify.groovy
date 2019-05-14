package com.jeancoder.ticketingsys.entry.incall.order

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.MD5Util
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderSeat
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderSeat
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.jeancoder.ticketingsys.ready.trade.DataTrans
import com.jeancoder.ticketingsys.ready.uncertain.SscOpFactory
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.jeancoder.ticketingsys.ready.util.NotifyObj
import com.piaodaren.ssc.factory.SeatPub
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.RemoteTicketResult
import com.piaodaren.ssc.model.SscAuthModel
import com.piaodaren.ssc.model.TicketRefundResult

JCLogger LOGGER = JCLoggerFactory.getLogger('TICKET_ORDER_NOTIFY');

def oc = JC.request.param('oc');
def on = JC.request.param('on');
def sign = JC.request.param('sign');
def pt = JC.request.param('pt');
def op = JC.request.param('op');
def pay_amount = JC.request.param('pay_amount');

DataTrans dt = GlobalHolder.getDt();
if(dt.code!='0') {
	return NotifyObj.build('-1', '交易出票错误', '1001', '交易模块注册错误', null);
}
println JackSonBeanMapper.toJson(dt);
def code_result = null;
def code_type = null;
dt?.data?.each{
	it-> if(it.order_type==oc) {
		code_result = it.token; code_type = it.order_type;
		return;
	}
}
if(!code_result) {
	code_result = '';
}
LOGGER.info('code_token=' + code_result + '+++++++++++++++++++ code_type=' + code_type);
//计算sign
def orig_str = 'oc='+oc+'&on='+on;
def orig_str_sign = MD5Util.getStringMD5(orig_str + code_result);

if(orig_str_sign!=sign) {
	LOGGER.error('sign=' + sign + ' is not match the computed sign value=' + orig_str_sign);
	return NotifyObj.build('-1', '交易出票错误', '2001', '密钥签名验证错误', null);
}


// 退票流程
if ("refund".equals(op) && code_type=='2000') {
	DataTcSsSaleOrderInfo order = OrderService.INSTANCE.getOrderByNo(on);
	DataTcSsSaleOrderRemote remote = OrderService.INSTANCE.getRemoteByOrderNo(on);
	List<DataTcSsSaleOrderSeat> seats = OrderService.INSTANCE.getSeatsByOrderNo(on);
	if(order == null) {
		LOGGER.error('order_not_found');
		return NotifyObj.build('-1', '交易退票失败', '3001', '交易订单未找到:' + on, null);
	}
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(order.getStore_id());
	if(cinemaAuthInfo == null) {
		return NotifyObj.build('-1', '交易退票失败', '3002', '未配置影院系统信息', null);
	}
	def seatss = "";
	if ("101001".equals(order.pay_type)) {
		def param = [:];
		param['pid'] = order.proj_id.toString();
		param['c_id'] = cinemaAuthInfo.getCinemaCode();
		param['order_no'] = on;
		param['oc'] = '2000';
		param['seats'] = seatss;
		param['lock_flag'] = remote.getLock_flag();
		param['ticket_flag1'] = remote.getTicket_flag_1();
		param['ticket_flag2'] = remote.getTicket_flag_2();
		SimpleAjax ajax = JC.internal.call(SimpleAjax,"crm", "/api/order/ticket_refund_outer_mc",param)
		LOGGER.info("ticket_refund_outer_mc " + JackSonBeanMapper.toJson(ajax));
		if (ajax.available && "0".equals(ajax.data.code)) {
			// 请求成功 且退票成功
			order.refund_time = new Date();
			order.setOrder_status(OrderConstants._order_status_drawback_ok_);
			OrderService.INSTANCE.addOuUpdateOrder(order);
			return NotifyObj.succ();
		}
		
		if (ajax.available &&  !"0".equals(ajax.data.code)) {
			// 请求成功 且退票失败
			order.refund_time = new Date();
			order.setOrder_status(OrderConstants._order_status_drawback_ok_);
			OrderService.INSTANCE.addOuUpdateOrder(order);
			return NotifyObj.build('-1', '交易退票失败', '3002', ajax.data.rmCode+":"+ajax.data.rmMsg, null);
		}
		
		if ((!ajax.available) && !"not_outer_mc".equals(ajax.messages[0])) {
			// 请求失败 而且错误提示不是   不是外部会员卡。 不是外部会员卡走普通的退票流程
			return NotifyObj.build('-1', '退票失败', '3002',ajax.messages[0], null);
		}
	} 
	
	String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
	String channel_url = cinemaAuthInfo.getAuthChannelUrl()
	String channel_num = cinemaAuthInfo.getAuthChannelNo()
	String channel_code = cinemaAuthInfo.getAuthChannelCode()
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
	SscOp ssc_op = SscOpFactory.generateSscOp(auth_model, order.user_id);
	
	LOGGER.info("退票参数_"+ JackSonBeanMapper.toJson(cinemaAuthInfo));
	LOGGER.info("退票参数_"+ order.getOrder_no());
	
	
	
	//ticket_refund(String c_id, String inner_order_no, String type,String ticket_flag1, String ticket_flag2, String seats,String lock_flag) {
	//TicketRefundResult ticket_flag = ssc_op.ticket_refund(cinemaAuthInfo.getCinemaCode(), order.getOrder_no(), remote.getTicket_flag_1(), remote.getTicket_flag_2(), seatss)
	//TicketRefundResult ticket_flag = ssc_op.ticket_refund(cinemaAuthInfo.getCinemaCode(), order.getOrder_no(),"1", remote.getTicket_flag_1(), remote.getTicket_flag_2(), seatss,remote.getLock_flag())
	TicketRefundResult ticket_flag = ssc_op.ticket_refund(cinemaAuthInfo.getCinemaCode(), order.getOrder_no(), "1",remote.getTicket_flag_1(),remote.getTicket_flag_2(),"TODO 座位号",remote.getLock_flag());
	LOGGER.info("退票结果:" + JackSonBeanMapper.toJson(ticket_flag) + " orderNo:"+ on);
	if(!"0".equals(ticket_flag.getCode())) {
		return NotifyObj.build('-1', '交易退票失败', '3002', ticket_flag.rmCode + ":" + ticket_flag.rmMsg, null);
	}
	order.refund_time = new Date();
	order.setOrder_status(OrderConstants._order_status_drawback_ok_);
	OrderService.INSTANCE.addOuUpdateOrder(order);
	
	return NotifyObj.succ();
} else if ("refund".equals(op) && code_type=='2010') {
	DataTcSsReserveOrderInfo order = OrderService.INSTANCE.getReserveOrderByNo(on);
	DataTcSsReserveOrderRemote remote = OrderService.INSTANCE.getReserveRemoteByOrderNo(on);
	List<DataTcSsReserveOrderSeat> seats = OrderService.INSTANCE.getReserveSeatsByOrderNo(on);
	if(order == null) {
		LOGGER.error('order_not_found');
		return NotifyObj.build('-1', '交易退票失败', '3001', '交易订单未找到:' + on, null);
	}
	def seatss = "";
	for (DataTcSsReserveOrderSeat seat :seats ) {
		seatss +=seat.seat_no+","
	}
	seatss = seatss.substring(0,seatss.length());
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(order.getStore_id());
	if(cinemaAuthInfo == null) {
		return NotifyObj.build('-1', '交易退票失败', '3002', '未配置影院系统信息', null);
	}
	if ("101001".equals(order.pay_type)) {
		def param = [:];
		param['pid'] = order.proj_id.toString();
		param['c_id'] = cinemaAuthInfo.getCinemaCode();
		param['order_no'] = on;
		param['oc'] = '2010';
		param['seats'] = seatss;
		param['lock_flag'] = remote.getLock_flag();
		param['ticket_flag1'] = remote.getTicket_flag_1();
		param['ticket_flag2'] = remote.getTicket_flag_2();
		SimpleAjax ajax = JC.internal.call(SimpleAjax,"crm", "/api/order/ticket_refund_outer_mc", param)
		if (ajax.available && "0".equals(ajax.data.code)) {
			// 请求成功 且退票成功
			order.refund_time = new Date();
			order.setOrder_status(OrderConstants._order_status_drawback_ok_);
			OrderService.INSTANCE.addOuUpdateReserveOrder(order);
			return NotifyObj.succ();
		}
		
		if (ajax.available &&  !"0".equals(ajax.data.code)) {
			// 请求成功 且退票失败
			order.refund_time = new Date();
			order.setOrder_status(OrderConstants._order_status_drawback_ok_);
			OrderService.INSTANCE.addOuUpdateReserveOrder(order);
			return NotifyObj.build('-1', '交易退票失败', '3002', ajax.data.rmCode+":"+ajax.data.rmMsg, null);
		}
		
		if ((!ajax.available) && !"not_outer_mc".equals(ajax.messages[0])) {
			// 请求失败 而且错误提示不是   不是外部会员卡。 不是外部会员卡走普通的退票流程
			return NotifyObj.build('-1', '退票失败', '3002',ajax.messages[0], null);
		}
	}
	String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
	String channel_url = cinemaAuthInfo.getAuthChannelUrl()
	String channel_num = cinemaAuthInfo.getAuthChannelNo()
	String channel_code = cinemaAuthInfo.getAuthChannelCode()
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
	SscOp ssc_op = SscOpFactory.generateSscOp(auth_model, order.user_id);
	//TicketRefundResult ticket_flag = ssc_op.ticket_refund(cinemaAuthInfo.getCinemaCode(), order.getOrder_no(),"1", remote.getTicket_flag_1(), remote.getTicket_flag_2(), seatss,remote.getLock_flag())
	TicketRefundResult ticket_flag = ssc_op.ticket_refund(cinemaAuthInfo.getCinemaCode(), order.getOrder_no(), remote.getTicket_flag_1(), remote.getTicket_flag_2(), seatss)
	LOGGER.info("退票结果:" + JackSonBeanMapper.toJson(ticket_flag) + " orderNo:"+ on);
	if(!"0".equals(ticket_flag.getCode())) {
		return NotifyObj.build('-1', '交易退票失败', '3002', ticket_flag.rmCode + ":" + ticket_flag.rmMsg, null);
	}
	order.refund_time = new Date();
	order.setOrder_status(OrderConstants._order_status_drawback_ok_);
	OrderService.INSTANCE.addOuUpdateReserveOrder(order);
	return NotifyObj.succ();
	
} 

if(code_type=='2000') {
	//开始执行出票操作 TODO
	DatabaseSource.getDatabasePower().beginTransaction();
	DataTcSsSaleOrderInfo order = OrderService.INSTANCE.getOrderByNo(on);
	DataTcSsSaleOrderRemote remote = OrderService.INSTANCE.getRemoteByOrderNo(on);
	List<DataTcSsSaleOrderSeat> seats = OrderService.INSTANCE.getSeatsByOrderNo(on);
	
	order.order_status=OrderConstants._order_status_payed_;
	
	if(order == null) {
		LOGGER.error('order_not_found');
		DatabaseSource.getDatabasePower().rollbackTransaction();
		return NotifyObj.build('-1', '交易出票失败', '3001', '交易订单未找到' + on, null);
	}
		
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(order.getStore_id());
	
	if(cinemaAuthInfo == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		LOGGER.error(Codes.COMMON_CINEMA_CONFIG_ERROR.msg);
		order.order_status=OrderConstants._order_status_payed_pub_failed_;
		order.setPay_type(pt);
		try {
			order.pay_amount = new BigDecimal(pay_amount);
		} catch(any) {
		}
		OrderService.INSTANCE.addOuUpdateOrder(order);
		return NotifyObj.build('-1', '交易出票失败', '3002', '未配置影院系统信息', null);
	}
	
	String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
	String channel_url = cinemaAuthInfo.getAuthChannelUrl()
	String channel_num = cinemaAuthInfo.getAuthChannelNo()
	String channel_code = cinemaAuthInfo.getAuthChannelCode()
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
	
	//SscOp ssc_op = SscFactory.generateSscOp(auth_model);
	SscOp ssc_op = SscOpFactory.generateSscOp(auth_model, order.user_id);
	println 'ssc_op proxy======' + ssc_op;
	
	SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
	Date start_time = datesdf.parse(order.getPlan_date());
	Calendar c = Calendar.getInstance(TimeZone.getDefault());
	c.setTime(start_time);
	c.add(Calendar.DATE, 1);
	Date end_time = c.getTime();
	CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
	CinemaPlan matchPlan = null;
	for(CinemaPlan cplan : planResult.getResult()) {
		if(cplan.getId().equals(order.getPlan_id())) {
			matchPlan = cplan;
		}
	}
	
	def lowprice = matchPlan.lowestPrice;
	def settle_price = matchPlan.settlePrice;
	
	List<SeatPub> seat_nos = new ArrayList<SeatPub>();
	for(DataTcSsSaleOrderSeat oseat : seats) {
		SeatPub seat = new SeatPub();
		seat.setAllowance("0.00")
		seat.setHandle_fee(oseat.getHandle_fee())
		//seat.setPay_amount(oseat.getSale_fee())
		//改取出票金额
		seat.setPay_amount(oseat.getSale_fee());
		seat.setSeat_id(oseat.getSeat_no())
		seat.setSettlePrice(oseat.getPub_fee())
		seat_nos.add(seat);
	}
	
	def order_no = order.order_no;
	
	RemoteTicketResult remoteResult = ssc_op.process_ticket(cinemaAuthInfo.getCinemaCode(), order.getPlan_id(), order_no, order_no, 
		remote.getLock_flag(), seat_nos, matchPlan.getCineUpdateTime(), null, MoneyUtil.divide(order.getTotal_amount(), "100"));
	
	if(!"0".equals(remoteResult.getCode())) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		LOGGER.error(Codes.CINEMA_CONN_FAILED.msg);
		order.order_status=OrderConstants._order_status_payed_pub_failed_;
		order.setPay_type(pt);
		try {
			order.pay_amount = new BigDecimal(pay_amount);
		} catch(any) {
		}
		OrderService.INSTANCE.addOuUpdateOrder(order);
		return NotifyObj.build('-1', '交易出票失败', '9001', remoteResult.getCode() + remoteResult.getMsg(), null);
	}
	order.setPay_type(pt);
	order.setOrder_status(OrderConstants._order_status_delivering_);
	remote.setTicket_flag_1(remoteResult.getValue1())
	remote.setTicket_flag_2(remoteResult.getValue2())
	try {
		order.pay_amount = new BigDecimal(pay_amount);
		if(remoteResult.rmCode=='000001') {
			order.setOrder_status('2900');
		}
	} catch(any) {
	}
	OrderService.INSTANCE.addOuUpdateOrder(order);
	OrderService.INSTANCE.addOrUpdateRemote(remote);
	
	DatabaseSource.getDatabasePower().commitTransaction();
	
	return NotifyObj.succ();
} else if(code_type=='2010') {
	//预约订单 出票接口
	
	DatabaseSource.getDatabasePower().beginTransaction();
	DataTcSsReserveOrderInfo order = OrderService.INSTANCE.getReserveOrderByNo(on);
	DataTcSsReserveOrderRemote remote = OrderService.INSTANCE.getReserveRemoteByOrderNo(on);
	List<DataTcSsReserveOrderSeat> seats = OrderService.INSTANCE.getReserveSeatsByOrderNo(on);
	order.order_status = OrderConstants._order_status_payed_;
	if(order == null) {
		LOGGER.error('order_not_found');
		DatabaseSource.getDatabasePower().rollbackTransaction();
		return NotifyObj.build('-1', '交易出票失败', '3001', '交易订单未找到' + on, null);
	}
		
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(order.getStore_id());
	
	if(cinemaAuthInfo == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		LOGGER.error(Codes.COMMON_CINEMA_CONFIG_ERROR.msg);
		order.order_status = OrderConstants._order_status_payed_pub_failed_;
		order.setPay_type(pt);
		try {
			order.pay_amount = new BigDecimal(pay_amount);
		} catch(any) {
		}
		OrderService.INSTANCE.addOuUpdateReserveOrder(order);
		return NotifyObj.build('-1', '交易出票失败', '3002', '未配置影院系统信息', null);
	}
	
	String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
	String channel_url = cinemaAuthInfo.getAuthChannelUrl()
	String channel_num = cinemaAuthInfo.getAuthChannelNo()
	String channel_code = cinemaAuthInfo.getAuthChannelCode()
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
	
	SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
	
	SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
	Date start_time = datesdf.parse(order.getPlan_date());
	Calendar c = Calendar.getInstance(TimeZone.getDefault());
	c.setTime(start_time);
	c.add(Calendar.DATE, 1);
	Date end_time = c.getTime();
	CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
	CinemaPlan matchPlan = null;
	for(CinemaPlan cplan : planResult.getResult()) {
		if(cplan.getId().equals(order.getPlan_id())) {
			matchPlan = cplan;
		}
	}
	
	List<SeatPub> seat_nos = new ArrayList<SeatPub>();
	for(DataTcSsReserveOrderSeat oseat : seats) {
		SeatPub seat = new SeatPub();
		seat.setAllowance("0.00")
		seat.setHandle_fee(oseat.getHandle_fee())
		seat.setPay_amount(oseat.getSale_fee())
		seat.setSeat_id(oseat.getSeat_no())
		seat.setSettlePrice(oseat.getPub_fee())
		seat_nos.add(seat);
	}
	
	def order_no = order.order_no;
	
	RemoteTicketResult remoteResult = ssc_op.process_ticket(cinemaAuthInfo.getCinemaCode(), order.getPlan_id(), order_no, order_no, remote.getLock_flag(), seat_nos, matchPlan.getCineUpdateTime(), null, MoneyUtil.divide(order.getTotal_amount(), "100"));
	
	if(!"0".equals(remoteResult.getCode())) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		LOGGER.error(Codes.CINEMA_CONN_FAILED.msg);
		order.order_status=OrderConstants._order_status_payed_pub_failed_;
		order.setPay_type(pt);
		try {
			order.pay_amount = new BigDecimal(pay_amount);
		} catch(any) {
		}
		
		OrderService.INSTANCE.addOuUpdateReserveOrder(order);
		return NotifyObj.build('-1', '交易出票失败', '9001', remoteResult.getCode() + remoteResult.getMsg(), null);
	}
	
	order.setOrder_status(OrderConstants._order_status_delivering_);
	order.setPay_type(pt);
	remote.setTicket_flag_1(remoteResult.getValue1())
	remote.setTicket_flag_2(remoteResult.getValue2())
	try {
		order.pay_amount = new BigDecimal(pay_amount);
	} catch(any) {
	}
	OrderService.INSTANCE.addOuUpdateReserveOrder(order);
	OrderService.INSTANCE.addOrUpdateReserveRemote(remote);
	
	DatabaseSource.getDatabasePower().commitTransaction();
	
	return NotifyObj.succ();
}




