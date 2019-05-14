package com.jeancoder.ticketingsys.ready.schema.dto

import com.jeancoder.ticketingsys.ready.support.MoneyUtil

class SchemaChildItem {
	private Long id;
	private Long schema_id;
	private String movie_dimensional;
	private String movie_size;
	private Long price;
	private Integer sort_num;
	private Date a_time;
	private Boolean is_custom;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSchema_id() {
		return schema_id;
	}
	public void setSchema_id(Long schema_id) {
		this.schema_id = schema_id;
	}
	public String getMovie_dimensional() {
		return movie_dimensional;
	}
	public void setMovie_dimensional(String movie_dimensional) {
		this.movie_dimensional = movie_dimensional;
	}
	public String getMovie_size() {
		return movie_size;
	}
	public void setMovie_size(String movie_size) {
		this.movie_size = movie_size;
	}
	public Long getPrice() {
		return price;
	}
	public String getPriceYuan() {
		return MoneyUtil.divide(""+price, "100");
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getSort_num() {
		return sort_num;
	}
	public void setSort_num(Integer sort_num) {
		this.sort_num = sort_num;
	}
	public Date getA_time() {
		return a_time;
	}
	public void setA_time(Date a_time) {
		this.a_time = a_time;
	}
	public Boolean getIs_custom() {
		return is_custom;
	}
	public void setIs_custom(Boolean is_custom) {
		this.is_custom = is_custom;
	}
	
}
