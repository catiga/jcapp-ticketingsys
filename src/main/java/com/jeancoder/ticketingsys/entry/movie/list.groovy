package com.jeancoder.ticketingsys.entry.movie

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.ticketingsys.ready.Service.MovieInfoService
import com.jeancoder.ticketingsys.ready.entity.MovieInfo
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.util.StringUtil

Result result = new Result();
JCLogger logger = JCLoggerFactory.getLogger(this.class);
try {
	def pn  = JC.request.param("pn");
	def ps  = JC.request.param("ps");
	if (StringUtil.isEmpty(pn)) {
		pn = "1"
	}
	if (StringUtil.isEmpty(ps)) {
		ps = "10"
	}
	pn = Integer.parseInt(pn);
	ps = Integer.parseInt(ps);
	JcPage<MovieInfo> movieInfoPage = new JcPage<MovieInfo>();
	movieInfoPage.pn = pn;
	movieInfoPage.ps = ps;
	movieInfoPage = MovieInfoService.INSTANCE.getPage(movieInfoPage, GlobalHolder.proj.id);
	//List<DataMovieListInfoDto> onlineFilms = FilmService.INSTANCE.getAllOnlineMovie();
	//result.addObject("supportSystems", supportSystems);
	result.addObject("page", movieInfoPage);
} catch (any) {
	logger.info("", any)
}
result.setView("movie/list");
return result;