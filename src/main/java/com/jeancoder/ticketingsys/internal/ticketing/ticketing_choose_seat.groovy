package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.TicketingSaleService
import com.jeancoder.ticketingsys.ready.order.dto.ticketing.ticketsale.TicketGatherDto
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleInfo
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleRemote
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleSeat

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
List<TicketGatherDto> list = new ArrayList<TicketGatherDto>();
try {
	def order_no = JC.internal.param("order_no");
	def order_id = JC.internal.param('order_id');
	
	Logger.info("order_no_"+order_no)
	List<TicketSaleInfo> order_items = null;
	
	if(order_id) {
		order_items = JcTemplate.INSTANCE().find(TicketSaleInfo, "select * from TicketSaleInfo where id=?", order_id);
	} else {
		order_items = TicketingSaleService.INSTANCE.get_ticketingsys_saleinfo_item(order_no);
	}

	for(TicketSaleInfo order:order_items){
		TicketGatherDto tgdto = new TicketGatherDto();
		tgdto.order_no = order.order_no
		tgdto.store_name = order.store_name
		tgdto.hall_name = order.hall_name
		tgdto.film_name = order.film_name
		tgdto.plan_date = order.plan_date
		tgdto.c_time = order.c_time
		tgdto.order_status = order.order_status
		tgdto.pay_type = order.pay_type
		tgdto.ticket_sum = order.ticket_sum
		tgdto.total_amount = order.total_amount
		tgdto.pay_amount = order.pay_amount
		tgdto.handle_fee = order.handle_fee
		
		//添加是否退票
		TicketSaleRemote trdto = TicketingSaleService.INSTANCE.getTicketRemote(order.id);
		if(!trdto.equals(null)&&!trdto.equals("")){
			tgdto.ticket_refund = trdto.ticket_refund;
		}
		//添加座位
		List<TicketSaleSeat> tsdtoList= TicketingSaleService.INSTANCE.getTicketSeat(order.id);
		if(!tsdtoList.equals(null)&&!tsdtoList.equals("")){
			for(TicketSaleSeat tsdto:tsdtoList){
				tsdto.seat_no = tsdto.seat_sr+'排'+tsdto.getSeat_sc()+'座';
			}
		}
		tgdto.ticketSeat = tsdtoList;

		list.add(tgdto)
	}
	return SimpleAjax.available("", list);
} catch (e) {
	Logger.error("exception",e);
	return SimpleAjax.notAvailable("选座订单查询失败")
}