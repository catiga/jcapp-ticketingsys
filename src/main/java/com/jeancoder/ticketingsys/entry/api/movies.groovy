package com.jeancoder.ticketingsys.entry.api

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieFullInfo
import com.jeancoder.ticketingsys.ready.film.service.FilmService
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanDate
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanMovie
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanPlan
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.dto.HallSchemaWithItem
import com.jeancoder.ticketingsys.ready.schema.dto.PlanSchema
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.support.Res
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanMovie
import com.piaodaren.ssc.model.CinemaPlanResult

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();

Result result = new Result();
JCLogger logger = LoggerSource.getLogger(this.class);
try {
	Long id = req.getLong("cinema_id");
	
	if(id == null) {
		result.setData(Res.Failed(Codes.COMMON_PARAM_ERROR));
		return result;
	}
	
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
	
	if(cinemaAuthInfo == null) {
		result.setData(Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR));
		return result;
	}
	
	Calendar now = Calendar.getInstance(TimeZone.getDefault());
	now.setTime(new Date());
	
	now.set(Calendar.HOUR,0);
	now.set(Calendar.MINUTE,0);
	now.set(Calendar.SECOND,0);
	now.set(Calendar.MILLISECOND,0);
	
	Date start_time = now.getTime()
	
	now.add(Calendar.DATE, 45);
	Date end_time = now.getTime()
	CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
	
	if(!"0".equals(planResult.getCode())) {
		result.setData(Res.Failed(Codes.CINEMA_CONN_FAILED));
		return result;
	}
	println "该影城支持的默认分类_id = " + id;
	//该影城支持的默认分类
	List<HallSchemaWithItem> cinemaSchemas = SchemaService.INSTANCE.getDefaultCinemaSchemas(id,GlobalHolder.proj.id);
	//println "该影城支持的默认分类_"+cinemaSchemas.size() + "___" + JackSonBeanMapper.toJson(cinemaSchemas);
	
	//组织数据 组织成 影片-》日期-》排期
	List<MovieDatePlanMovie> movies = new ArrayList<MovieDatePlanMovie>();
	for(CinemaPlan plan : planResult.getResult()) {
		if(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getStartTime()).getTime() < new Date().getTime()) {
			continue;
		}
		
		//暂不支持多影片排期 默认取第一部
		CinemaPlanMovie pmovie = plan.getMovieInfo().get(0);
		DataTcSsMovieFullInfo movieInfo = FilmService.INSTANCE.getMovieInfoByNo(pmovie.getCineMovieNum(), GlobalHolder.proj.id);
		String mname = movieInfo != null && movieInfo.getFilm_name() != null && !"".equals(movieInfo.getFilm_name()) ? movieInfo.getFilm_name() : pmovie.getMovieName()
		
		MovieDatePlanMovie matchMovie = null;
		for(MovieDatePlanMovie addedMovie : movies) {
			if(addedMovie.getName().equals(mname)) {
				matchMovie = addedMovie;
			}
		}
		if(matchMovie == null) {
			matchMovie = new MovieDatePlanMovie();
			matchMovie.setProperties(movieInfo)
			movies.add(matchMovie);
		}
		matchMovie.setCelebritys(FilmService.INSTANCE.getCelesByMovieId(movieInfo.getId()))
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
		
		// 好时光H5端 显示价格 不取网售票类价格，不取鼎鑫前台市场价
		List<PlanSchema> planSchemalist = SchemaService.INSTANCE.matchPlanSchemas(plan,pmovie, cinemaSchemas);
		//println  "planSchemalist_size" + planSchemalist.size();
		String price = MoneyUtil.get_cent_from_yuan(plan.getMarketPrice());
		if (!planSchemalist != null && !planSchemalist.isEmpty()) {
			price = planSchemalist.get(0).getPrice();
		}
	//	println "鼎鑫————"+ plan.getStartTime() + "__" + plan.getMovieInfo().get(0).getMovieSize()+"__"+ plan.getMovieInfo().get(0).getMovieDimensional() + "__" +  plan.getHallId() + "__" +  plan.getHallName();
	//	println "鼎鑫————"+ plan.getMarketPrice();
	//	println "价格————"+price
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
		cplan.setSchemas(planSchemalist)
		cplan.setLastUpdateTime(plan.getCineUpdateTime())
		cplan.setStandard_price(price)
		cplan.setCurrent_price(price)
		matchDate.getPlans().add(cplan)
	}
//	println "planResult__" + JackSonBeanMapper.toJson(movies);
	logger.info("INTERNAL_SERVER_ERROR__" + movies.size())
	result.setData(Res.Success(movies));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}