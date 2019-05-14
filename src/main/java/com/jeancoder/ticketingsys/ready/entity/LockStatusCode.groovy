package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'unregu_record')
class LockStatusCode {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger pid;
	
	String store_name;
	
	String hall_name;
	
	String film_name;
	
	String plan_id;
	
	String plan_date;
	
	String plan_time;
	
	String seats;
	
	@JCForeign
	BigInteger order_id;
	
	String order_no;
	
	String prot_code;
	
	String prot_info;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	String notify_mobiles;
	
	public String toString() {
		def x = this;
		return x.store_name + ',' + x.hall_name + ',' + x.film_name + ',' + x.plan_date + ' ' + x.plan_time + ',' + seats + ',（' + x.order_no + '）';
	}
}
