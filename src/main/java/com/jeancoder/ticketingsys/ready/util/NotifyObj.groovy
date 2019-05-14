package com.jeancoder.ticketingsys.ready.util

class NotifyObj {

	def code;
	
	def text;
	
	def err_code;
	
	def err_code_desc;
	
	def other;
	
	static def build(def code, def text, def err_code, def err_code_desc, def other) {
		return new NotifyObj(code:code, text:text, err_code:err_code, err_code_desc:err_code_desc, other:other);
	}
	
	static def succ() {
		return new NotifyObj(code:'0', text:'操作成功');
	}
}
