package com.jeancoder.ticketingsys.ready.schema.entity

class TicketPriceSchema {
	private BigInteger id;
	private BigInteger group_id;
	private String schema_name;
	private Integer sort_num;
	private String schema_status;
	private Boolean by_permission;
	private String week_rule;
	private String time_rule;
	private String month_rule;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getGroup_id() {
		return group_id;
	}
	public void setGroup_id(BigInteger group_id) {
		this.group_id = group_id;
	}
	public String getSchema_name() {
		return schema_name;
	}
	public void setSchema_name(String schema_name) {
		this.schema_name = schema_name;
	}
	public Integer getSort_num() {
		return sort_num;
	}
	public void setSort_num(Integer sort_num) {
		this.sort_num = sort_num;
	}
	public String getSchema_status() {
		return schema_status;
	}
	public void setSchema_status(String schema_status) {
		this.schema_status = schema_status;
	}
	public Boolean getBy_permission() {
		return by_permission;
	}
	public void setBy_permission(Boolean by_permission) {
		this.by_permission = by_permission;
	}
	public String getWeek_rule() {
		return week_rule;
	}
	public void setWeek_rule(String week_rule) {
		this.week_rule = week_rule;
	}
	public String getTime_rule() {
		return time_rule;
	}
	public void setTime_rule(String time_rule) {
		this.time_rule = time_rule;
	}
	public String getMonth_rule() {
		return month_rule;
	}
	public void setMonth_rule(String month_rule) {
		this.month_rule = month_rule;
	}
}
