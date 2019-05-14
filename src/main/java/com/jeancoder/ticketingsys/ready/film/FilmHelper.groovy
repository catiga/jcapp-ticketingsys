package com.jeancoder.ticketingsys.ready.film

import com.jeancoder.ticketingsys.ready.film.entity.DataTcSsMoviePicture
import com.jeancoder.ticketingsys.ready.film.service.FilmService
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanMovie

class FilmHelper {
	public static final FilmHelper INSTANCE = new FilmHelper();
	
	
	public DataTcSsMoviePicture printMovie() {
		return new DataTcSsMoviePicture();
	}
	
	
	public void syncFilmPoster(List<CinemaPlan> plans, def pid){
		Map<String,CinemaPlanMovie> films = distinctFilm(plans,pid);
		for(String filmCode : films.keySet()) {
			CinemaPlanMovie fiml = films.get(filmCode);
			syncFilmAction(fiml, pid);
		}
	}
	
	public Map<String,CinemaPlanMovie> distinctFilm(List<CinemaPlan> plans,def pid){
		Map<String,CinemaPlanMovie> films = new HashMap<String,CinemaPlanMovie>();
		for(CinemaPlan plan : plans) {
			for(CinemaPlanMovie movie : plan.getMovieInfo()) {
				try {
					//更新影片下线时间
					FilmService.INSTANCE.updateMovieOfflineDate(movie.getCineMovieNum(), plan.getBusinessDate(),movie.getMovieName(),pid);
					//MovieAttachService.INSTANCE.updateMovieOfflineDate(movie.getCineMovieNum(), plan.getBusinessDate(), movie.getMovieName(),pid);
				}catch(Exception e) {
					e.printStackTrace()
					//更新失败也没关系
				}
				films.put(movie.getCineMovieNum(), movie);
			}
		}
		
		return films;
	}
	
	private void syncFilmAction(CinemaPlanMovie film,def pid) {
		new SyncThread(film,pid).start();
	}
	
}
