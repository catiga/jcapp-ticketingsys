package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_sale_order_seat')
class SaleSeat {

	@JCID
	BigInteger id;
	
	BigInteger order_id;
	
	BigInteger seat_id;
	
	String seat_no;
	
	Integer seat_gr;
	
	Integer seat_gc;
	
	String seat_sr;
	
	String seat_sc;
	
	Timestamp c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
	
	Integer flag = 0;
	
	String went_status = '00';
	
	String handle_fee;
	
	String sale_fee;
	
	String pub_fee;
	
	BigDecimal service_fee = new BigDecimal(0);
	
	BigInteger tclass_id;	//单座的销售票类ID
	
}
