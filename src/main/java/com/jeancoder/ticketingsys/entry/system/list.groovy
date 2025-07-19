package com.jeancoder.ticketingsys.entry.system

import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.CinemaConfig
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.support.TicketingSysType
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.jeancoder.ticketingsys.ready.system.dto.SystemCodeInfo

Result result = new Result();
JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName());
List<TicketingSysType> supportSystems =  TicketingSysTypeHelper.getSupportSystem();
BigInteger pid = GlobalHolder.proj.id;

try{
//	def systems = null;
//	if (pid == 1) {
//		 systems = TicketingSystemService.INSTANCE.getAll();
//	}else{
//		 systems = TicketingSystemService.INSTANCE.getAll(pid);
//	}
	
	def sql = 'select * from CinemaConfig where flag!=?'; def params = []; params.add(-1);
	if(GlobalHolder.proj.root!=1) {
		sql += ' and pid=?';
		params.add(pid);
	}
	def systems = JcTemplate.INSTANCE().find(CinemaConfig, sql, params.toArray());
	if(systems == null || systems.size() < 0){
		systems = new ArrayList<SystemCodeInfo>();
	}
	result.addObject("supportSystems", supportSystems);
	result.addObject("systems", systems);
	result.setView("system/list");
}catch(Exception e){
	Logger.error("查询票务系统失败");
}
return result;