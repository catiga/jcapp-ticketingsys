package com.jeancoder.ticketingsys.internal.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.ticketingsys.ready.film.MaoyanHelper
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieCelebrityDto
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieFullInfo
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMoviePictureDto
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

import groovy.json.JsonSlurper

def myid = JC.internal.param("movie_id")?.toString()?.trim();
try {
	String defaultHtml = MaoyanHelper.INSTANCE.getMovieHtml(myid);

	String maoyanJsonDataReg = "<script>var AppData =([^>]*);</script>"
	String json_data = MaoyanHelper.INSTANCE.regexpFetchContent(maoyanJsonDataReg,1,defaultHtml);

	json_data = json_data.replaceAll("w\\.h", "180.250")

	def data = new JsonSlurper().parseText(json_data);

	DataTcSsMovieFullInfo movieInfo = new DataTcSsMovieFullInfo();
	movieInfo.id = data.$id;
	movieInfo.film_name = data.movie.nm;
	movieInfo.pic_entry = data.movie.img;
	movieInfo.film_no = data.$id;
	movieInfo.film_type = data.movie.cat;
	movieInfo.release_date = data.movie.rt;
	movieInfo.film_region = data.movie.src;
	movieInfo.time_diff = data.movie.dur;
	movieInfo.film_score = '';
	movieInfo.film_content = data.movie.dra;
	movieInfo.film_dimensional = data.movie.ver;
	movieInfo.film_language = data.movie.oriLang;
	movieInfo.film_brief = data.movie.star;

	List<DataTcSsMovieCelebrityDto> celebritys = [];
	for(x in data.celebrities) {
		DataTcSsMovieCelebrityDto cel = new DataTcSsMovieCelebrityDto();
		cel.cele_name = x.cnm;	//姓名
		cel.cele_name_en = x.enm;	//英文名
		cel.role_name = x.roles;	//职能
		cel.role_type = '1000';		//类型
		cel.title_img = x.avatar;

		celebritys += cel;
	}
	movieInfo.celebritys = celebritys;

	List<DataTcSsMoviePictureDto> pictures = [];
	for(x in data.movie.photos) {
		DataTcSsMoviePictureDto ph = new DataTcSsMoviePictureDto();
		ph.pic_type = 'img';
		ph.pic_url = x;
		pictures.add(ph);
	}
	movieInfo.pictures = pictures;

	return Res.Success(movieInfo);
}catch(Exception e) {
	e.printStackTrace()
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}