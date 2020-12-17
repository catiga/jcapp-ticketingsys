package com.jeancoder.ticketingsys.entry.schema
import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.entity.TicketPriceSchema
import com.jeancoder.ticketingsys.ready.schema.entity.TicketPriceSchemaItem
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.SchemaStatus

import groovy.json.JsonSlurper

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

JCLogger logger = JCLoggerFactory.getLogger('');
try {
	DatabaseSource.getDatabasePower().beginTransaction();
	
	Long id = req.getLong("id");
	Long group_id = req.getLong("group_id");
	String schema_name = req.getParameter("schema_name");
	String time_rule = req.getParameter("time_rule");
	String week_rule = req.getParameter("week_rule");
	String price_item = req.getParameter("price_item");
	
	TicketPriceSchema schema = new TicketPriceSchema();
	schema.setId(id);
	schema.setSchema_name(schema_name);
	schema.setSchema_status(SchemaStatus.START);
	schema.setWeek_rule(week_rule);
	schema.setTime_rule(time_rule);
	schema.setBy_permission(false);
	schema.setGroup_id(group_id);
	
	SchemaService.INSTANCE.addOrUpdateSchema(schema);
	
	SchemaService.INSTANCE.deleteSchemaOldItem(schema.getId())
	def items_json_object = new JsonSlurper().parseText(price_item);
	
	int ind = 0;
	for(def item_obj : items_json_object) {
		TicketPriceSchemaItem citem = new TicketPriceSchemaItem();
		citem.setMovie_dimensional(item_obj["movie_dimensional"]);
		citem.setMovie_size(item_obj["movie_size"]);
		if("-".equals(item_obj["price"])) {
			citem.setIs_custom(true)
			citem.setPrice(0);
		}else {
			citem.setIs_custom(false)
			citem.setPrice(Double.parseDouble(MoneyUtil.multiple(item_obj["price"], "100")).longValue());
		}
		citem.setSort_num(++ind);
		citem.setSchema_id(schema.getId());
		
		SchemaService.INSTANCE.addOrUpdateItem(citem);
	}
	
	DatabaseSource.getDatabasePower().commitTransaction();
	
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	logger.error('', e);
	DatabaseSource.getDatabasePower().rollbackTransaction();
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}