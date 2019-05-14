package com.jeancoder.ticketingsys.ready.film.dto

class DataMovieListInfoDto {
	private Long id;
	private String film_no;
	private String film_name;
	private String film_subtitle;
	private String film_language;
	private String film_format;
	private String film_region;
	private String cinema_movie_name;
	private String film_dimensional;
	private String film_size;
	private String release_date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFilm_name() {
		return film_name;
	}
	public void setFilm_name(String film_name) {
		this.film_name = film_name;
	}
	public String getFilm_subtitle() {
		return film_subtitle;
	}
	public void setFilm_subtitle(String film_subtitle) {
		this.film_subtitle = film_subtitle;
	}
	public String getFilm_language() {
		return film_language;
	}
	public void setFilm_language(String film_language) {
		this.film_language = film_language;
	}
	public String getFilm_format() {
		return film_format;
	}
	public void setFilm_format(String film_format) {
		this.film_format = film_format;
	}
	public String getFilm_region() {
		return film_region;
	}
	public void setFilm_region(String film_region) {
		this.film_region = film_region;
	}
	public String getRelease_date() {
		return release_date;
	}
	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	public String getCinema_movie_name() {
		return cinema_movie_name;
	}
	public void setCinema_movie_name(String cinema_movie_name) {
		this.cinema_movie_name = cinema_movie_name;
	}
	public String getFilm_no() {
		return film_no;
	}
	public void setFilm_no(String film_no) {
		this.film_no = film_no;
	}
	public String getFilm_dimensional() {
		return film_dimensional;
	}
	public void setFilm_dimensional(String film_dimensional) {
		this.film_dimensional = film_dimensional;
	}
	public String getFilm_size() {
		return film_size;
	}
	public void setFilm_size(String film_size) {
		this.film_size = film_size;
	}
	
	
}
