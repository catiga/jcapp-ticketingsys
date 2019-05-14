package com.jeancoder.ticketingsys.ready.schema.dto

class SchemaWithItem {
	private Long id;
	private Long group_id;
	private String schema_name;
	private Integer sort_num;
	private String schema_status;
	private Boolean by_permission;
	private String week_rule;
	private String time_rule;
	private String month_rule;
	private Date a_time;
	private List<SchemaChildItem> items;
	private Boolean selected = false;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
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
	public Date getA_time() {
		return a_time;
	}
	public void setA_time(Date a_time) {
		this.a_time = a_time;
	}
	public List<SchemaChildItem> getItems() {
		return items;
	}
	public void setItems(List<SchemaChildItem> items) {
		this.items = items;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
