package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_lock_order_info')
class LockOrder {

	@JCID
	BigInteger id;
	
	String order_no;
	
	String order_status;
	
	String total_amount;
	
	String pay_amount;
	
	Timestamp a_time;
	
	Integer ticket_sum;
	
	BigInteger store_id;
	
	String store_name;
	
	String hall_id;
	
	String hall_name;
	
	String plan_id;
	
	String plan_date;
	
	String plan_time;
	
	String film_name;
}
