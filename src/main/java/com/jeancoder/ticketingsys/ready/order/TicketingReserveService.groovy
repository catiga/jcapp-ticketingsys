package com.jeancoder.ticketingsys.ready.order;

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveInfo
import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveRemote
import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveSeat

public class TicketingReserveService {
	public static final TicketingReserveService INSTANCE = new TicketingReserveService();
	private static final JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	
	def getTicketReserveList(JcPage<TicketReserveInfo> page,def pid,String start_data, String end_data,String order_status){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from TicketReserveInfo where 1=1 ");
		if(!StringUtil.isEmpty(start_data)&&!StringUtil.isEmpty(end_data)){
			sb.append(" and (a_time between '"+start_data+"' and '"+end_data+"') ");
		}
		if(pid!=null){
			sb.append(" and proj_id ="+pid);
		}
		sb.append(" and flag!="+ -1+" and order_status in"+"("+order_status+")"+" order by a_time desc ");
		page =  jcTemplate.find(TicketReserveInfo, page, sb.toString(),null);		
		return page;
	}
	
	def getTicketRemote(BigInteger order_id){
		String sql = "select * from TicketReserveRemote where order_id ="+order_id;
		return jcTemplate.get(TicketReserveRemote, sql, null);
	}
	
	def getTicketSeat(BigInteger order_id){
		String sql = "select * from TicketReserveSeat where order_id ="+order_id;
		return jcTemplate.find(TicketReserveSeat, sql, null);
	}
}
