package com.jeancoder.ticketingsys.ready.support

import java.lang.reflect.Field

class Codes{
	public static final ResCode SUCCESS = new ResCode(0,"成功");
	
	public static final ResCode INTERNAL_SERVER_ERROR = new ResCode(10000,"系统错误");
	
	public static final ResCode COMMON_PARAM_ERROR = new ResCode(10001,"请检查输入参数");
	public static final ResCode COMMON_CINEMA_CONFIG_ERROR = new ResCode(10002,"影城不存在或配置错误");
	public static final ResCode COMMON_RECORD_REPEAT = new ResCode(10002,"记录重复");
	
	public static final ResCode CINEMA_CONN_FAILED = new ResCode(40001,"影院连接失败");
	public static final ResCode CINEMA_CHANNEL_CODE_NOT_MATCH = new ResCode(40002,"售票系统与影城编码不匹配");
	public static final ResCode CINEMA_EMPTY_CINEMA = new ResCode(40003,"当前选择售票系统未获取到影城");
	public static final ResCode CINEMA_CHOOSE_SEAT_SELLED = new ResCode(40004,"座位已售出");
	public static final ResCode CINEMA_CHOOSE_SEAT_EMPTY = new ResCode(40005,"请选择座位");
	
	public static final ResCode CINEMA_GENORDER_SCHEMA_ERROR = new ResCode(50001,"传入的票类有误");
	public static final ResCode CINEMA_GENORDER_ORDERNO_NOTFOUND = new ResCode(50002,"订单编号不存在");
	public static final ResCode CINEMA_GENORDER_UNLOCK_FAILED = new ResCode(50003,"解锁失败，请检查订单是否已经解锁，或者已经出票");
	
	public static void main(String[] args) {
		//生成markdown错误码描述文档
		for(Field f : Codes.class.getDeclaredFields()) {
			if(f.getType().toString().indexOf("ResCode") != -1) {
				ResCode r = (ResCode)f.get(Codes.class);
				print "> "+r.getCode()+">>>"+r.getMsg()+"  ";
				println ""
			}
		}
	}
	
}

