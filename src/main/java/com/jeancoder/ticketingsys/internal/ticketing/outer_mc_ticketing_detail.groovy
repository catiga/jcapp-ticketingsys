package com.jeancoder.ticketingsys.internal.ticketing

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.TicketHandleFee
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.TicketingSaleService
import com.jeancoder.ticketingsys.ready.order.dto.OrderSeatItemDto
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderSeat
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleInfo
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanResult

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
try {
	def order_no = JC.internal.param("no");
	def oc = JC.internal.param("oc");
	def pid = JC.internal.param("pid");
	def g_list = [];
	def c_id = null;
	def play_no = null;
	def lock_flag = null;
	def update_time = null;
	if ('2000'.equals(oc)) {
		DataTcSsSaleOrderInfo tcss_order = OrderService.INSTANCE.getOrderByNo(order_no);
		List<OrderSeatItemDto> items = OrderService.INSTANCE.getOrderSeats(tcss_order.id);
		DataTcSsSaleOrderRemote remote = OrderService.INSTANCE.getRemoteByOrderNo(order_no);
		if(tcss_order == null) {
			return SimpleAjax.notAvailable("订单不存在")
		}
		
		
		/**获取影城手续费配置相关**/
		List<TicketHandleFee> handle_fees = JcTemplate.INSTANCE().find(TicketHandleFee, 'select * from TicketHandleFee where flag!=? and store_id=? and hall_num in(?,?)', -1, tcss_order.store_id, tcss_order.hall_id, -1);
		def store_handle_fee = new BigDecimal('0');
		def hall_service_fee = new BigDecimal('0');
		if(handle_fees) {
			for(x in handle_fees) {
				if(x.hall_num==null || x.hall_num=='-1') {
					//影城默认手续费
					store_handle_fee = x.fee;
					break;
				}
			}
			for(x in handle_fees) {
				if(x.hall_num==tcss_order.hall_id) {
					hall_service_fee = x.fee;
					break;
				}
			}
		}
		Logger.info('handle_fees===' + tcss_order.store_id + ", " + handle_fees.toString());
		Logger.info('store_handle_fee type is : ' + store_handle_fee.class + ' and value is: ' + store_handle_fee);
		/**获取影城手续费配置相关**/
		
		BigDecimal pref_amount = new BigDecimal(0);
		SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start_time = datesdf.parse(tcss_order.getPlan_date());
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTime(start_time);
		c.add(Calendar.DATE, 1);
		Date end_time = c.getTime();
		CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(tcss_order.getStore_id());
		CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
		CinemaPlan matchPlan = null;
		for(CinemaPlan cplan : planResult.getResult()) {
			if(cplan.getId().equals(tcss_order.getPlan_id())) {
				matchPlan = cplan;
			}
		}
		for(x in items) {
			def sale_fee = x.sale_fee;
			def pub_fee = x.pub_fee;
			pref_amount = pref_amount.add(new BigDecimal(x.sale_fee).subtract(new BigDecimal(sale_fee)));
			g_list.add([x.seat_no, sale_fee,pub_fee, tcss_order.hall_id, store_handle_fee]);	//增加手续费字段
		}
		StoreInfo store = StoreService.INSTANCE.getById(tcss_order.store_id);
		c_id = store.store_no;
		play_no = tcss_order.getPlan_id();
		lock_flag = remote.getLock_flag();
		update_time = matchPlan.getCineUpdateTime();
	} else  if ('2010'.equals(oc)) {
		DataTcSsReserveOrderInfo tcss_order = OrderService.INSTANCE.getReserveOrderByNo(order_no);
		DataTcSsReserveOrderRemote remote = OrderService.INSTANCE.getReserveRemoteByOrderNo(order_no);
		List<DataTcSsReserveOrderSeat> items = OrderService.INSTANCE.getReserveSeatsByOrderNo(order_no);
		if(tcss_order == null) {
			return SimpleAjax.notAvailable("订单不存在")
		}
		BigDecimal pref_amount = new BigDecimal(0);
		SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
		Date start_time = datesdf.parse(tcss_order.getPlan_date());
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		c.setTime(start_time);
		c.add(Calendar.DATE, 1);
		Date end_time = c.getTime();
		CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(tcss_order.getStore_id());
		CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
		CinemaPlan matchPlan = null;
		for(CinemaPlan cplan : planResult.getResult()) {
			if(cplan.getId().equals(tcss_order.getPlan_id())) {
				matchPlan = cplan;
			}
		}
		for(x in items) {
			def sale_fee = x.sale_fee;
			def pub_fee = x.pub_fee;
			pref_amount = pref_amount.add(new BigDecimal(x.sale_fee).subtract(new BigDecimal(sale_fee)));
			g_list.add([x.seat_no, sale_fee,pub_fee, tcss_order.hall_id]);
		}
		StoreInfo store = StoreService.INSTANCE.getById(tcss_order.store_id);
		c_id = store.store_no;
		play_no = tcss_order.getPlan_id();
		lock_flag = remote.getLock_flag();
		update_time = matchPlan.getCineUpdateTime();
	}  else {
		return SimpleAjax.notAvailable("订单不存在")
	}
	def param = [:];
	param['g'] = g_list;
	param['c_id'] = c_id;
	param['play_no'] = play_no;
	param['lock_flag'] = lock_flag;
	
	param['update_time'] = update_time;
	param['play_no'] = play_no;
	return SimpleAjax.available("",param);
} catch (e) {
	Logger.error("exception",e);
	return SimpleAjax.notAvailable("查询影院信息失败")
}