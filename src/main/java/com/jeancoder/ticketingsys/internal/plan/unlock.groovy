package com.jeancoder.ticketingsys.internal.plan

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleRemote
import com.jeancoder.ticketingsys.ready.entity.SaleSeat
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.SscAuthModel


JCLogger logger = LoggerSource.getLogger();

Result result = new Result();

try {
	String order_no = JC.internal.param("order_no");
	
//	DataTcSsSaleOrderInfo order = OrderService.INSTANCE.getOrderByNo(order_no);
//	DataTcSsSaleOrderRemote remote = OrderService.INSTANCE.getRemoteByOrderNo(order_no);
//	List<DataTcSsSaleOrderSeat> seats = OrderService.INSTANCE.getSeatsByOrderNo(order_no);
	
	SaleOrder order = JcTemplate.INSTANCE().get(SaleOrder, 'select * from SaleOrder where order_no=?', order_no);
	if(order == null) {
		result.setData(Res.Failed(Codes.CINEMA_GENORDER_ORDERNO_NOTFOUND));
		return result;
	}
	
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(order.getStore_id().longValue());
	
	if(cinemaAuthInfo == null) {
		result.setData(Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR));
		return result;
	}
	
	SaleRemote remote = JcTemplate.INSTANCE().get(SaleRemote, 'select * from SaleRemote where order_id=?', order.id);
	List<SaleSeat> seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', order.id);
		
	String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
	String channel_url = cinemaAuthInfo.getAuthChannelUrl()
	String channel_num = cinemaAuthInfo.getAuthChannelNo()
	String channel_code = cinemaAuthInfo.getAuthChannelCode()
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
	
	SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
	
	List<String> unlock_seat_nos = new ArrayList<String>();
	for(SaleSeat oseat : seats) {
		unlock_seat_nos.add(oseat.getSeat_no());
	}
	
	boolean unres = ssc_op.batch_unlock(cinemaAuthInfo.getCinemaCode(), order.getPlan_id(), remote.getLock_flag(), unlock_seat_nos, order.getOrder_no());
	
	if(!unres) {
		result.setData(Res.Failed(Codes.CINEMA_GENORDER_UNLOCK_FAILED));
		return result;
	}
	
	order.setOrder_status(OrderConstants._order_status_remote_unlock_by_front_);
	JcTemplate.INSTANCE().update(order);
	//OrderService.INSTANCE.addOuUpdateOrder(order);
	
	result.setData(Res.Success(unres));
	return result;
}catch(Exception e) {
	logger.error('', e);
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}

