package com.jeancoder.ticketingsys.ready.order;

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveInfo
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleInfo
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleRemote
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleSeat

public class Ticketing_Counter_Service {
	public static final Ticketing_Counter_Service INSTANCE = new Ticketing_Counter_Service();
	private static final JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	
	def getTicketReserveList(JcPage<TicketSaleInfo> page,def pid,String start_data, String end_data,String order_status){
		StringBuilder sb = new StringBuilder();
		sb.append("select * from TicketSaleInfo where 1=1 ");
		if(!StringUtil.isEmpty(start_data)&&!StringUtil.isEmpty(end_data)){
			sb.append(" and (a_time between '"+start_data+"' and '"+end_data+"') ");
		}
		if(pid != null){
			sb.append(" and proj_id ="+pid);
		}
		sb.append(" and user_id is null ");
		sb.append(" and flag!="+ -1+" and order_status in"+"("+order_status+")"+" order by a_time desc ");
		page =  jcTemplate.find(TicketSaleInfo, page, sb.toString(),null);		
		return page;
	}
	
	def getTicketRemote(BigInteger order_id){
		String sql = "select * from TicketSaleRemote where order_id ="+order_id;
		return jcTemplate.get(TicketSaleRemote, sql, null);
	}
	
	def getTicketSeat(BigInteger order_id){
		String sql = "select * from TicketSaleSeat where order_id ="+order_id;
		return jcTemplate.find(TicketSaleSeat, sql, null);
	}
	
	public List<TicketSaleInfo> get_ticketingsys_saleinfo_item(def order_no){
		String sql = "select * from TicketSaleInfo where order_no = ?";
		return jcTemplate.find(TicketSaleInfo, sql, order_no);
	}
	
	public List<TicketReserveInfo> get_ticketingsys_reserveinfo_item(def order_no){
		String sql = "select * from TicketReserveInfo where order_no = ?";
		return jcTemplate.find(TicketReserveInfo, sql, order_no);
	}
	
}
