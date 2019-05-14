package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "data_tc_ss_handle_fee_setting")
class TicketHandleFee {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger store_id;
	
	@JCForeign
	BigInteger store_basic;
	
	@JCForeign
	BigInteger hall_id;
	
	String hall_num;
	
	BigDecimal fee;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	BigInteger pid;
}
