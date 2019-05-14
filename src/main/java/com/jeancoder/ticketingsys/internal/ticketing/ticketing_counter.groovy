package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.ticketingsys.ready.order.Ticketing_Counter_Service
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.order.dto.ticketing.ticketsale.TicketGatherDto
import com.jeancoder.ticketingsys.ready.order.dto.ticketing.ticketsale.TicketSalePage
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleInfo
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleRemote
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleSeat

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
TicketSalePage page  = new TicketSalePage();
page.available = false;
def pn = StringUtil.trim((String)CommunicationSource.getParameter("pn"));
def ps = StringUtil.trim((String)CommunicationSource.getParameter("ps"));
def pid = StringUtil.trim((String)CommunicationSource.getParameter("pid"));
def start_data = StringUtil.trim((String)CommunicationSource.getParameter("start_data"))
def end_data = StringUtil.trim((String)CommunicationSource.getParameter("end_data"))
def order_status=StringUtil.trim((String)CommunicationSource.getParameter("order_status"))

try{
	if (StringUtil.isEmpty(pn)) {
		pn = "1";
	}
	pn = Integer.parseInt(pn);
	if (StringUtil.isEmpty(ps)) {
		ps = "10";
	}
	ps = Integer.parseInt(ps);
	if (StringUtil.isEmpty(pid)) {
		pid = null;
	}
	if (StringUtil.isEmpty(start_data)) {
		start_data = "";
	}
	if (StringUtil.isEmpty(end_data)) {
		end_data = "";
	}
	JcPage<TicketSaleInfo> orderPage  = new JcPage<TicketSaleInfo>();
	orderPage.pn = pn;
	orderPage.ps = ps;
	orderPage = Ticketing_Counter_Service.INSTANCE.getTicketReserveList(orderPage, pid, start_data, end_data, order_status);
	//处理数据
	List<TicketGatherDto> list = new ArrayList<TicketGatherDto>();
	for(TicketSaleInfo order:orderPage.result){
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
		TicketSaleRemote trdto = Ticketing_Counter_Service.INSTANCE.getTicketRemote(order.id);
		tgdto.ticket_refund = trdto.ticket_refund;
		//添加座位信息
		List<TicketSaleSeat> tsdtoList= Ticketing_Counter_Service.INSTANCE.getTicketSeat(order.id);
		tgdto.ticketSeat = tsdtoList;
		list.add(tgdto)
	}
	page.result = list;
	page.pn = orderPage.pn;
	page.ps = orderPage.ps;
	page.totalCount = orderPage.totalCount;
	page.available = true;
}catch(Exception e){
	Logger.error("票务收银台查询失败",e);
	page.available = false;
	page.msg = "查询失败";
}
return page;

