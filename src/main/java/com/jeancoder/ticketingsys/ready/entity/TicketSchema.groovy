package com.jeancoder.ticketingsys.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'ticket_price_schema')
class TicketSchema {

	@JCID
	BigInteger id;
	
	String schema_name;
	
	Integer flag = 0;
}
