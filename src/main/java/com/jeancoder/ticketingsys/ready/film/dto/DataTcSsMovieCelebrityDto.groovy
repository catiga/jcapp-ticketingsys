package com.jeancoder.ticketingsys.ready.film.dto

class DataTcSsMovieCelebrityDto {
	private Long id;
	private Long movie_id;
	private Long cele_id;
	private String role_type;
	private String role_name;
	private String title_img;
	private String cele_name;
	private String cele_name_en;
	private Date a_time;
	private Integer flag = 0;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(Long movie_id) {
		this.movie_id = movie_id;
	}
	public Long getCele_id() {
		return cele_id;
	}
	public void setCele_id(Long cele_id) {
		this.cele_id = cele_id;
	}
	public String getRole_type() {
		return role_type;
	}
	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getTitle_img() {
		return title_img;
	}
	public void setTitle_img(String title_img) {
		this.title_img = title_img;
	}
	public String getCele_name() {
		return cele_name;
	}
	public void setCele_name(String cele_name) {
		this.cele_name = cele_name;
	}
	public String getCele_name_en() {
		return cele_name_en;
	}
	public void setCele_name_en(String cele_name_en) {
		this.cele_name_en = cele_name_en;
	}
	public Date getA_time() {
		return a_time;
	}
	public void setA_time(Date a_time) {
		this.a_time = a_time;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
