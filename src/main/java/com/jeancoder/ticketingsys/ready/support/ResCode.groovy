package com.jeancoder.ticketingsys.ready.support

class ResCode {
	private int code;
	private String msg;
	public ResCode() {}
	public ResCode(int code,String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
