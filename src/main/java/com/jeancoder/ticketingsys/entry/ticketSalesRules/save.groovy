package com.jeancoder.ticketingsys.entry.ticketSalesRules

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.entity.TicketSalesRules
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.ticketSalesRules.ticketSalesRulesService
import com.jeancoder.ticketingsys.ready.util.DateUtil

String id = JC.request.param("id");
String title = JC.request.param("mc_name");
String info = JC.request.param("mc_info");
String price_streg = JC.request.param("mc_p_streg");
String allow_low_price = JC.request.param("mc_isPrice");
String time_streg = JC.request.param("spru_time_spec");
String store_type = JC.request.param("mc_store");
String store_type_name = JC.request.param("mc_store_name");
String content = JC.request.param("mc_content");
String spru_time_type = JC.request.param("spru_time_type");
String movie_type = JC.request.param("mc_l_ms");
String movie_type_name = JC.request.param("mc_l_ms_name");
String hall_id = JC.request.param("hall_id");
// 检查不同类型兑换的参数

JCLogger logger = LoggerSource.getLogger();

try{
	if ( StringUtil.isEmpty(info)) {
		return new Result().setData(AvailabilityStatus.notAvailable("规则简介不能为空"));
	}
	if (StringUtil.isEmpty(title) ) {
		return new Result().setData(AvailabilityStatus.notAvailable("规则名称不能为空"));
	}
	if (StringUtil.isEmpty(price_streg)) {
		return new Result().setData(AvailabilityStatus.notAvailable("价格变动值不能为空"));
	}
	String spru_time_spec = DateUtil.compareW_to_R(spru_time_type,time_streg);
	TicketSalesRules rule =new TicketSalesRules();
	//rule.id=new BigInteger(id.toString());
	String [] price_streg_value =price_streg.split("/");
	String price_value='';
	for (int j=0;j<price_streg_value.length;j++) {
		String [] price = price_streg_value[j].split(",");
		String price_value1 = price[0]+","+price[1]+","+price[2]+","+MoneyUtil.get_cent_from_yuan(price[3]);
		price_value =price_value1 + '/'+price_value;
	}
	String price_value2=price_value.substring(0,price_value.length()-1);
	rule.title=title;
	rule.info=info;
	rule.time_streg=spru_time_spec;
	rule.content=content;
	rule.store_type=store_type;
	rule.movie_type=movie_type;
	rule.hall_id = hall_id;
	rule.time_type= spru_time_type;
	rule.store_type_name=store_type_name;
	rule.allow_low_price=new Integer(allow_low_price.toString());
	rule.movie_type_name=movie_type_name;
	rule.price_streg=price_value2;
	String resultStr=null;
	if (StringUtil.isEmpty(id)) {
		resultStr = ticketSalesRulesService.saveItem(rule);
	}else{
		rule.id=new BigInteger(id.toString());
		resultStr = ticketSalesRulesService.updateItem(rule);
	}
	println 'JC.request.param=== ' + resultStr
	if (!StringUtil.isEmpty(resultStr)) {
		AvailabilityStatus.notAvailable(resultStr)
	}
	return AvailabilityStatus.available();
}catch(Exception e){
	e.printStackTrace();
	println 'e.msg=' + e.message;
	return AvailabilityStatus.notAvailable("保存网售票类规则失败");
}