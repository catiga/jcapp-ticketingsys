package com.jeancoder.ticketingsys.entry.movie

import java.sql.Timestamp
import java.util.Date

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.Service.MovieCelebrityServer
import com.jeancoder.ticketingsys.ready.Service.MovieInfoService
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.MovieAttach
import com.jeancoder.ticketingsys.ready.entity.MovieCelebrity
import com.jeancoder.ticketingsys.ready.entity.MovieInfo
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.util.DateUtil
import com.jeancoder.ticketingsys.ready.util.StringUtil

Result result = new Result();
JCLogger logger = LoggerSource.getLogger(this.class);
try {
	def mid  = JC.request.param("mid");
	
	MovieInfo movieInfo = MovieInfoService.INSTANCE.getItem(mid);
	
	if (movieInfo == null) {
		return SimpleAjax.notAvailable("影片不存在")
	}
	SimpleAjax reules = JC.internal.call(SimpleAjax,"piaodaren", "movie/movie_detail",[pid:GlobalHolder.proj.id,film_no:movieInfo.film_no])
	if (!reules.available) {
		return reules;
	}
	def data = reules.data;
	if (data == null || data.size() == 0) {
		return SimpleAjax.notAvailable("影片不存在");
	}
	data = data.get(0);
	movieInfo.film_score = data.film_score;
	movieInfo.film_name = data.film_name;
	movieInfo.film_subtitle = data.film_subtitle;
	movieInfo.film_brief = data.film_brief;
	movieInfo.film_content = data.film_content;
	movieInfo.film_language = data.film_language;
	
	movieInfo.film_format = data.film_format;
	movieInfo.film_dimensional = data.film_dimensional;
	movieInfo.film_size = data.film_size;
	if (!StringUtil.isEmpty(data.release_date_str)) {
		movieInfo.release_date = DateUtil.getDate(data.release_date_str, "yyyy-MM-dd")
	}
	movieInfo.film_region = data.film_region;
	movieInfo.pic_entry = data.pic_entry;
	movieInfo.pic_small = data.pic_small;
	movieInfo.time_diff = data.time_diff;
	movieInfo.film_type = data.film_type;
	movieInfo.film_alias_name = data.film_alias_name;
	movieInfo.film_source ="0010";
	MovieInfoService.INSTANCE.updaetOrSave(movieInfo);
	for (def celebritys : data.celebritys) {
		MovieCelebrity mc = new MovieCelebrity();
		List<MovieCelebrity> mcList = MovieCelebrityServer.INSTANCE.getByMid(movieInfo.id);
		for (MovieCelebrity item : mcList) {
			item.flag = -1;
			MovieCelebrityServer.INSTANCE.updaetOrSave(item);
		}
		mc.movie_id = movieInfo.id;
		mc.role_type = celebritys.role_type;
		mc.role_name = celebritys.role_name;
		if (celebritys.cele != null) {
			mc.title_img = celebritys.cele.head_img;
			mc.cele_name = celebritys.cele.name_cn;
			mc.cele_name_en = celebritys.cele.name_en;
		}
		mc.a_time = new Date();
		mc.flag = 0;
		MovieCelebrityServer.INSTANCE.updaetOrSave(mc);
	}
	return SimpleAjax.available();
} catch (any) {
	logger.info("", any)
	return SimpleAjax.notAvailable("同步失败");
}
 