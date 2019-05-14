package com.jeancoder.ticketingsys.internal.plan

import java.text.SimpleDateFormat
import java.util.List

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.dto.sys.SysProjectInfo
import com.jeancoder.ticketingsys.ready.film.dto.DataMovieListInfoDto
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieFullInfo
import com.jeancoder.ticketingsys.ready.film.service.FilmService
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanDate
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanMovie
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanPlan
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.dto.HallSchemaWithItem
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanMovie
import com.piaodaren.ssc.model.CinemaPlanResult

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();

Result result = new Result();

try {
	List<DataMovieListInfoDto> onlineFilms = FilmService.INSTANCE.getAllOnlineMovie();
	result.setData(Res.Success(onlineFilms));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}