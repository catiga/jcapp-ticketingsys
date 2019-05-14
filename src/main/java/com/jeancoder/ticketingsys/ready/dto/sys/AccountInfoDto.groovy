package com.jeancoder.ticketingsys.ready.dto.sys

class AccountInfoDto {
	BigInteger acmid;
	String cn;
	String levelname;//等级名称
	String balance;//余额
	String mcmobile;//电话
	String mcname;//姓名
	String point;//积分
	String mch_id;//会员卡等级id
	String id_card;//身份证号
	String mc_num;//会员卡号
	String validate_type;//失效日期类型
	Long validate_period;//会员卡有效期
	String card_code;//二维码
	String content//会员卡权益
//	String h_name;
	def rule;//会员卡规则
}
