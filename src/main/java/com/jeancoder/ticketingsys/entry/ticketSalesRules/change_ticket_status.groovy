package com.jeancoder.ticketingsys.entry.ticketSalesRules

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.entity.TicketSalesRules
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.ticketSalesRules.ticketSalesRulesService

Result result=new Result();
JCRequest request =RequestSource.getRequest();
JCLogger logger=LoggerSource.getLogger(this.getClass().getName())
try {
	String rid=request.getParameter("id");
	String mstatus=request.getParameter("mstatus");
	def pid = GlobalHolder.getProj().getId();
	if (StringUtil.isEmpty(rid)) {
		return result.setData(AvailabilityStatus.notAvailable("参数不能为空"));
	}
	def id=new BigInteger(rid.toString())
	String resultStr = ticketSalesRulesService.updateStatus(pid, id, mstatus);
	if (!StringUtil.isEmpty(resultStr)) {
		AvailabilityStatus.notAvailable(resultStr)
	}
	return AvailabilityStatus.available();
} catch (Exception e) {
	logger.error("更改状态失败", e);
	return AvailabilityStatus.notAvailable("更改状态失败");
}