package com.jeancoder.ticketingsys.entry.ssclog

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.piaodaren.ssc.factory.SscHistory

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

//result.addObject("supportSystems", supportSystems);
result.addObject("logs", SscHistory.getHistory());
result.setView("ssclog/list");
return result;