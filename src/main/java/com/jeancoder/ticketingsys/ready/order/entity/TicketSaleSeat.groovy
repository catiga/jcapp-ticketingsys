package com.jeancoder.ticketingsys.ready.order.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean

@JCBean(tbname="data_tc_ss_sale_order_seat")
class TicketSaleSeat  {
	BigInteger id;
	BigInteger order_id;
	BigInteger seat_id;
	String seat_no;//座位号
	Integer seat_gr;
	Integer seat_gc;
	String seat_sr;//排
	String seat_sc;//座
	Timestamp c_time;
	Integer flag;
	String went_status;
	String handle_fee;
	String sale_fee;
	String pub_fee;
}