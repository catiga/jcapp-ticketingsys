package com.jeancoder.ticketingsys.internal.plan

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
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
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanMovie
import com.piaodaren.ssc.model.CinemaPlanResult

JCResponse res = ResponseSource.getResponse();

JCLogger logger = LoggerSource.getLogger();

def s = System.currentTimeMillis();
try {
	Long id = Long.valueOf(JC.internal.param('cinema_id').toString());
	String day = JC.internal.param('day');
	
	if(id == null || day == null || "".equals(day)) {
		return Res.Failed(Codes.COMMON_PARAM_ERROR)
	}
	
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
	
	if(cinemaAuthInfo == null) {
		return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR)
	}
	def pid = cinemaAuthInfo.pid;
	SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
	Calendar now = Calendar.getInstance(TimeZone.getDefault());
	now.setTime(datesdf.parse(day));
	
	now.set(Calendar.HOUR,0);
	now.set(Calendar.MINUTE,0);
	now.set(Calendar.SECOND,0);
	now.set(Calendar.MILLISECOND,0);
	
	Date start_time = now.getTime()
	
	now.add(Calendar.DATE, 1);
	Date end_time = now.getTime()
	
	CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
	
	if(!"0".equals(planResult.getCode())) {
		return Res.Failed(Codes.CINEMA_CONN_FAILED);
	}
	//println "movies_____" + id;
	//该影城支持的所有票类
	List<HallSchemaWithItem> cinemaSchemas = SchemaService.INSTANCE.getCinemaSchemas(id);
	//println "cinemaSchemas__"+JackSonBeanMapper.toJson(cinemaSchemas);
	//组织数据 组织成 影片-》日期-》排期
	List<MovieDatePlanMovie> movies = new ArrayList<MovieDatePlanMovie>();
	
	for(CinemaPlan plan : planResult.getResult()) {
		if(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getStartTime()).getTime() < new Date().getTime()) {
			continue;
		}
		//暂不支持多影片排期 默认取第一部
		CinemaPlanMovie pmovie = plan.getMovieInfo().get(0);
		DataTcSsMovieFullInfo movieInfo = FilmService.INSTANCE.getMovieInfoByNo(pmovie.getCineMovieNum(),pid);
		String mname = movieInfo != null && movieInfo.getFilm_name() != null && !"".equals(movieInfo.getFilm_name()) ? movieInfo.getFilm_name() : pmovie.getMovieName()
		
		MovieDatePlanMovie matchMovie = null;
		for(MovieDatePlanMovie addedMovie : movies) {
			if(addedMovie.getName().equals(mname)) {
				matchMovie = addedMovie;
			}
		}
		if(matchMovie == null) {
			matchMovie = new MovieDatePlanMovie();
			movies.add(matchMovie);
		}
		
		matchMovie.setName(mname);
		if(movieInfo != null) {
			matchMovie.setScore(movieInfo.getFilm_score());
			matchMovie.setImg(movieInfo.getPic_small())
		}
		
		MovieDatePlanDate matchDate = matchMovie.matchAddedDate(plan.getBusinessDate());
		if(matchDate == null) {
			matchDate = new MovieDatePlanDate();
			matchMovie.getDates().add(matchDate);
		}
		
		
		matchDate.setDate(plan.getBusinessDate());
		
		MovieDatePlanPlan cplan = new MovieDatePlanPlan();
		cplan.setCinemaId(""+id);
		cplan.setPlanId(plan.getId());
		cplan.setPlanDate(plan.getBusinessDate())
		cplan.setSubtitle(pmovie.getMovieSubtitle())
		cplan.setLanguage(pmovie.getMovieLanguage())
		cplan.setHallId(plan.getHallId())
		cplan.setHall(plan.getHallName())
		cplan.setStartTime(plan.getStartTime())
		cplan.setEndTime(plan.getEndTime())
		cplan.setFormat(pmovie.getMovieFormat())
		cplan.setDimensional(pmovie.getMovieDimensional())
		cplan.setSize(pmovie.getMovieSize())
		cplan.setSchemas(SchemaService.INSTANCE.matchPlanSchemas(plan,pmovie, cinemaSchemas))
		cplan.setLastUpdateTime(plan.getCineUpdateTime())
		
		matchDate.getPlans().add(cplan)
	}
	
	return Res.Success(movies);
}catch(Exception e) {
	logger.error('movies error;', e);
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}