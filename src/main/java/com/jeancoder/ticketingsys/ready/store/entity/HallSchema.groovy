package com.jeancoder.ticketingsys.ready.store.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "hall_schema")
class HallSchema {
	@JCID
	private Long id;
	private Long store_id;
	private String hall_id;
	private Long schema_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStore_id() {
		return store_id;
	}
	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}
	public String getHall_id() {
		return hall_id;
	}
	public void setHall_id(String hall_id) {
		this.hall_id = hall_id;
	}
	public Long getSchema_id() {
		return schema_id;
	}
	public void setSchema_id(Long schema_id) {
		this.schema_id = schema_id;
	}
}
