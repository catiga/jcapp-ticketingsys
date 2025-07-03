package com.jeancoder.ticketingsys.internal.api

import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieFullInfo
import com.jeancoder.ticketingsys.ready.film.service.FilmService
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanDate
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanMovie
import com.jeancoder.ticketingsys.ready.plan.dto.MovieDatePlanPlan
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.dto.HallSchemaWithItem
import com.jeancoder.ticketingsys.ready.schema.dto.PlanSchema
import com.jeancoder.ticketingsys.ready.schema.dto.TicketPriceDto
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.support.Res
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanMovie
import com.piaodaren.ssc.model.CinemaPlanResult
def cinema_id = JC.internal.param('cinema_id')?.toString()?.trim();
JCLogger logger = JCLoggerFactory.getLogger(this.getClass().getName());
if(!cinema_id) {
	return Res.Failed(Codes.COMMON_PARAM_ERROR);
}

Long id = Long.valueOf(cinema_id);
logger.info('get_plan_cinema_id=' + cinema_id);
Cinema cinema = JcTemplate.INSTANCE().get(Cinema, 'select * from Cinema where id=?', id);
if(cinema==null) {
	return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
}
def pid = cinema.proj_id;
CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
if(cinemaAuthInfo == null) {
	return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
}

Calendar now = Calendar.getInstance(TimeZone.getDefault());
now.setTime(new Date());
now.set(Calendar.HOUR,0);
now.set(Calendar.MINUTE,0);
now.set(Calendar.SECOND,0);
now.set(Calendar.MILLISECOND,0);

Date start_time = now.getTime()
now.add(Calendar.DATE, getData(id));
Date end_time = now.getTime()

