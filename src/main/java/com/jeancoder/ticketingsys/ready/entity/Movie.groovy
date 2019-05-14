package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp
import java.util.Date
import java.util.List

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieCelebrityDto
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMoviePictureDto

@JCBean(tbname = 'data_tc_ss_movie_info')
class Movie {

	@JCID
	BigInteger id;
	
	String film_no;
	
	String film_name;
	
	String film_subtitle;
	
	String film_brief;
	
	String film_content;
	
	String film_language;
	
	String film_format;
	
	String film_dimensional;
	
	String film_size;
	
	String film_region;
	
	String release_date;
	
	String pic_entry;
	
	String pic_small;
	
	String prevue;
	
	String film_score;
	
	Integer time_diff;
	
	String film_type;
	
	String film_alias_name;
	
	String film_source;
	
	Integer flag = 0;
	
	Timestamp a_time;
	
	@JCNotColumn
	List<DataTcSsMoviePictureDto> pictures;
	
	@JCNotColumn
	List<DataTcSsMovieCelebrityDto> celebritys;
	
}
