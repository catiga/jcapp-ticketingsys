package com.jeancoder.ticketingsys.ready.dto

import java.sql.Timestamp

class TradeInfo {

	BigInteger id;
	
	BigInteger pid;
	
	BigInteger log_id;
	
	String tnum;
	
	String tss;
	
	Timestamp a_time;
	
	Timestamp c_time;
	
	Timestamp pay_time;
	
	//这里映射为主支付方式
	String pay_type;
	
	Integer flag = 0;
	
	BigDecimal total_amount;
	
	BigDecimal pay_amount;
	
	BigDecimal handle_fee = new BigDecimal(0);
	
	BigDecimal service_fee = new BigDecimal(0);
	
	String tname;
	
	String tbody;
	
	BigInteger storeid;
	
	String storename;
	
	BigInteger buyerid;
	
	String buyerphone;
	
	String buyername;
}