try {
	def start_ttt = Calendar.getInstance().getTimeInMillis();
	CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
	//CinemaPlanResult planResult = SssHelper.INSTANCE.just_get_cinema_plans_from_cache(cinemaAuthInfo, start_time, end_time);
	def end_ttt = Calendar.getInstance().getTimeInMillis();
	if((end_ttt - start_ttt)/1000>1.5) {
		logger.info('cache_get_time: cinema_id=' + id + '::: exhausted_time=' + (end_ttt - start_ttt)/1000 + "");
	}

	if(!"0".equals(planResult.getCode())) {
		return Res.Failed(Codes.CINEMA_CONN_FAILED);
	}

	List<HallSchemaWithItem> cinemaSchemas = SchemaService.INSTANCE.getCinemaSchemas_online(id,pid);

	SimpleAjax market_info = null;
	try {
		// 只取在线选座
		// market_info = JC.internal.call(SimpleAjax.class, 'market', 'market/get_all_market_rule', ["pid":pid, "mc_type":"2000"])
		market_info = JC.internal.call(SimpleAjax.class, 'market', 'market/get_tcss_market_rule', ["pid":pid, "mc_type":"2000"])
	} catch (Exception e) {
		logger.error("无获取可用的票务营销活动失败，或获取活动失败");
	}
	
	List<MovieDatePlanMovie> movies = new ArrayList<MovieDatePlanMovie>();
	for(CinemaPlan plan : planResult.getResult()) {
		if(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(plan.getStartTime()).getTime() < new Date().getTime()) {
			continue;
		}
		///暂不支持多影片排期 默认取第一部
		CinemaPlanMovie pmovie = plan.getMovieInfo().get(0);
		
		DataTcSsMovieFullInfo movieInfo = FilmService.INSTANCE.getMovieInfoByNoWithoutPid(pmovie.getCineMovieNum());
		//Movie movieInfo = JcTemplate.INSTANCE().get(Movie, 'select * from Movie where flag!=? and film_no=? order by id asc', -1, pmovie.getCineMovieNum());
		
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
		matchMovie.setName(mname);
		if(movieInfo != null) {
			matchMovie.setScore(movieInfo.getFilm_score());
			matchMovie.setImg(movieInfo.getPic_small());
			matchMovie.setCelebritys(FilmService.INSTANCE.getCelesByMovieId(movieInfo.getId()));
		}

		MovieDatePlanDate matchDate = matchMovie.matchAddedDate(plan.getBusinessDate());
		if(matchDate == null) {
			matchDate = new MovieDatePlanDate();
			matchMovie.getDates().add(matchDate);
		}

		matchDate.setDate(plan.getBusinessDate());

		// H5端 显示价格 不取网售票类价格，不取鼎鑫前台市场价
		List<PlanSchema> planSchemalist = SchemaService.INSTANCE.matchPlanSchemas(plan,pmovie, cinemaSchemas);
		String price = MoneyUtil.get_cent_from_yuan(plan.getMarketPrice());
		if (planSchemalist != null && !planSchemalist.isEmpty()) {
			price = planSchemalist.get(0).getPrice();
		}
		TicketPriceDto item = new TicketPriceDto();
		item.running_time_by_plan(plan.getStartTime());
		
		def dimension = movieInfo!=null?movieInfo.film_dimensional:pmovie.movieDimensional;
		dimension = dimension.toUpperCase();	//转换大写
		if(dimension.indexOf('3D')>-1) {
			//优先匹配3D情况
			dimension = '3D';
		} else {
			dimension = '2D';
		}
		
		item.movie_dimensional = dimension;
		
		item.movie_size = movieInfo!=null?movieInfo.film_size:pmovie.movieSize;
		item.store_limit = id + '';
		
		//item.movie_limit = movieInfo.id + '';
		//传入影片编码
		item.movie_limit = movieInfo!=null?movieInfo.film_no:pmovie.cineMovieNum;
		
		item.price = new BigDecimal(price);
		item.hall_limit = plan.getHallId();
		item.min_price = new BigDecimal(plan.lowestPrice).multiply(100);
		String filter_price = SchemaService.INSTANCE.filterPriceRlues(item,pid);

		try {
			if(market_info != null && market_info.available && market_info.data != null){
				item.currt_running_time = plan.getStartTime();
				item.hall_limit = plan.getHallId();
				filter_price = SchemaService.INSTANCE.filter_price_with_rules(pid, item, market_info.data);
			}
		} catch (Exception e) {
			logger.error("获取可用的票务营销活动失败", e);
		}
		if(filter_price != null&&filter_price != ''){
			if (new BigDecimal(filter_price).compareTo(new BigDecimal(0))<0) {
				filter_price = '0';
			}
		}
		//将后台设置的禁售时间与当前影片的时间比较
		String time_code = "0";
		if (!StringUtil.isEmpty(cinema.time_quantity)) {
			int s_time = Integer.parseInt(cinema.time_quantity);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar nowTime = Calendar.getInstance();
			nowTime.add(Calendar.MINUTE, s_time);
			String current_time = formatter.format(nowTime.getTime());
			String plan_time = plan.getStartTime();
			int m = current_time.compareTo(plan_time);
			if(m>0){//说明该影片的出售时间已过 H5端不能出售
				time_code = "1";
			}
		}
		
		MovieDatePlanPlan cplan = new MovieDatePlanPlan();
		cplan.setCinemaId(""+id);
		cplan.setPlanId(plan.getId());
		cplan.setSell_code(time_code);
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
		//cplan.setCurrent_price(price)
		cplan.setCurrent_price(filter_price?filter_price:price);
		//logger.info("pmovie_cplan__" + JackSonBeanMapper.toJson(cplan))
		matchDate.getPlans().add(cplan)
	}
	return Res.Success(movies);
}catch(Exception e) {
	logger.info("get movies error", e);
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}


public Integer getData(def c_id) {
	try {
		// 满天星接口 只能查5的排期 但是只返回2天
		def cids = [20,21,22,23]
		for (def c : cids) {
			if (c.toString().equals(c_id.toString())) {
				return 2;
			}
		}
	} catch (any) {
		LoggerSource.getLogger(this.getClass().getName()).error("getData__",any)
	}
	return 60;	//默认返回60天
	
}