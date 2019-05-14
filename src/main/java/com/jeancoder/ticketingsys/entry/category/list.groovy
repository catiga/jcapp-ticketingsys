package com.jeancoder.ticketingsys.entry.category

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.category.CategoryService

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

def all_category = CategoryService.INSTANCE.getAllCategoryList();

result.addObject("all_category", all_category);
result.setView("category/list");
return result;