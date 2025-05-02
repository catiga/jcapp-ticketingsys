package com.jeancoder.ticketingsys.entry.store
import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.entity.HallSchema
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();
JCLogger logger = JCLoggerFactory.getLogger("save_hall_schemas");

try {
	DatabaseSource.getDatabasePower().beginTransaction();
	
	Long cinema_id = req.getLong("cinema_id");
	String hall_id = req.getParameter("hall_id");
	String schema_ids = req.getParameter("all_schemas")
	
	List<HallSchema> newSchemas = new ArrayList<HallSchema>();
	String[] schema_id_arr = schema_ids.split(",");
	for(String schemaId : schema_id_arr) {
		HallSchema newSchema = new HallSchema();
		newSchema.setStore_id(cinema_id);
		newSchema.setHall_id(hall_id);
		newSchema.setSchema_id(Long.valueOf(schemaId));
		
		newSchemas.add(newSchema);
	}
	
	StoreService.INSTANCE.saveHallSchemas(cinema_id, hall_id, newSchemas);
	
	DatabaseSource.getDatabasePower().commitTransaction();
	
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	logger.error("save_hall_schemas error", e)
	DatabaseSource.getDatabasePower().rollbackTransaction();
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}