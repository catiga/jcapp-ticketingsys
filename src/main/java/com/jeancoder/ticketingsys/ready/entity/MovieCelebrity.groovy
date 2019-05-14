package com.jeancoder.ticketingsys.ready.entity

import java.util.Date

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID


@JCBean(tbname = 'data_tc_ss_movie_celebrity')
class MovieCelebrity {
	@JCID
	BigInteger id;
	BigInteger movie_id;
	BigInteger cele_id;
	String role_type;
	String role_name;
	String title_img;
	String cele_name;
	String cele_name_en;
	Date a_time;
	Integer flag = 0;
}
