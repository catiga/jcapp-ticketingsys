package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_cinema_config')
class CinemaConfig {

	@JCID
	BigInteger id;
	
	BigInteger store_id;
	
	String store_cinema_num;
	
	String tc_code;
	
	String tc_name;
	
	String ss_code;
	
	String ss_name;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	BigInteger utimekey;
	
	String auth_chan_num
	
	String auth_chan_code
	
	String auth_chan_url;
	
	String pp_rule_type;
	
	String pp_rule_value;
	
	String pp_rule_cht;
	
	String handle_fee;
	
	Integer pt_rule;
	
	Integer httime;
	
	String config_name;
	
	BigInteger pid;
}
