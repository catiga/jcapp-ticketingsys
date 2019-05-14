package com.jeancoder.ticketingsys.ready.order.dto

class OrderSeatItemDto {
	private Long id;
	private Long order_id;
	private String seat_no;
	private Integer seat_gr;
	private Integer seat_gc;
	private String seat_sr;
	private String seat_sc;
	private String handle_fee;
	private String sale_fee;
	private String pub_fee;
	
	private String service_fee;
	
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
	public String getSeat_no() {
		return seat_no;
	}
	public void setSeat_no(String seat_no) {
		this.seat_no = seat_no;
	}
	public Integer getSeat_gr() {
		return seat_gr;
	}
	public void setSeat_gr(Integer seat_gr) {
		this.seat_gr = seat_gr;
	}
	public Integer getSeat_gc() {
		return seat_gc;
	}
	public void setSeat_gc(Integer seat_gc) {
		this.seat_gc = seat_gc;
	}
	public String getSeat_sr() {
		return seat_sr;
	}
	public void setSeat_sr(String seat_sr) {
		this.seat_sr = seat_sr;
	}
	public String getSeat_sc() {
		return seat_sc;
	}
	public void setSeat_sc(String seat_sc) {
		this.seat_sc = seat_sc;
	}
	public String getHandle_fee() {
		return handle_fee;
	}
	public void setHandle_fee(String handle_fee) {
		this.handle_fee = handle_fee;
	}
	public String getSale_fee() {
		return sale_fee;
	}
	public void setSale_fee(String sale_fee) {
		this.sale_fee = sale_fee;
	}
	public String getPub_fee() {
		return pub_fee;
	}
	public void setPub_fee(String pub_fee) {
		this.pub_fee = pub_fee;
	}
	public String getService_fee() {
		return service_fee;
	}
	public void setService_fee(String service_fee) {
		this.service_fee = service_fee;
	}
	
}
