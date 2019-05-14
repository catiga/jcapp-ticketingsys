package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.dto.GroupWithSchema
import com.jeancoder.ticketingsys.ready.schema.dto.SchemaWithItem

JCLogger Logger = JCLoggerFactory.getLogger(this.getClass().getName())
Result result = new Result();
try {
	def groupId = StringUtil.trim((String)CommunicationSource.getParameter("groupId"));
	
	GroupWithSchema groupschema = new GroupWithSchema();
    groupschema = SchemaService.INSTANCE.getGroupname(groupId);
	
	if(groupschema.equals("")||groupschema==null){
		return result.setData(AvailabilityStatus.available("无可选票类"));
	}else{
		if(groupschema.group_name.equals("收银台")){
			return result.setData(AvailabilityStatus.available("收银台的票类"));
		}
	}
	List<SchemaWithItem> schemas = new ArrayList<SchemaWithItem>();
	
	schemas = SchemaService.INSTANCE.getGroupSchema_ticket(groupId);
	return result.setData(AvailabilityStatus.available(schemas));
} catch (e) {
	return result.setData(AvailabilityStatus.notAvailable("预约选座查询失败"));
	Logger.error("查询失败：",e);
}