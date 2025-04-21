package com.jeancoder.ticketingsys.entry.schema
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.dto.GroupInfo
import com.jeancoder.ticketingsys.ready.schema.entity.TicketPriceSchemaGroup
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.ResCode

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	Long id = req.getLong("id");
	String group_name = req.getParameter("group_name");
	String group_type = req.getParameter("group_type");
	
	TicketPriceSchemaGroup group = new TicketPriceSchemaGroup();
	group.setId(id);
	group.setGroup_name(group_name)
	group.setGroup_type(group_type)
	group.setPid(GlobalHolder.proj.id);
	
	if (id == null && !group_name.equals("")&&!group_name.equals(null)) {
		List<GroupInfo> list = SchemaService.INSTANCE.getGroupByNameAndType(group_name,group_type,GlobalHolder.proj.id);//查询是否有该分组
		if (list!=null && !list.isEmpty()) {
			return result.setData(Res.Failed(new ResCode(999999,"该分组名不能重复添加")));
		}
	}
	/*if (id != null && group_name.equals(TicketingsysConstant.default_schema_group)) {
		return result.setData(Res.Failed(new ResCode(999999,"该分组名已经禁用")));
	}
	if (id == null && group_name.equals(TicketingsysConstant.default_schema_group)) {
		List<GroupInfo> list = SchemaService.INSTANCE.getGroupByName(TicketingsysConstant.default_schema_group);
		if (list!=null && !list.isEmpty()) {
			return result.setData(Res.Failed(new ResCode(999999,"该分组名不能重复添加")));
		}
	}*/
	
//	SchemaService.INSTANCE.addOrUpdateGroup(group);
	JcTemplate.INSTANCE().save(group);
	
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}