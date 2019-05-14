package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.TicketingSaleService
import com.jeancoder.ticketingsys.ready.order.dto.TicketSaleInfoDto
import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveInfo
import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveSeat
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleSeat

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
try {
	def order_no = StringUtil.trim((String)CommunicationSource.getParameter("order_no"));

	TicketReserveInfo order_item = TicketingSaleService.INSTANCE.get_ticketingsysreserveinfo_item(order_no);
	TicketSaleInfoDto dto = new TicketSaleInfoDto(order_item);
	//添加座位
	List<TicketReserveSeat> tsdtoList= TicketingSaleService.INSTANCE.getreserveTicketSeat(order_item.id);
	def seats = "";
	if(!tsdtoList.equals(null)&&!tsdtoList.equals("")){
		for(TicketSaleSeat tsdto:tsdtoList){
			seats += tsdto.seat_sr+'排'+tsdto.getSeat_sc()+'座' + ",";
		}
	}
	seats = seats.substring(0,seats.length()-1);
	dto.seats = seats;
	return  SimpleAjax.available('',dto);
} catch (e) {
	Logger.error("exception",e);
	return SimpleAjax.notAvailable("选座订单查询失败");
}