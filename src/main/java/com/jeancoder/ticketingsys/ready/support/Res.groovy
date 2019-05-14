package com.jeancoder.ticketingsys.ready.support

class Res {
	private int code;
	private String msg;
	private Object data;
	
	private Res() {}
	
	public static Res Success() {
		Res r = new Res();
		r.setCode(0);
		return r;
	}
	
	public static Res Success(Object data) {
		Res r = new Res();
		r.setCode(0);
		r.setData(data);
		return r;
	}
	
	public static Res Failed(ResCode code) {
		Res r = new Res();
		r.setCode(code.getCode());
		r.setMsg(code.getMsg());
		return r;
	}
	public static Res Failed(ResCode code,String addition) {
		Res r = new Res();
		r.setCode(code.getCode());
		r.setMsg(addition != null && !"".equals(addition) ? code.getMsg() +"："+addition : code.getMsg());
		return r;
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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
