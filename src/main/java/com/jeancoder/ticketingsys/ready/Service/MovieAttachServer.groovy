package com.jeancoder.ticketingsys.ready.Service

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.MovieAttach
import com.jeancoder.ticketingsys.ready.entity.MovieInfo

class MovieAttachServer {
	
	static final JCLogger logger = LoggerSource.getLogger(MovieAttachServer.class);
	
	private static final MovieAttachServer INSTANCE = new MovieAttachServer();
	
	JcTemplate jc_template = JcTemplate.INSTANCE();
	 
	public MovieAttach updaetOrSave(MovieAttach entity){
		if (entity.id == null) {
			entity.id = jc_template.save(entity);
		} else {
			jc_template.update(entity);
		}
		return entity;
	}
}
