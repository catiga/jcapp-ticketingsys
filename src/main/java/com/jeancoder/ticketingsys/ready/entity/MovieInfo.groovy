package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_movie_info')
class MovieInfo {
	@JCID
	BigInteger id;
	
	BigInteger proj_id;
	
	String film_no; // 影片编号
	
	String film_score;// 演出评分
	
	/**这个是原始名字**/
	String film_name; //  演出名称
	
	String film_subtitle;
	
	String film_brief;// 简介
	
	String film_content;
	
	String film_language;
	
	String film_format;
	
	String film_dimensional;
	
	String film_size;
	
	String film_region;
	
	Date release_date;//发布日期
	
	String pic_entry;
	
	String pic_small;
	
	String prevue;
	
	Timestamp c_time;
	
	Integer flag = 0;
	
	Date a_time = new Date();
	
	/*********** @new field *************/
	Integer time_diff;// 演出时长
	
	String film_type;// 演出类型
	
	String film_alias_name;// 演出别名
	
	String film_source = "0000";
}
