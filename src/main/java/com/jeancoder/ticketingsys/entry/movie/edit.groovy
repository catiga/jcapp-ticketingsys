package com.jeancoder.ticketingsys.entry.movie

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.MovieInfo

def mid = JC.request.param('mid');

MovieInfo movie = JcTemplate.INSTANCE().get(MovieInfo,"select * from MovieInfo Where id=? and flag!=? order by a_time desc  ", mid,-1)

Result view = new Result();
view.setView('movie/edit');

view.addObject('movie_info', movie);

return view;
