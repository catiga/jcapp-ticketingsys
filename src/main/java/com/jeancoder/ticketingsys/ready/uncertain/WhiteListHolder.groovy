package com.jeancoder.ticketingsys.ready.uncertain

class WhiteListHolder {
	
	//需要正常出票的手机号码
	static def _mobilelist_ = [];
	
	static {
		_mobilelist_.add('13501020884');
	}

	def static judge(def mobile) {
		def cut = true;
		for(x in _mobilelist_) {
			if(x==mobile) {
				//不能拦截，要真实出票
				cut = false;
				break;
			}
		}
		return cut;
	}
}
