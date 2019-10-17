package com.jeancoder.ticketingsys.entry.movie
import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.entity.MovieInfo
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.film.MaoyanHelper
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMovieCelebrity
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMovieInfo
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMoviePicture
import com.jeancoder.ticketingsys.ready.film.service.FilmService
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

import groovy.json.JsonSlurper

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();
JCLogger logger = LoggerSource.getLogger(this.class);

String maoyanJsonDataReg = "<script>var AppData =([^>]*);</script>"

String movieInfoIdStr = JC.request.param("org_id");
String myid = JC.request.param("film_my_id");

//获取更多信息
String film_cat = JC.request.param('film_cat');
String film_diff = JC.request.param('film_diff');
String film_dimen = JC.request.param('film_dimen');
String film_name = JC.request.param('film_name');
String film_pub_date = JC.request.param('film_pub_date');
String film_score = JC.request.param('film_score');
String film_img = JC.request.param('film_img');

//首先尝试更新基本信息
def base_update = false;
try {
	MovieInfo movie = JcTemplate.INSTANCE().get(MovieInfo, 'select * from MovieInfo where id=?', movieInfoIdStr);
	if(movie!=null) {
		if(film_cat) {
			movie.film_type = film_cat;
		}
		if(film_diff) {
			try {
				movie.time_diff = Integer.valueOf(film_diff);
			} catch(e) {
			}
		}
		if(film_dimen) {
			movie.film_dimensional = film_dimen;
		}
		if(film_name) {
			movie.film_name = film_name;
		}
		if(film_pub_date) {
			try {
				film_pub_date = film_pub_date.substring(0, 10);
				movie.release_date = new SimpleDateFormat('yyyy-MM-dd').parse(film_pub_date);
			} catch(e) {}
		}
		if(film_score) {
			movie.film_score = film_score;
		}
		if(film_img) {
			movie.pic_entry = film_img;
			movie.pic_small = film_img;
		}
		JcTemplate.INSTANCE().update(movie);
		base_update = true;
	}
}catch(any) {}
try {
	String defaultHtml = MaoyanHelper.INSTANCE.getMovieHtml(myid);
	
	String test = MaoyanHelper.INSTANCE.regexpFetchContent(maoyanJsonDataReg,1,defaultHtml);
	
	maoyanData = new JsonSlurper().parseText(test);
	
	def maoyanData = new JsonSlurper().parseText(test);
	
	Long existsId = null;
	if(movieInfoIdStr != null && !"".equals(movieInfoIdStr)) {
		existsId = Long.valueOf(movieInfoIdStr);
	}
	
	DataTcSsMovieInfo film = new DataTcSsMovieInfo();
	film.setId(existsId);
	film.setFilm_name(maoyanData["movie"]["nm"]);
	film.setFilm_brief("");
	film.setFilm_content(maoyanData["movie"]["dra"]);
	film.setFilm_region(maoyanData["movie"]["fra"] == null || "".equals(maoyanData["movie"]["fra"]) ? maoyanData["movie"]["src"] : maoyanData["movie"]["fra"]);
	try {
		def release_date = maoyanData["movie"]["frt"] == null || "".equals(maoyanData["movie"]["frt"]) ? maoyanData["movie"]["rt"] : maoyanData["movie"]["frt"];
		def tmp_release_date = new SimpleDateFormat('yyyy-MM-dd').parse(release_date);
		film.setRelease_date(release_date);
	} catch(any) {
		try {
			def release_date = maoyanData["movie"]["rt"];
			def tmp_release_date = new SimpleDateFormat('yyyy-MM-dd').parse(release_date);
			film.setRelease_date(release_date);
		}catch(any_e){
			
		}
	}
	film.setPic_entry(MaoyanHelper.INSTANCE.processImgUrl(maoyanData["movie"]["img"], "180", "250"));
	film.setPic_small(MaoyanHelper.INSTANCE.processImgUrl(maoyanData["movie"]["img"], "180", "250"));
	film.setPrevue(maoyanData["movie"]["vd"]);
	film.setFilm_score(maoyanData["movie"]["sc"]);
	film.setTime_diff(maoyanData["movie"]["dur"]);
	film.setFilm_type(maoyanData["movie"]["cat"]);
	film.setFilm_alias_name(maoyanData["movie"]["nm"]);
	film.setFilm_source("");
	if(existsId == null) {
		film.setA_time(new Date());
	}
	
	FilmService.INSTANCE.addOrUpdate(film);
	
	//删除全部演员
	DatabasePower dbpower = DatabaseSource.getDatabasePower();
	String delCeleSql = "DELETE FROM data_tc_ss_movie_celebrity WHERE movie_id = ?";
	dbpower.doUpdate(delCeleSql,film.getId());
	
	//保存演员
	try {
		def celes = maoyanData["celebrities"];
		for(def cele : celes) {
			DataTcSsMovieCelebrity celeEntity = new DataTcSsMovieCelebrity();
			if("2".equals(cele["cr"]) || "2" == cele["cr"]) {
				celeEntity.setRole_name("导演")
			}else {
				celeEntity.setRole_name(cele["roles"])
			}
			celeEntity.setCele_name_en(cele["enm"])
			celeEntity.setCele_name(cele["cnm"])
			celeEntity.setTitle_img(MaoyanHelper.INSTANCE.processImgUrl(cele["avatar"], "180", "250"));
			celeEntity.setMovie_id(film.getId())
			celeEntity.setA_time(new Date());
			
			FilmService.INSTANCE.addOrUpdateCele(celeEntity);
		}
	}catch(Exception e) {
		//演员 保存失败就失败吧
	}
	
	//删除全部图片
	String delPicSql = "DELETE FROM data_tc_ss_movie_picture WHERE m_id = ?";
	dbpower.doUpdate(delPicSql, film.getId());
	
	//保存图片
	try {
		def photos = maoyanData["movie"]["photos"];
		for(def photo : photos) {
			DataTcSsMoviePicture picture = new DataTcSsMoviePicture();
			
			picture.setM_id(film.getId());
			picture.setPic_url(MaoyanHelper.INSTANCE.processImgUrl(String.valueOf(photo), "180", "140"));
			picture.setPic_type("img");
			
			FilmService.INSTANCE.addOrUpdatePicture(picture);
		}
	}catch(Exception e) {
		//图片 保存失败就失败吧
	}

	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	if(base_update) {
		result.setData(Res.Success());
		return result;
	}
	logger.error("同步影片信息失败",e)
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}