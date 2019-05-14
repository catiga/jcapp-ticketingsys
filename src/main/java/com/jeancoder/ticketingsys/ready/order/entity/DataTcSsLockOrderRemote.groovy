package com.jeancoder.ticketingsys.ready.order.entity

class DataTcSsLockOrderRemote {
	private Long id;
	private Long order_id;
	private String lock_flag;
	private String ticket_flag_1;
	private String ticket_flag_2;
	private String ticket_refund;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
	public String getLock_flag() {
		return lock_flag;
	}
	public void setLock_flag(String lock_flag) {
		this.lock_flag = lock_flag;
	}
	public String getTicket_flag_1() {
		return ticket_flag_1;
	}
	public void setTicket_flag_1(String ticket_flag_1) {
		this.ticket_flag_1 = ticket_flag_1;
	}
	public String getTicket_flag_2() {
		return ticket_flag_2;
	}
	public void setTicket_flag_2(String ticket_flag_2) {
		this.ticket_flag_2 = ticket_flag_2;
	}
	public String getTicket_refund() {
		return ticket_refund;
	}
	public void setTicket_refund(String ticket_refund) {
		this.ticket_refund = ticket_refund;
	}
}
