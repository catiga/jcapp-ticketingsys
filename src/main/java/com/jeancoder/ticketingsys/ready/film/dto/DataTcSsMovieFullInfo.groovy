package com.jeancoder.ticketingsys.ready.film.dto

import java.lang.reflect.Method

public class DataTcSsMovieFullInfo {
	
	public DataTcSsMovieFullInfo() {
	}
	
	private Long id;
	private String film_no;
	private String film_name;
	private String film_subtitle;
	private String film_brief;
	private String film_content;
	private String film_language;
	private String film_format;
	private String film_dimensional;
	private String film_size;
	private String film_region;
	private String release_date;
	private String pic_entry;
	private String pic_small;
	private String prevue;
	private String film_score;
	private Integer time_diff;
	private String film_type;
	private String film_alias_name;
	private String film_source;
	private Integer flag = 0;
	private Date a_time;
	private List<DataTcSsMoviePictureDto> pictures;
	private List<DataTcSsMovieCelebrityDto> celebritys;
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
	public String getFilm_brief() {
		return film_brief;
	}
	public void setFilm_brief(String film_brief) {
		this.film_brief = film_brief;
	}
	public String getFilm_content() {
		return film_content;
	}
	public void setFilm_content(String film_content) {
		this.film_content = film_content;
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
	public String getPic_entry() {
		return pic_entry;
	}
	public void setPic_entry(String pic_entry) {
		this.pic_entry = pic_entry;
	}
	public String getPic_small() {
		return pic_small;
	}
	public void setPic_small(String pic_small) {
		this.pic_small = pic_small;
	}
	public String getPrevue() {
		return prevue;
	}
	public void setPrevue(String prevue) {
		this.prevue = prevue;
	}
	public String getFilm_score() {
		return film_score;
	}
	public void setFilm_score(String film_score) {
		this.film_score = film_score;
	}
	public Integer getTime_diff() {
		return time_diff;
	}
	public void setTime_diff(Integer time_diff) {
		this.time_diff = time_diff;
	}
	public String getFilm_type() {
		return film_type;
	}
	public void setFilm_type(String film_type) {
		this.film_type = film_type;
	}
	public String getFilm_alias_name() {
		return film_alias_name;
	}
	public void setFilm_alias_name(String film_alias_name) {
		this.film_alias_name = film_alias_name;
	}
	public String getFilm_source() {
		return film_source;
	}
	public void setFilm_source(String film_source) {
		this.film_source = film_source;
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
	public List<DataTcSsMoviePictureDto> getPictures() {
		return pictures;
	}
	public void setPictures(List<DataTcSsMoviePictureDto> pictures) {
		this.pictures = pictures;
	}
	public List<DataTcSsMovieCelebrityDto> getCelebritys() {
		return celebritys;
	}
	public void setCelebritys(List<DataTcSsMovieCelebrityDto> celebritys) {
		this.celebritys = celebritys;
	}
}
