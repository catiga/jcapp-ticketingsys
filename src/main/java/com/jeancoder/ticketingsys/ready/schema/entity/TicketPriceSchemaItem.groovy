package com.jeancoder.ticketingsys.ready.schema.entity

class TicketPriceSchemaItem {
	private BigInteger id;
	private BigInteger schema_id;
	private String movie_dimensional;
	private String movie_size;
	private Long price;
	private Integer sort_num;
	private Boolean is_custom;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getSchema_id() {
		return schema_id;
	}
	public void setSchema_id(BigInteger schema_id) {
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
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getSort_num() {
		return sort_num;
	}
	public void setSort_num(Integer sort_num) {
		this.sort_num = sort_num;
	}
	public Boolean getIs_custom() {
		return is_custom;
	}
	public void setIs_custom(Boolean is_custom) {
		this.is_custom = is_custom;
	}
	
}
