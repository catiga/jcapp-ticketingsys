package com.jeancoder.ticketingsys.ready.support

import java.text.SimpleDateFormat

class OrderNoHelper {
	/**
	 * 一个简易的20位的订单号生成规则
	 * 碰撞几率可能较高
	 * @return
	 */
	public static String gene() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date())+Math.round(Math.random()*1000)
	}
	public static void main(String[] args) {
		println gene()
	}
}
