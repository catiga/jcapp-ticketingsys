package com.jeancoder.ticketingsys.internal.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

/*
JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	SysProjectInfo project = (SysProjectInfo)req.getAttribute("current_project")
	
	if(project == null) {
		result.setData(Res.Success());
		return result;
	}
	
	List<StoreInfo> stores = StoreService.INSTANCE.getStoresByProjId(project.getId().longValue())
	
	result.setData(Res.Success(stores));
	return result;
}catch(Exception e) {
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}
*/


def s = System.currentTimeMillis();
Result result = new Result();

def token_key = JC.internal.param('token');
def domain = JC.internal.param('domain');

//SimpleAjax ret_result = RemoteUtil.connect(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token_key]);
SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'trade', '/incall/cashier/token_validate', [token:token_key,domain:domain]);
println "JackSonBeanMapper____" + JackSonBeanMapper.toJson(ret_result) + "————————";
if(ret_result==null) {
	return SimpleAjax.notAvailable('trade_server_error,请检查交易服务');
}
if(!ret_result.available) {
	return ret_result;
}
if(ret_result.data[0]==null || ret_result.data[0]['sid'] == null) {
	return SimpleAjax.notAvailable('counter_set_error,请绑定收银台的门店信息');
}
def sid = ret_result.data[0]['sid'];

List<Cinema> stores = JcTemplate.INSTANCE().find(Cinema, 'select * from Cinema where store_basic=? and flag!=?', sid, -1);
result.setData(Res.Success(stores));
def e = System.currentTimeMillis();
println "store_store"+(e-s)/1000;
return Res.Success(stores);

