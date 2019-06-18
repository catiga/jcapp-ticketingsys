package com.jeancoder.ticketingsys.ready.order

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.order.constants.SeatWentStatus
import com.jeancoder.ticketingsys.ready.order.dto.OrderSeatItemDto
import com.jeancoder.ticketingsys.ready.order.dto.OrderWithSeatDto
import com.jeancoder.ticketingsys.ready.order.dto.OrderWithTicketDto
import com.jeancoder.ticketingsys.ready.order.dto.ReserveSeatItemDto
import com.jeancoder.ticketingsys.ready.order.dto.ReserveWithSeatDto
import com.jeancoder.ticketingsys.ready.order.dto.SeatOrderDto
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsLockOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsLockOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsLockOrderSeat
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsReserveOrderSeat
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderInfo
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderRemote
import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderSeat
import com.jeancoder.ticketingsys.ready.support.Page

class OrderService {
	public static final OrderService INSTANCE = new OrderService();
	
	public void addOuUpdateOrder(DataTcSsSaleOrderInfo order) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(order, "id");
	}
	
	public void addOrUpdateSeat(DataTcSsSaleOrderSeat seat) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(seat, "id");
		
	}
	public void addOrUpdateRemote(DataTcSsSaleOrderRemote remote) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(remote, "id");
	}
	
	public void addOuUpdateReserveOrder(DataTcSsReserveOrderInfo order) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(order, "id");
	}
	
	public void addOrUpdateReserveSeat(DataTcSsReserveOrderSeat seat) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(seat, "id");
		
	}
	public void addOrUpdateReserveRemote(DataTcSsReserveOrderRemote remote) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(remote, "id");
	}
	
	public void addOuUpdateLockOrder(DataTcSsLockOrderInfo order) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(order, "id");
	}
	
	public void addOrUpdateLockSeat(DataTcSsLockOrderSeat seat) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(seat, "id");
		
	}
	public void addOrUpdateLockRemote(DataTcSsLockOrderRemote remote) {
		DatabaseSource.getDatabasePower().doUpdateSerialize(remote, "id");
	}
	
	public DataTcSsSaleOrderInfo getOrderById(def id) {
		String sql = "SELECT * FROM data_tc_ss_sale_order_info WHERE id = ?";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsSaleOrderInfo.class, sql, id);
	}
	
	public DataTcSsSaleOrderInfo getOrderByNo(String orderNo) {
		String sql = "SELECT * FROM data_tc_ss_sale_order_info WHERE order_no = ?";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsSaleOrderInfo.class, sql,orderNo);
	}
	
	public DataTcSsSaleOrderRemote getRemoteByOrderNo(String orderNo) {
		String sql = "SELECT * from data_tc_ss_sale_order_remote WHERE order_id = (SELECT id FROM data_tc_ss_sale_order_info WHERE order_no = ?)";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsSaleOrderRemote.class, sql,orderNo);
	}
	
	public List<DataTcSsSaleOrderSeat> getSeatsByOrderNo(String orderNo){
		String sql = "SELECT * FROM data_tc_ss_sale_order_seat WHERE order_id = (SELECT id FROM data_tc_ss_sale_order_info WHERE order_no = ?)";
		return DatabaseSource.getDatabasePower().doQueryList(DataTcSsSaleOrderSeat.class, sql, orderNo);
	}
	
	public DataTcSsReserveOrderInfo getReserveOrderById(def id) {
		String sql = "SELECT * FROM data_tc_ss_reserve_order_info WHERE id = ?";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsReserveOrderInfo.class, sql, id);
	}
	
	public DataTcSsReserveOrderInfo getReserveOrderByNo(String orderNo) {
		String sql = "SELECT * FROM data_tc_ss_reserve_order_info WHERE order_no = ?";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsReserveOrderInfo.class, sql,orderNo);
	}
	
	public DataTcSsReserveOrderRemote getReserveRemoteByOrderNo(String orderNo) {
		String sql = "SELECT * from data_tc_ss_reserve_order_remote WHERE order_id = (SELECT id FROM data_tc_ss_reserve_order_info WHERE order_no = ?)";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsReserveOrderRemote.class, sql,orderNo);
	}
	
	public List<DataTcSsReserveOrderSeat> getReserveSeatsByOrderNo(String orderNo){
		String sql = "SELECT * FROM data_tc_ss_reserve_order_seat WHERE order_id = (SELECT id FROM data_tc_ss_reserve_order_info WHERE order_no = ?)";
		return DatabaseSource.getDatabasePower().doQueryList(DataTcSsReserveOrderSeat.class, sql, orderNo);
	}
	
	public DataTcSsLockOrderInfo getLockOrderByNo(String orderNo) {
		String sql = "SELECT * FROM data_tc_ss_lock_order_info WHERE order_no = ?";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsLockOrderInfo.class, sql,orderNo);
	}
	
	public DataTcSsLockOrderRemote getLockRemoteByOrderNo(String orderNo) {
		String sql = "SELECT * from data_tc_ss_lock_order_remote WHERE order_id = (SELECT id FROM data_tc_ss_lock_order_info WHERE order_no = ?)";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsLockOrderRemote.class, sql,orderNo);
	}
	
	public List<DataTcSsLockOrderSeat> getLockSeatsByOrderNo(String orderNo){
		String sql = "SELECT * FROM data_tc_ss_lock_order_seat WHERE order_id = (SELECT id FROM data_tc_ss_lock_order_info WHERE order_no = ?)";
		return DatabaseSource.getDatabasePower().doQueryList(DataTcSsLockOrderSeat.class, sql, orderNo);
	}
	
	public List<OrderWithSeatDto> getOrders(Page page,String phone_number,String order_no){
		StringBuffer statusin = new StringBuffer();
		for(String status : OrderConstants.payed_orders()) {
			if(statusin.length() > 0) {
				statusin.append(",");
			}
			statusin.append("'"+status+"'");
		}
		String sql = "SELECT * FROM data_tc_ss_sale_order_info WHERE 1=1 "
		if(phone_number != null && !"".equals(phone_number)) {
			sql += " AND mobile like '%"+phone_number+"%'"
		}
		if(order_no != null && !"".equals(order_no)) {
			sql += " AND order_no like '%"+order_no+"%'"
		}
		sql += " AND order_status in ("+statusin.toString()+") ORDER BY a_time desc LIMIT "+ page.limitExp();
		List<OrderWithSeatDto> orders = DatabaseSource.getDatabasePower().doQueryList(OrderWithSeatDto.class, sql);
		if(orders != null) {
			for(OrderWithSeatDto o : orders) {
				o.setSeats(getOrderSeats(o.getId()))
			}
		}
		return orders;
	}
	
	public List<OrderWithSeatDto> getOrdersByApId(Page page, BigInteger ap_id, String order_no){
		StringBuffer statusin = new StringBuffer();
		for(String status : OrderConstants.payed_orders()) {
			if(statusin.length() > 0) {
				statusin.append(",");
			}
			statusin.append("'"+status+"'");
		}
		//增加另外三种情况
		statusin.append(",'2900','2901','3900','3901'");
		
		String sql = "SELECT * FROM data_tc_ss_sale_order_info WHERE 1=1";
		if(ap_id) {
			sql += " and user_id=" + ap_id;
		}
		
		if(order_no != null && !"".equals(order_no)) {
			sql += " AND order_no like '%"+order_no+"%'"
		}
		sql += " AND order_status in ("+statusin.toString()+") ORDER BY a_time desc LIMIT "+ page.limitExp();
		List<OrderWithSeatDto> orders = DatabaseSource.getDatabasePower().doQueryList(OrderWithSeatDto.class, sql);
		if(orders != null) {
			for(OrderWithSeatDto o : orders) {
				o.setSeats(getOrderSeats(o.getId()))
			}
		}
		return orders;
	}
	
	public OrderWithTicketDto getOrderWithTicketByNo(String orderNo) {
		String sql = "SELECT * FROM data_tc_ss_sale_order_info WHERE 1=1 AND order_no = ? ORDER BY a_time DESC LIMIT 1";
		OrderWithTicketDto order = DatabaseSource.getDatabasePower().doQueryUnique(OrderWithTicketDto.class, sql, orderNo);
		if(order != null) {
			order.setSeats(getOrderSeats(order.getId()))
			order.setTicket(getRemoteByOrderNo(order.getOrder_no()))
		}
		return order;
	}
	
	public List<OrderSeatItemDto> getOrderSeats(Long orderId){
		String sql = "SELECT * FROM data_tc_ss_sale_order_seat WHERE order_id = ?";
		return DatabaseSource.getDatabasePower().doQueryList(OrderSeatItemDto.class,sql, orderId);
	}
	
	public List<ReserveWithSeatDto> getReserves(Page page,String phone_number,String order_no){
		StringBuffer statusin = new StringBuffer();
		for(String status : OrderConstants.created_orders()) {
			if(statusin.length() > 0) {
				statusin.append(",");
			}
			statusin.append("'"+status+"'");
		}
		String sql = "SELECT * FROM data_tc_ss_reserve_order_info WHERE 1=1 "
		if(phone_number != null && !"".equals(phone_number)) {
			sql += " AND mobile like '%"+phone_number+"%'"
		}
		if(order_no != null && !"".equals(order_no)) {
			sql += " AND order_no like '%"+order_no+"%'"
		}
		sql += " AND order_status in ("+statusin.toString()+") ORDER BY a_time desc LIMIT "+ page.limitExp();
		List<ReserveWithSeatDto> orders = DatabaseSource.getDatabasePower().doQueryList(ReserveWithSeatDto.class, sql);
		if(orders != null) {
			for(ReserveWithSeatDto o : orders) {
				o.setSeats(getReserveSeats(o.getId()))
			}
		}
		return orders;
	}
	
	public List<ReserveSeatItemDto> getReserveSeats(Long reserveId){
		String sql = "SELECT * FROM data_tc_ss_reserve_order_seat WHERE order_id = ?";
		return DatabaseSource.getDatabasePower().doQueryList(ReserveSeatItemDto.class,sql, reserveId);
	}
	
	/**
	 * 获取作为当前匹配的有效订单
	 * @param planId
	 * @param seatId
	 * @return
	 */
	public SeatOrderDto getSeatCurrentOrder(String planId,String seatId) {
		String sql = '''
			select
			a.*
			from
			data_tc_ss_reserve_order_info a
			join
			data_tc_ss_reserve_order_seat b
			on b.order_id = a.id
			where a.order_status = ?
			and a.plan_id = ?
			and b.seat_no = ?
		''';
		SeatOrderDto corder = DatabaseSource.getDatabasePower().doQueryUnique(SeatOrderDto.class, sql,OrderConstants._order_status_create_,planId,seatId);
		return corder;
	}
	
	public SeatOrderDto getSeatPrelockOrder(String planId,String seatId) {
		String sql = '''
			select
			a.*
			from
			data_tc_ss_lock_order_info a
			join
			data_tc_ss_lock_order_seat b
			on b.order_id = a.id
			where a.order_status = ?
			and a.plan_id = ?
			and b.seat_no = ?
		''';
		SeatOrderDto corder = DatabaseSource.getDatabasePower().doQueryUnique(SeatOrderDto.class, sql,OrderConstants._order_status_create_,planId,seatId);
		return corder;
	}
	
	public SeatOrderDto getSeatBuyedOrder(String planId,String seatId) {
		String str = OrderConstants._order_status_taked_ + "," + OrderConstants._order_status_delivering_ + "," + OrderConstants._order_status_delivering_nopub_+","+OrderConstants._order_status_delivering_nopub_ok_+",3900,3901"
					
		String sql = '''
			select
			a.*
			from
			data_tc_ss_sale_order_info a
			join
			data_tc_ss_sale_order_seat b
			on b.order_id = a.id
			where a.order_status in (''' + str +''')
			and a.plan_id = ?
			and b.seat_no = ?
		''';
		SeatOrderDto corder = DatabaseSource.getDatabasePower().doQueryUnique(SeatOrderDto.class, sql,planId,seatId);
		return corder;
	}
	
	
	public SeatOrderDto getSeatLockedOrder(String planId,String seatId) {
		String sql = '''
			select
			a.*
			from
			data_tc_ss_sale_order_info a
			join
			data_tc_ss_sale_order_seat b
			on b.order_id = a.id
			where a.order_status = ?
			and a.plan_id = ?
			and b.seat_no = ?
			order by a_time desc
			limit 1
		''';
		SeatOrderDto corder = DatabaseSource.getDatabasePower().doQueryUnique(SeatOrderDto.class, sql,OrderConstants._order_status_create_,planId,seatId);
		return corder;
	}
	
	public DataTcSsReserveOrderInfo getReserveByNo(String orderNo) {
		String sql = "SELECT * FROM data_tc_ss_reserve_order_info WHERE order_no = ?";
		return DatabaseSource.getDatabasePower().doQueryUnique(DataTcSsReserveOrderInfo.class, sql,orderNo)
	}
	
	
	public int refOrderSeat(String order_no,String seat_id) {
		String sql = '''
			update 
			data_tc_ss_sale_order_info a
			join
			data_tc_ss_sale_order_seat b
			on b.order_id = a.id
			set b.went_status = ?
			WHERE a.order_no = ? AND b.seat_no = ?
		'''
		DatabaseSource.getDatabasePower().doUpdate(sql, SeatWentStatus.REFUNDED,order_no,seat_id);
		
		String leftSeatNumSql = '''
			select count(1) from 
			data_tc_ss_sale_order_info a
			join
			data_tc_ss_sale_order_seat b
			on b.order_id = a.id
			WHERE b.went_status != ? AND a.order_no = ? AND b.seat_no != ?
		''';
		Integer ls = DatabaseSource.getDatabasePower().doQueryUniqueScalar(Integer.class, leftSeatNumSql,SeatWentStatus.REFUNDED,order_no,seat_id);
		return ls.intValue();
	}
	
	public int refReserveSeat(String order_no,String seat_id) {
		String sql = '''
			update 
			data_tc_ss_reserve_order_info a
			join
			data_tc_ss_reserve_order_seat b
			on b.order_id = a.id
			set b.went_status = ?
			WHERE a.order_no = ? AND b.seat_no = ?
		'''
		DatabaseSource.getDatabasePower().doUpdate(sql, SeatWentStatus.REFUNDED,order_no,seat_id);
		
		String leftSeatNumSql = '''
			select count(1) from 
			data_tc_ss_reserve_order_info a
			join
			data_tc_ss_reserve_order_seat b
			on b.order_id = a.id
			WHERE b.went_status != ? AND a.order_no = ? AND b.seat_no != ?
		''';
		Integer ls = DatabaseSource.getDatabasePower().doQueryUniqueScalar(Integer.class, leftSeatNumSql,SeatWentStatus.REFUNDED,order_no,seat_id);
		return ls.intValue();
	}
	
	public int refLockSeat(String order_no,String seat_id) {
		String sql = '''
			update 
			data_tc_ss_lock_order_info a
			join
			data_tc_ss_lock_order_seat b
			on b.order_id = a.id
			set b.went_status = ?
			WHERE a.order_no = ? AND b.seat_no = ?
		'''
		DatabaseSource.getDatabasePower().doUpdate(sql, SeatWentStatus.REFUNDED,order_no,seat_id);
		
		String leftSeatNumSql = '''
			select count(1) from 
			data_tc_ss_lock_order_info a
			join
			data_tc_ss_lock_order_seat b
			on b.order_id = a.id
			WHERE b.went_status != ? AND a.order_no = ? AND b.seat_no != ?
		''';
		Integer ls = DatabaseSource.getDatabasePower().doQueryUniqueScalar(Integer.class, leftSeatNumSql,SeatWentStatus.REFUNDED,order_no,seat_id);
		return ls.intValue();
	}
}
