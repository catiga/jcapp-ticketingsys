package com.jeancoder.ticketingsys.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_lock_order_remote')
class LockCode {

	@JCID
	BigInteger id;
	
	BigInteger order_id;
	
	String lock_flag;
	
	String ticket_flag_1;
	
	String ticket_flag_2;
	
	String ticket_refund;
}
