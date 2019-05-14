package com.jeancoder.ticketingsys.ready.schema.dto

class GroupInfo {
	private Long id;
	private String group_name;
	private Date a_time;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public Date getA_time() {
		return a_time;
	}
	public void setA_time(Date a_time) {
		this.a_time = a_time;
	}
}
