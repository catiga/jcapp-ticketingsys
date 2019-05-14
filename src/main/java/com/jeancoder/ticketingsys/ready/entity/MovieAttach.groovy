package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_movie_attach')
class MovieAttach {
	@JCID
	BigInteger id;
	
	String cinema_movie_name;
	
	Date a_time;
	
	Timestamp c_time;
	
	Integer flag;
	
}
