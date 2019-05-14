package com.jeancoder.ticketingsys.ready.schema.dto

class GroupWithSchema {
	private Long id;
	private String group_name;
	private Date a_time;
	private List<SchemaWithItem> schemas;
	private String group_type;
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
	public List<SchemaWithItem> getSchemas() {
		return schemas;
	}
	public void setSchemas(List<SchemaWithItem> schemas) {
		this.schemas = schemas;
	}
	public String getGroup_type() {
		return group_type;
	}
	public void setGroup_type(String group_type) {
		this.group_type = group_type;
	}
	
}

