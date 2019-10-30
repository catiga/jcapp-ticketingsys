package com.jeancoder.ticketingsys.ready.film

import groovy.json.JsonException
import groovy.json.JsonSlurper

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMovieCelebrity
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMovieInfo
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMoviePicture
import com.jeancoder.ticketingsys.ready.film.service.FilmService
import com.piaodaren.ssc.model.CinemaPlanMovie

class SyncThread extends Thread {
	JCLogger logger = LoggerSource.getLogger();
	private CinemaPlanMovie movie;
	private Long pid;
	public SyncThread() {}
	public SyncThread(CinemaPlanMovie movie,def pid) {
		this.movie = movie;
		this.pid = new Long(pid.toString());
	}
	@Override
	public void run() {
		Long existsId = FilmService.INSTANCE.getExistsId(movie.getCineMovieNum(), pid);
		logger.info(""+existsId)
		if(existsId != null) {
			//该影片已经获取过影讯
			return;
		}

		String maoyanJsonDataReg = "<script>var AppData =([^>]*);</script>"

		String snapshotStr = MaoyanHelper.INSTANCE.getSnapshot(movie.getMovieName());
		def json = null;
		try {
			json = new JsonSlurper().parseText(snapshotStr);
		}catch(JsonException e) {
			logger.error('save_movie error exception:', e);
			//保存一个无名称的影片
			DataTcSsMovieInfo film = new DataTcSsMovieInfo();
			film.setFilm_no(movie.getCineMovieNum());
			film.setFilm_subtitle(movie.getMovieSubtitle());
			film.setFilm_language(movie.getMovieLanguage());
			film.setFilm_format(movie.getMovieFormat());
			film.setFilm_dimensional(movie.getMovieDimensional());
			film.setFilm_size(movie.getMovieSize());
			film.setA_time(new Date());
			film.setProj_id(pid)
			FilmService.INSTANCE.addOrUpdate(film);
			//影讯获取失败 保存该影片影讯状态为失败状态
			return;
		}

		if(json["movies"] == null) {
			//快照获取失败
			//保存一个无名称的影片
			DataTcSsMovieInfo film = new DataTcSsMovieInfo();
			film.setFilm_no(movie.getCineMovieNum());
			film.setFilm_subtitle(movie.getMovieSubtitle());
			film.setFilm_language(movie.getMovieLanguage());
			film.setFilm_format(movie.getMovieFormat());
			film.setFilm_dimensional(movie.getMovieDimensional());
			film.setFilm_size(movie.getMovieSize());
			film.setA_time(new Date());
			film.setProj_id(pid)
			FilmService.INSTANCE.addOrUpdate(film);
			//影讯获取失败 保存该影片影讯状态为失败状态
			return;
		}

		def movies = json["movies"]["list"];
		def hitMovie = movies[0];
		String maoyanId = hitMovie["id"];

		String defaultHtml = MaoyanHelper.INSTANCE.getMovieHtml(maoyanId);

		String test = MaoyanHelper.INSTANCE.regexpFetchContent(maoyanJsonDataReg,1,defaultHtml);

		if(test == null) {
			//保存一个无名称的影片
			DataTcSsMovieInfo film = new DataTcSsMovieInfo();
			film.setFilm_no(movie.getCineMovieNum());
			film.setFilm_subtitle(movie.getMovieSubtitle());
			film.setFilm_language(movie.getMovieLanguage());
			film.setFilm_format(movie.getMovieFormat());
			film.setFilm_dimensional(movie.getMovieDimensional());
			film.setFilm_size(movie.getMovieSize());
			film.setA_time(new Date());
			film.setProj_id(pid)
			FilmService.INSTANCE.addOrUpdate(film);
			//影讯获取失败 保存该影片影讯状态为失败状态
			return;
		}

		def maoyanData = null;

		try {
			maoyanData = new JsonSlurper().parseText(test);

			DataTcSsMovieInfo film = new DataTcSsMovieInfo();
			film.setFilm_no(movie.getCineMovieNum());
			film.setFilm_name(maoyanData["movie"]["nm"]);
			film.setFilm_subtitle(movie.getMovieSubtitle());
			film.setFilm_brief("");
			film.setFilm_content(maoyanData["movie"]["dra"]);
			film.setFilm_language(movie.getMovieLanguage());
			film.setFilm_format(movie.getMovieFormat());
			film.setFilm_dimensional(movie.getMovieDimensional());
			film.setFilm_size(movie.getMovieSize());
			film.setFilm_region(maoyanData["movie"]["fra"] == null || "".equals(maoyanData["movie"]["fra"]) ? maoyanData["movie"]["src"] : maoyanData["movie"]["fra"]);
			film.setRelease_date(maoyanData["movie"]["frt"] == null || "".equals(maoyanData["movie"]["frt"]) ? maoyanData["movie"]["rt"] : maoyanData["movie"]["frt"]);
			film.setPic_entry(MaoyanHelper.INSTANCE.processImgUrl(maoyanData["movie"]["img"], "180", "250"));
			film.setPic_small(MaoyanHelper.INSTANCE.processImgUrl(maoyanData["movie"]["img"], "180", "250"));
			film.setPrevue(maoyanData["movie"]["vd"]);
			film.setFilm_score(maoyanData["movie"]["sc"]);
			film.setTime_diff(maoyanData["movie"]["dur"]);
			film.setFilm_type(maoyanData["movie"]["cat"]);
			film.setFilm_alias_name(maoyanData["movie"]["nm"]);
			film.setFilm_source("");
			film.setA_time(new Date());
			film.setProj_id(pid)
			FilmService.INSTANCE.addOrUpdate(film);


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
		}catch(Exception e) {
			//保存一个无名称的影片
			DataTcSsMovieInfo film = new DataTcSsMovieInfo();
			film.setFilm_no(movie.getCineMovieNum());
			film.setFilm_subtitle(movie.getMovieSubtitle());
			film.setFilm_language(movie.getMovieLanguage());
			film.setFilm_format(movie.getMovieFormat());
			film.setFilm_dimensional(movie.getMovieDimensional());
			film.setFilm_size(movie.getMovieSize());
			film.setA_time(new Date());
			film.setProj_id(pid)
			FilmService.INSTANCE.addOrUpdate(film);
			//影讯获取失败 保存该影片影讯状态为失败状态
			return;
		}
	}
}
