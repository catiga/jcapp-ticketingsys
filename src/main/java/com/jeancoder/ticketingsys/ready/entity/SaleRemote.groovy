package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_sale_order_remote')
class SaleRemote {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger order_id;
	
	String lock_flag;
	
	String ticket_flag_1;
	
	String ticket_flag_2;
	
	String ticket_refund;
	
	Timestamp c_time;
	
	Integer flag;
}
