package com.jeancoder.ticketingsys.ready.schema.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = "ticket_price_schema_group")
class TicketPriceSchemaGroup {

	@JCID
	private Long id;
	private String group_name;
	private String group_type;
	private BigInteger pid;
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
	public String getGroup_type() {
		return group_type;
	}
	public void setGroup_type(String group_type) {
		this.group_type = group_type;
	}
	public BigInteger getPid() {
		return pid;
	}
	public void setPid(BigInteger pid) {
		this.pid = pid;
	}
}
