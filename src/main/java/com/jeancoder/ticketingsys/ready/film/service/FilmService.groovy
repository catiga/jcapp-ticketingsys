package com.jeancoder.ticketingsys.ready.film.service

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.Movie
import com.jeancoder.ticketingsys.ready.film.dto.DataMovieListInfoDto
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieCelebrityDto
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieFullInfo
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMoviePictureDto
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMovieAttach
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMovieCelebrity
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMovieInfo
import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMoviePicture

class FilmService {
	public static final FilmService INSTANCE = new FilmService();
	
	public void addOrUpdate(DataTcSsMovieInfo movie) {
		DatabasePower power = DatabaseSource.getDatabasePower();
		power.doUpdateSerialize(movie, "id", false);
	}
	
	public Long getExistsId(String filmNo,def pid) {
		DatabasePower power = DatabaseSource.getDatabasePower();
		String sql = '''
		SELECT
		id
		FROM
		data_tc_ss_movie_info
		WHERE 
		film_no = ? AND proj_id=?  AND flag != -1 
		''';
		power.doQueryUniqueScalar(Long.class,sql, filmNo, pid);
	}
	
	public DataTcSsMovieFullInfo getMovieInfoById(Long id) {
		DatabasePower power = DatabaseSource.getDatabasePower();
		String sql = '''
				SELECT
				*
				FROM
				data_tc_ss_movie_info
				WHERE 
				id = ?
				''';
		return power.doQueryUnique(DataTcSsMovieFullInfo.class, sql,id);
	}
	
	public DataTcSsMovieFullInfo getMovieInfoByNo(String filmNo, def pid) {
		DatabasePower power = DatabaseSource.getDatabasePower();
		String sql = '''
		SELECT
		*
		FROM
		data_tc_ss_movie_info
		WHERE 
		film_no = ? AND proj_id=?  AND flag != -1
		ORDER BY id asc
		''';
		//return power.doQueryUnique(DataTcSsMovieFullInfo.class, sql,filmNo);
		
		List<DataTcSsMovieFullInfo> result = power.doQueryList(DataTcSsMovieFullInfo.class, sql,filmNo, pid);
		 
		DataTcSsMovieFullInfo movie = null;
		if(result&&!result.isEmpty()) {
			def i = 0;
			for(x in result) {
				//这里处理多余的影片
				if(i == 0) {
					movie = x;
				} else {
					Movie  m =  JcTemplate.INSTANCE().get(Movie, " select * from Movie WHERE id=?", x.id);
					m.flag = -1;
					JcTemplate.INSTANCE().update(m);
				}
				i++;
			}
		}
		return movie;
	}
	
	public void addOrUpdateCele(DataTcSsMovieCelebrity cele) {
		DatabasePower power = DatabaseSource.getDatabasePower();
		power.doUpdateSerialize(cele, "id", false);
	}
	
	public void addOrUpdatePicture(DataTcSsMoviePicture pic) {
		DatabasePower power = DatabaseSource.getDatabasePower();
		power.doUpdateSerialize(pic, "id", false);
	}
	
	public List<DataMovieListInfoDto> getAllOnlineMovie() {
//		DatabasePower power = DatabaseSource.getDatabasePower();
//		String sql = '''
//			select
//			a.id,
//			a.film_no,
//			a.film_name,
//			a.film_subtitle,
//			a.film_language,
//			a.film_format,
//			a.film_region,
//			a.release_date,
//			a.film_dimensional,
//			a.film_size,
//			b.cinema_movie_name
//			from
//			data_tc_ss_movie_info a
//			join
//			data_tc_ss_movie_attach b
//			on a.film_no = b.film_no
//			where b.offline_date >= ?
//		''';
//		
//		return power.doQueryList(DataMovieListInfoDto.class, sql,new java.sql.Date(System.currentTimeMillis()));
		
		DatabasePower power = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			a.id,
			a.film_no,
			a.film_name,
			a.film_subtitle,
			a.film_language,
			a.film_format,
			a.film_region,
			a.release_date,
			a.film_dimensional,
			a.film_size
			from
			data_tc_ss_movie_info a
			where a.flag!=?
		''';
		
		return power.doQueryList(DataMovieListInfoDto.class, sql, -1);
	}
	
	public void updateMovieOfflineDate(String filmNo,String offlineDate,String filmName, def pid) {
		DatabasePower power = DatabaseSource.getDatabasePower();
		
		String checkExistsSql = '''
			select id,film_no,offline_date,flag,a_time from data_tc_ss_movie_attach where film_no = ? and pid=?
		''';

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date newOfflineDate = new java.sql.Date(sdf.parse(offlineDate).getTime());
		
		DataTcSsMovieAttach extsisObj = power.doQueryUnique(DataTcSsMovieAttach.class, checkExistsSql,filmNo,pid);
		
		if(extsisObj != null && extsisObj.getOffline_date() != null) {
			if(extsisObj.getOffline_date().getTime() < newOfflineDate.getTime()) {
				extsisObj.setOffline_date(newOfflineDate);
				power.doUpdateSerialize(extsisObj, "id",false);
			}else {
				//原来存储的下线时间更靠后
			}
		}else {
			if(extsisObj == null) {
				extsisObj = new DataTcSsMovieAttach();
				extsisObj.setA_time(new Date());
				extsisObj.pid = new Long(pid.toString());
			}
			extsisObj.setCinema_movie_name(filmName);
			extsisObj.setFilm_no(filmNo)
			extsisObj.setOffline_date(newOfflineDate)
			power.doUpdateSerialize(extsisObj, "id",false);
		}
		
	}
	
	public List<DataTcSsMovieCelebrityDto> getCelesByMovieId(Long movieId){
		String sql = "select * from data_tc_ss_movie_celebrity where movie_id = ? and flag != ? ";
		return DatabaseSource.getDatabasePower().doQueryList(DataTcSsMovieCelebrityDto.class, sql,movieId,-1);
	}
	
	public List<DataTcSsMoviePictureDto> getPicsByMovieId(Long movieId){
		String sql = "select * from data_tc_ss_movie_picture where m_id = ? and flag != ? ";
		return DatabaseSource.getDatabasePower().doQueryList(DataTcSsMoviePictureDto.class, sql,movieId,-1);
	}
}
