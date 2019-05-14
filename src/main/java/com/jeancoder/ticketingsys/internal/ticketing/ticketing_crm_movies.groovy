package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.TicketingSaleService
import com.jeancoder.ticketingsys.ready.order.dto.TicketSaleInfoDto
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleInfo
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleSeat
import com.jeancoder.ticketingsys.ready.util.DateUtil

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
try {
	def order_no = StringUtil.trim((String)CommunicationSource.getParameter("order_no"));

	TicketSaleInfo order_item = TicketingSaleService.INSTANCE.get_ticketingsys_saleinfo_item_list(order_no);
	TicketSaleInfoDto dto = new TicketSaleInfoDto(order_item);
	//添加座位
	List<TicketSaleSeat> tsdtoList= TicketingSaleService.INSTANCE.getTicketSeat(order_item.id);
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