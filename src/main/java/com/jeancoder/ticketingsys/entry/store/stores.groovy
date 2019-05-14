package com.jeancoder.ticketingsys.entry.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

Result result = new Result();
JCRequest req = RequestSource.getRequest();
def token_key = JC.request.param('token');

if(token_key==null) {
	JCRequest request = JC.request.get();
	request.getCookies()?.each({
		if(it.name=='_c_u_k_adm_') {
			token_key = it.value;
			return;
		}
	});
	if(token_key==null) {
		return SimpleAjax.notAvailable('need_login,请先登陆');
	}
}
SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'trade', '/incall/cashier/token_validate', ['token':token_key, domain:req.getServerName()]);
println "JackSonBeanMapper____" + JackSonBeanMapper.toJson(ret_result) + "————————"+ req.getServerName();
if(ret_result==null) {
	return SimpleAjax.notAvailable(['trade_server_error','请检查交易服务']);
}
if(!ret_result.available) {
	return ret_result;
}
if(ret_result.data[0]==null) {
	return SimpleAjax.notAvailable(['counter_set_error','请绑定收银台的门店信息']);
}
def sid = ret_result.data[0]['sid'];

List<Cinema> stores = JcTemplate.INSTANCE().find(Cinema, 'select * from Cinema where store_basic=? and flag!=?', sid, -1);
result.setData(Res.Success(stores));
return result;

