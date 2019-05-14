package com.jeancoder.ticketingsys.ready.film.dto

class DataTcSsMoviePictureDto {
	private Long id;
	private Long m_id;
	private String pic_url;
	private String pic_type;
	private Integer flag = 0;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getM_id() {
		return m_id;
	}
	public void setM_id(Long m_id) {
		this.m_id = m_id;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getPic_type() {
		return pic_type;
	}
	public void setPic_type(String pic_type) {
		this.pic_type = pic_type;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
