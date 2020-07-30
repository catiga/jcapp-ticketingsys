package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_spec_sp_rule')
class TicketSalesRules {
	@JCID
	BigInteger id;
	String title;
	String info;
	String aptype;
	String apstatus;//10未开始  20进行中   21暂停    30结束
	BigInteger p_id;
	Date a_time;
	Timestamp c_time;
	Integer flag=0;
	Integer allow_low_price;
	Integer apindex;
	String price_streg;
	String time_streg;
	String movie_type;
	String movie_type_name;
	String store_type;
	String store_type_name;
	String content;
	String time_type;
	String hall_id;
	
	//排序规则
	//数字越小，优先级越低
	Integer seq = 0;
}
