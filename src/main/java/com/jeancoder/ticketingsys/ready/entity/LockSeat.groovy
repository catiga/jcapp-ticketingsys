package com.jeancoder.ticketingsys.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_lock_order_seat')
class LockSeat {

	@JCID
	BigInteger id;
	
	BigInteger order_id;
	
	String seat_no;
	
	String seat_sr;
	
	String seat_sc;
	
}
