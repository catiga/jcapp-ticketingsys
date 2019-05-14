package com.jeancoder.ticketingsys.ready.seatdetail.entity

import com.jeancoder.jdbc.bean.JCID

class HallInformation {
	@JCID
	BigInteger id;
	Long hall_id;
	Long cinema_id;
	String ticket_type;
	Integer flag;
}
