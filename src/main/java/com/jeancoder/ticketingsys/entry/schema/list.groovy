package com.jeancoder.ticketingsys.entry.schema

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.schema.SchemaService

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

def groups = SchemaService.INSTANCE.getAllSchemaGroup(GlobalHolder.proj.id);

result.addObject("groups", groups);
result.setView("schema/list");
return result;