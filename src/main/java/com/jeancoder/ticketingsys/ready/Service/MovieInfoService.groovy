package com.jeancoder.ticketingsys.ready.Service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.MovieInfo

class MovieInfoService {
	
	static final JCLogger logger = LoggerSource.getLogger(MovieInfoService.class);
	
	private static final MovieInfoService INSTANCE = new MovieInfoService();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	public JcPage<MovieInfo> getPage(JcPage<MovieInfo> page, def pid){
		return jc_template.find(MovieInfo, page,"select * from MovieInfo Where proj_id=? and flag!=? order by a_time desc  ", pid,-1)
	}
	
	
	public MovieInfo getItem(def id){
		return jc_template.get(MovieInfo,"select * from MovieInfo Where id=? and flag!=? order by a_time desc  ", id,-1)
	}
	
	
	public MovieInfo updaetOrSave(MovieInfo entity){
		entity.c_time = new Timestamp(new Date().getTime());
		if (entity.id == null) {
			entity.id = jc_template.save(entity);
		} else {
			jc_template.update(entity);
		}
		return entity;
	}
}
