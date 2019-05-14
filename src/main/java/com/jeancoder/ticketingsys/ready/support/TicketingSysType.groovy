package com.jeancoder.ticketingsys.ready.support

class TicketingSysType {
	private String code;
	private String name;
	
	TicketingSysType(){}
	
	TicketingSysType(String code,String name){
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
