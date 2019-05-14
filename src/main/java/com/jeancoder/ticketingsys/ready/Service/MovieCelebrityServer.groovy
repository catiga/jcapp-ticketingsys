package com.jeancoder.ticketingsys.ready.Service

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.MovieCelebrity

class MovieCelebrityServer {
	static final JCLogger logger = LoggerSource.getLogger(MovieInfoService.class);
	
	private static final MovieCelebrityServer INSTANCE = new MovieCelebrityServer();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	
	
	public MovieCelebrity updaetOrSave(MovieCelebrity entity){
		if (entity.id == null) {
			entity.id = jc_template.save(entity);
		} else {
			jc_template.update(entity);
		}
		return entity;
	}
	
	
	public List<MovieCelebrity> getByMid(def mid){
		 return jc_template.find(MovieCelebrity, "select * from MovieCelebrity Where movie_id=? and flag!=?", mid,-1) ;
	}
}
