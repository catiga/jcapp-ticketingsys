package com.jeancoder.ticketingsys.ready.prefer

class PreferFactory {

	static def generate(def ct, def oc = '2000') {
		if(ct=='100') {
			//会员卡类
			return new McPrefer(oc:oc);
		} else if(ct=='200') {
			//券类
			return new CouponPrefer(oc:oc);
		} else if(ct=='300') {
			//活动类
			return new MarketPrefer(oc:oc);
		}
		return null;
	}
	
	static def main(def argc) {
		Prefer p = generate('100');
		println p;
	}
}
