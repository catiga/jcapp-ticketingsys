package com.jeancoder.ticketingsys.ready.film.entity

import java.util.Date;

class DataTcSsMovieAttach {
	private Long id;
	private Long pid;
	private String film_no;
	private java.sql.Date offline_date;
	private String cinema_movie_name;
	private Integer flag = 0;
	private Date a_time;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFilm_no() {
		return film_no;
	}
	public void setFilm_no(String film_no) {
		this.film_no = film_no;
	}
	public java.sql.Date getOffline_date() {
		return offline_date;
	}
	public void setOffline_date(java.sql.Date offline_date) {
		this.offline_date = offline_date;
	}
	public String getCinema_movie_name() {
		return cinema_movie_name;
	}
	public void setCinema_movie_name(String cinema_movie_name) {
		this.cinema_movie_name = cinema_movie_name;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Date getA_time() {
		return a_time;
	}
	public void setA_time(Date a_time) {
		this.a_time = a_time;
	} 
	
	
}
