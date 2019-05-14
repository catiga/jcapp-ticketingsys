package com.jeancoder.ticketingsys.ready.order.dto

import com.jeancoder.ticketingsys.ready.support.MoneyUtil

class SeatOrderDto {
	private Long id;
	private String order_no;
	private Date a_time;
	private Integer ticket_sum;
	private String plan_date;
	private String plan_time;
	private String store_name;
	private String hall_name;
	private String film_name;
	private String total_amount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public Date getA_time() {
		return a_time;
	}
	public void setA_time(Date a_time) {
		this.a_time = a_time;
	}
	public Integer getTicket_sum() {
		return ticket_sum;
	}
	public void setTicket_sum(Integer ticket_sum) {
		this.ticket_sum = ticket_sum;
	}
	public String getPlan_date() {
		return plan_date;
	}
	public void setPlan_date(String plan_date) {
		this.plan_date = plan_date;
	}
	public String getPlan_time() {
		return plan_time;
	}
	public void setPlan_time(String plan_time) {
		this.plan_time = plan_time;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getHall_name() {
		return hall_name;
	}
	public void setHall_name(String hall_name) {
		this.hall_name = hall_name;
	}
	public String getFilm_name() {
		return film_name;
	}
	public void setFilm_name(String film_name) {
		this.film_name = film_name;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = MoneyUtil.divide(total_amount,"100");
	}
}
