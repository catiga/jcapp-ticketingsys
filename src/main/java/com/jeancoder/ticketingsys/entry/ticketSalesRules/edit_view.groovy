package com.jeancoder.ticketingsys.entry.ticketSalesRules

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.entity.TicketSalesRules
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.ticketSalesRules.ticketSalesRulesService
import com.jeancoder.ticketingsys.ready.util.DateUtil

import java.text.SimpleDateFormat

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

try{
	String id = request.getParameter("id");
	String mstatus = request.getParameter("mstatus");
	BigInteger pid=GlobalHolder.getProj().getId();
	TicketSalesRules rule=ticketSalesRulesService.getItem(pid, new BigInteger(id));
	if (rule!=null) {
		if (!(StringUtil.isEmpty(rule.time_type)||rule.time_type.equals('0'))) {
			String time_streg=DateUtil.compareR_to_W(rule.time_type, rule.time_streg);
			rule.time_streg=time_streg;
		}
		result.addObject("rule", rule);
		//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		String format1 = format.format(rule.time_streg);
		//		result.addObject("start_time", format1);
		//		result.addObject("end_time", format2);
		//		result.addObject("detail", detail);
	}else{
		TicketSalesRules info=new TicketSalesRules();
		result.addObject("rule", info);
	}
	result.addObject("mstatus", mstatus);
	result.setView("ticketSalesRules/edit/edit" );
	return result;
}catch(Exception e){
	Logger.error("查询列表失败",e);
	return result.setData(AvailabilityStatus.notAvailable("查询列表失败"));
}