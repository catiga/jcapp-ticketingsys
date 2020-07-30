package com.jeancoder.ticketingsys.entry.movie.aj

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.MovieInfo

def id = JC.request.param('id');
def film_name = JC.request.param('film_name');
def film_alias_name = JC.request.param('film_alias_name');
def film_subtitle = JC.request.param('film_subtitle');
def film_language = JC.request.param('film_language');
def film_dimen = JC.request.param('film_dimen');
def film_type = JC.request.param('film_type')
def film_size = JC.request.param('film_size');
def film_region = JC.request.param('film_region');
def film_date = JC.request.param('film_date');
def time_diff = JC.request.param('time_diff');
def pic_entry = JC.request.param('pic_entry');
def content = JC.request.param('content');

try {
	time_diff = Integer.valueOf(time_diff);
} catch(any) {
	time_diff = 90;
}

SimpleDateFormat _sdf_ = new SimpleDateFormat('yyyy-MM-dd');

try {
	film_date = _sdf_.format(_sdf_.parse(film_date));
} catch(any) {
	film_date = null;
}

MovieInfo movie = JcTemplate.INSTANCE().get(MovieInfo,"select * from MovieInfo where id=? and flag!=? order by a_time desc  ", id, -1);
if(movie==null) {
	return SimpleAjax.notAvailable('obj_not_found,影片未找到');
}

movie.film_alias_name = film_alias_name;
movie.film_content = content;
movie.film_dimensional = film_dimen;
movie.film_language = film_language;
movie.film_region = film_region;
movie.film_size = film_size;
movie.film_subtitle = film_subtitle;
movie.film_type = film_type;
movie.pic_entry = pic_entry;
movie.time_diff = time_diff;
movie.release_date = film_date;
movie.pic_small = pic_entry;

JcTemplate.INSTANCE().update(movie);

//尝试更新同影片编号的所有影片信息
def sql = 'select * from MovieInfo where film_no=? and flag!=?';
List<MovieInfo> result = JcTemplate.INSTANCE().find(MovieInfo, sql, movie.film_no, -1);
if(result && !result.empty) {
	for(x in result) {
		if(!x.film_alias_name) {
			x.film_alias_name = movie.film_alias_name;
		}
		if(!x.film_brief) {
			x.film_brief = movie.film_brief;
		}
		if(!x.film_content) {
			x.film_content = movie.film_content;
		}
		if(!x.film_dimensional) {
			x.film_dimensional = movie.film_dimensional;
		}
		if(!x.film_format) {
			x.film_format = movie.film_format;
		}
		if(!x.film_language) {
			x.film_language = movie.film_language;
		}
		if(!x.film_region) {
			x.film_region = movie.film_region;
		}
		if(!x.film_score) {
			x.film_score = movie.film_score;
		}
		if(!x.film_size) {
			x.film_size = movie.film_size;
		}
		if(!x.film_subtitle) {
			x.film_subtitle = movie.film_subtitle;
		}
		if(!x.film_type) {
			x.film_type = movie.film_type;
		}
		if(!x.pic_entry) {
			x.pic_entry = movie.pic_entry;
		}
		if(!x.pic_small) {
			x.pic_small = movie.pic_small;
		}
		if(!x.release_date) {
			x.release_date = movie.release_date;
		}
		if(!x.time_diff) {
			x.time_diff = movie.time_diff;
		}
		JcTemplate.INSTANCE().update(x);
	}
}

return SimpleAjax.available();

