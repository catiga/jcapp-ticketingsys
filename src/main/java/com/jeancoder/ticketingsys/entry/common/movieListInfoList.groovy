package com.jeancoder.ticketingsys.entry.common

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.sys.QueryAutoCompletDto
import com.jeancoder.ticketingsys.ready.film.dto.DataMovieListInfoDto
import com.jeancoder.ticketingsys.ready.film.service.FilmService

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	List<DataMovieListInfoDto> res = FilmService.INSTANCE.getAllOnlineMovie();
	if (res==null || res.empty) {
		//return result.setData(AvailabilityStatus.notAvailable('查询不到对应的影片'));
		return SimpleAjax.notAvailable('上映影片加载失败');
	}
	List<QueryAutoCompletDto> params = new ArrayList<QueryAutoCompletDto>();
	for (DataMovieListInfoDto m:res) {
		if(m == null) {
			continue;
		}
		QueryAutoCompletDto qac = new QueryAutoCompletDto();
		//qac.setLabel(m.id.toString());
		//使用影片编码
		qac.setLabel(m.film_no);
		StringBuffer buffer = new StringBuffer();
		buffer.append(m.film_name);
		buffer.append("--" + m.film_no);
		if(m.film_dimensional!=null&&!m.film_dimensional.equals("")) {
			buffer.append("--" + m.film_dimensional);
			if(m.film_size!=null&&!m.film_size.equals("")) {
				buffer.append(m.film_size);
			}
		} else {
			if(m.film_size!=null&&!m.film_size.equals("")) {
				buffer.append("--" + m.film_size());
			}
		}
		qac.setValue(buffer.toString());
		params.add(qac);
	}
	final def dtoList = params;
	//result.setData(AvailabilityStatus.available(dtoList));
	return SimpleAjax.available('', dtoList);
} catch (Exception e) {
	Logger.error("查询影片列表失败", e);
	//result.setData(AvailabilityStatus.notAvailable("查询影片列表失败"));
	return SimpleAjax.notAvailable('影片列表获取异常')
}
