package com.jeancoder.ticketingsys.ready.order.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname="data_tc_ss_reserve_order_remote")
class TicketReserveRemote  {
	@JCID
	BigInteger id;
	BigInteger order_id;
	String lock_flag;
	String ticket_flag_1;
	String ticket_flag_2;
	String ticket_refund;//是否退票  remote
	Timestamp c_time;
	Integer flag;
}
