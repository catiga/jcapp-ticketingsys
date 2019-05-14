package com.jeancoder.ticketingsys.internal.incall.order

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleInfo
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveInfo
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.jeancoder.ticketingsys.ready.entity.SaleOrder

Result result = new Result();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName())
def p = CommunicationSource.getParameter("orders");
def pid = CommunicationSource.getParameter("pid");
try {
	if (StringUtil.isEmpty(p)||StringUtil.isEmpty(pid)) {
		return SimpleAjax.notAvailable('参数不可为空');
	}
	JcTemplate jcTemplate = JcTemplate.INSTANCE()
	String saleSql = "select * from SaleOrder where proj_id=? and order_no=? and o_c=? and flag!=?";
	String reserveSql = "select * from TicketReserveInfo where proj_id=? and order_no=? and o_c=? and flag!=?";
	def resultList = [];
	String [] list = p.split(";");//参数为："编号+oc"
	for (int i = 0 ; i<list.length;i++) {
		def item = list[i].split(",");
		if (item[1].equals('2000')) {//开卡订单
			SaleOrder order = jcTemplate.get(SaleOrder, saleSql, pid,item[0],item[1],-1);
			if (order == null) {
				continue;
			}
			order.order_status = OrderConstants._order_status_closed_;
			order.c_time = new Timestamp(new Date().getTime());
			jcTemplate.update(order);
			resultList.add(order);
		}else if(item[1].equals('2010')){ 
			TicketReserveInfo order = jcTemplate.get(TicketReserveInfo, reserveSql, pid,item[0],item[1],-1);
			if (order == null) {
				continue;
			}
			order.order_status = OrderConstants._order_status_closed_;
			order.c_time = new Timestamp(new Date().getTime());
			jcTemplate.update(order);
			resultList.add(order);
		}
	}
	return SimpleAjax.available("",resultList);
} catch (Exception e) {
	Logger.error("修改订单状态失败", e);
	return SimpleAjax.notAvailable("修改订单状态失败");
}