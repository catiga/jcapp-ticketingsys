package com.jeancoder.ticketingsys.entry.category
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.category.CategoryService
import com.jeancoder.ticketingsys.ready.category.entity.DataTcSsTicCategory
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	Long id = req.getLong("id");
	String category_code = req.getParameter("category_code");
	String category_name = req.getParameter("category_name");
	
	if(category_code == null || "".equals(category_code) || category_name == null || "".equals(category_name)) {
		result.setData(Res.Failed(Codes.COMMON_PARAM_ERROR));
		return result;
	}
	
	if(CategoryService.INSTANCE.checkCodeExists(category_code)) {
		result.setData(Res.Failed(Codes.COMMON_RECORD_REPEAT,"该分类已存在"));
		return result;
	}
	
	DataTcSsTicCategory category = new DataTcSsTicCategory();
	category.setId(id);
	category.setCategory_code(category_code);
	category.setCategory_name(category_name);
	
	CategoryService.INSTANCE.addOrUpdate(category);
	
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}