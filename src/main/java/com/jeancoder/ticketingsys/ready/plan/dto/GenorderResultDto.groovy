package com.jeancoder.ticketingsys.ready.plan.dto

class GenorderResultDto {
	private String orderNo;
	private String totalAmount;
	
	String tradeNum;
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
