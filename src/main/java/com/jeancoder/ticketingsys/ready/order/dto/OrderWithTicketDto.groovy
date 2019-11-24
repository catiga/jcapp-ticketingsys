package com.jeancoder.ticketingsys.ready.order.dto

import com.jeancoder.ticketingsys.ready.order.entity.DataTcSsSaleOrderRemote

class OrderWithTicketDto {
	private Long id;
	private String order_no;
	private String mobile;
	private String order_status;
	private String total_amount;
	private String pay_amount;
	private Date a_time;
	private Date pay_time;
	private Date check_time;
	private Date deliver_time;
	private Integer ticket_sum;
	private Integer store_id;
	private String store_name;
	private String hall_id;
	private String hall_name;
	private String plan_id;
	private String plan_date;
	private String plan_time;
	private String film_name;
	private String pic_url;
	private String pay_type;
	private String o_c;
	private String film_no;
	
	String film_dimensional;
	
	//private String handle_fee;
	private Date drawback_time;
	private Date refund_time;
	private String remark;
	private List<OrderSeatItemDto> seats;
	private DataTcSsSaleOrderRemote ticket;
	
	Long proj_id;
	
	private BigDecimal handle_fee;
	
	private BigDecimal service_fee;
	
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	public Date getCheck_time() {
		return check_time;
	}
	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}
	public Date getDeliver_time() {
		return deliver_time;
	}
	public void setDeliver_time(Date deliver_time) {
		this.deliver_time = deliver_time;
	}
	public Integer getTicket_sum() {
		return ticket_sum;
	}
	public void setTicket_sum(Integer ticket_sum) {
		this.ticket_sum = ticket_sum;
	}
	public Integer getStore_id() {
		return store_id;
	}
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getHall_id() {
		return hall_id;
	}
	public void setHall_id(String hall_id) {
		this.hall_id = hall_id;
	}
	public String getHall_name() {
		return hall_name;
	}
	public void setHall_name(String hall_name) {
		this.hall_name = hall_name;
	}
	public String getPlan_id() {
		return plan_id;
	}
	public void setPlan_id(String plan_id) {
		this.plan_id = plan_id;
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
	public String getFilm_name() {
		return film_name;
	}
	public void setFilm_name(String film_name) {
		this.film_name = film_name;
	}
	public String getPic_url() {
		return pic_url;
	}
	public void setPic_url(String pic_url) {
		this.pic_url = pic_url;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getO_c() {
		return o_c;
	}
	public void setO_c(String o_c) {
		this.o_c = o_c;
	}
	public BigDecimal getHandle_fee() {
		return handle_fee;
	}
	public void setHandle_fee(BigDecimal handle_fee) {
		this.handle_fee = handle_fee;
	}
	public Date getDrawback_time() {
		return drawback_time;
	}
	public void setDrawback_time(Date drawback_time) {
		this.drawback_time = drawback_time;
	}
	public Date getRefund_time() {
		return refund_time;
	}
	public void setRefund_time(Date refund_time) {
		this.refund_time = refund_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<OrderSeatItemDto> getSeats() {
		return seats;
	}
	public void setSeats(List<OrderSeatItemDto> seats) {
		this.seats = seats;
	}
	public DataTcSsSaleOrderRemote getTicket() {
		return ticket;
	}
	public void setTicket(DataTcSsSaleOrderRemote ticket) {
		this.ticket = ticket;
	}
	public Date getA_time() {
		return a_time;
	}
	public void setA_time(Date a_time) {
		this.a_time = a_time;
	}
	public BigDecimal getService_fee() {
		return service_fee;
	}
	public void setService_fee(BigDecimal service_fee) {
		this.service_fee = service_fee;
	}
	public String getFilm_no() {
		return film_no;
	}
	public void setFilm_no(String film_no) {
		this.film_no = film_no;
	}
	public String getFilm_dimensional() {
		return film_dimensional;
	}
	public void setFilm_dimensional(String film_dimensional) {
		this.film_dimensional = film_dimensional;
	}
	
}
