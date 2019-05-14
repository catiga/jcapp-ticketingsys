package com.jeancoder.ticketingsys.ready.seatdetail.entity

import java.security.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

class SeatingDetails {
	@JCID
	BigInteger id;
	Long dhi_id;
	String seat_no;
	String seat_gr;
	String seat_gc;
	String seat_type;
	String seat_status;
	Date a_time;
	Integer flag;
}
