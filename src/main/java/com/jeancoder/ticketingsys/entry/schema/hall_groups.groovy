package com.jeancoder.ticketingsys.entry.schema

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

Long storeId = req.getLong("cinema_id");
String hallId = req.getParameter("hall_id");

def groups = SchemaService.INSTANCE.getAllSchemaGroup(storeId,hallId,GlobalHolder.proj.id);

result.setData(Res.Success(groups))
return result;