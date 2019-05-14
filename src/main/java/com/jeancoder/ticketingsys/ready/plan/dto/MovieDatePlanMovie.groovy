package com.jeancoder.ticketingsys.ready.plan.dto

import java.util.List

import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieCelebrityDto
import com.jeancoder.ticketingsys.ready.film.dto.DataTcSsMovieFullInfo

class MovieDatePlanMovie {
	private String name;
	private String img;
	private String score;
	private List<MovieDatePlanDate> dates = new ArrayList<MovieDatePlanDate>();
	private DataTcSsMovieFullInfo properties;
	private List<DataTcSsMovieCelebrityDto> celebritys;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public List<MovieDatePlanDate> getDates() {
		return dates;
	}
	public void setDates(List<MovieDatePlanDate> dates) {
		this.dates = dates;
	}
	
	public MovieDatePlanDate matchAddedDate(String date) {
		if(dates == null) {
			return null;
		}
		for(MovieDatePlanDate addedDate : dates) {
			if(addedDate.getDate().equals(date)) {
				return addedDate;
			}
		}
		return null;
	}
	public DataTcSsMovieFullInfo getProperties() {
		return properties;
	}
	public void setProperties(DataTcSsMovieFullInfo properties) {
		this.properties = properties;
	}
	public List<DataTcSsMovieCelebrityDto> getCelebritys() {
		return celebritys;
	}
	public void setCelebritys(List<DataTcSsMovieCelebrityDto> celebritys) {
		this.celebritys = celebritys;
	}
	
}
