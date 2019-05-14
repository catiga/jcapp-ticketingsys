package com.jeancoder.ticketingsys.ready.support

import java.lang.reflect.Field
import java.util.List
import java.util.ArrayList

class TicketingSysTypeHelper {
	public static final TicketingSysType TC_DINGXIN = new TicketingSysType(TicketingSysCode.DINGXIN,"鼎鑫");
	public static final TicketingSysType TC_HUOLIENIAO = new TicketingSysType(TicketingSysCode.HUOLIENIAO,"凤凰佳影");
	public static final TicketingSysType TC_YUEKE_YUN = new TicketingSysType(TicketingSysCode.YUEKE_YUN,"凤凰佳影云版本");
	public static final TicketingSysType TC_CHENGXING = new TicketingSysType(TicketingSysCode.CHENGXING,"晨星");
	public static final TicketingSysType TC_MANTIANXING = new TicketingSysType(TicketingSysCode.MANTIANXING,"满天星");
	public static final TicketingSysType TC_MANTIANXING_V2 = new TicketingSysType(TicketingSysCode.MANTIANXING_V2,"满天星2.0");
	public static final TicketingSysType TC_M1905 = new TicketingSysType(TicketingSysCode.M1905,"m1905");
	public static final TicketingSysType TC_BLESSED = new TicketingSysType(TicketingSysCode.BLESSED,"幸福蓝海");
	public static final TicketingSysType TC_PDR = new TicketingSysType(TicketingSysCode.PDR,"嗨票");
	
	public static List<TicketingSysType> getSupportSystem(){
		List<TicketingSysType> types = new ArrayList<TicketingSysType>();
		for(Field f : TicketingSysTypeHelper.class.getDeclaredFields()) {
			if(f.getName().startsWith("TC_")) {
				types.add(f.get(TicketingSysTypeHelper.class));
			}
		}
		return types;
	}
	
	public static String getSysName(String sysCode) {
		for(Field f : TicketingSysTypeHelper.class.getDeclaredFields()) {
			if(f.getName().startsWith("TC_")) {
				TicketingSysType type = f.get(TicketingSysTypeHelper.class);
				if(type.getCode().equals(sysCode)) {
					return type.getName();
				}
			}
		}
		return null;
	}
	
	/**
	 * 转换成老版本售票系统code
	 * @param ticketingSysCode
	 * @return
	 */
	public static String transToOldCode(String ticketingSysCode) {
		if(TicketingSysCode.DINGXIN.equals(ticketingSysCode)) {
			return "10";
		}else if(TicketingSysCode.HUOLIENIAO.equals(ticketingSysCode)) {
			return "90";	//凤凰佳影
		}else if(TicketingSysCode.CHENGXING.equals(ticketingSysCode)) {
			return "80";
		}else if(TicketingSysCode.MANTIANXING.equals(ticketingSysCode)) {
			return "22";
		}else if(TicketingSysCode.MANTIANXING_V2.equals(ticketingSysCode)) {
			return "21";
		}else if(TicketingSysCode.M1905.equals(ticketingSysCode)) {
			return "70";
		}else if(TicketingSysCode.BLESSED.equals(ticketingSysCode)) {
			return "100";
		}else if(TicketingSysCode.YUEKE_YUN.equals(ticketingSysCode)) {
			return "91";	//凤凰佳影云版本
		}else if(TicketingSysCode.PDR.equals(ticketingSysCode)) {
			return "11";
		}
	}
	
//	public static void main(String[] args) {
//		println TicketingSysTypeHelper.getSupportSystem().size();
//	}
}
