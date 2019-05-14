package com.jeancoder.ticketingsys.entry.ticketSalesRules

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.entity.TicketSalesRules
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.ticketSalesRules.ticketSalesRulesService


Result result = new Result();
JCRequest request =RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	Long pid =GlobalHolder.getProj().getId();; 
	String pn_s = request.getParameter("pn");
	String ps_s = request.getParameter("ps");
	String mstatus=request.getParameter("status");

	//分页查询处理
	if(StringUtil.isEmpty(pn_s)){
		pn_s = "1";
	}
	if(StringUtil.isEmpty(ps_s)){
		ps_s="30";
	}

	Integer pn = Integer.parseInt(pn_s);
	if(pn<1){
		pn=1;
	}

	Integer ps = Integer.parseInt(ps_s);

	JcPage<TicketSalesRules> page = new JcPage<TicketSalesRules>();
	page.setPn(pn);
	page.setPs(ps);

	//返回结果
	page = ticketSalesRulesService.getList(pid, page,mstatus);
	result.addObject("page", page);
	result.addObject("apstatus", mstatus)
	result.setView("ticketSalesRules/list")
	return result;
}catch(Exception e){
	Logger.error("查询网售规则列表失败",e);
	return result.setData("error_msg","查询网售规则列表失败");
}


