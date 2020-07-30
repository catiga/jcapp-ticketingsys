package com.jeancoder.ticketingsys.entry.movie.aj

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

MovieInfo movie = JcTemplate.INSTANCE().get(MovieInfo,"select * from MovieInfo Where id=? and flag!=? order by a_time desc  ", id, -1);
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

JcTemplate.INSTANCE().update(movie);

return SimpleAjax.available();

