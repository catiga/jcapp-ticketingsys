package com.jeancoder.ticketingsys.internal.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieFullInfo
import com.jeancoder.ticketingsys.ready.film.service.FilmService
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res


try {
	Long id = Long.valueOf(JC.internal.param("movie_id").toString());
	
	println "movie_detail__" + id;
	DataTcSsMovieFullInfo movieInfo = FilmService.INSTANCE.getMovieInfoById(id);
	movieInfo.setPictures(FilmService.INSTANCE.getPicsByMovieId(id));
	movieInfo.setCelebritys(FilmService.INSTANCE.getCelesByMovieId(id))
	
	return Res.Success(movieInfo);
}catch(Exception e) {
	e.printStackTrace()
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}