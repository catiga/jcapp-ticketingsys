package com.jeancoder.ticketingsys.interceptor.general

import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.JC
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.trade.DataTrans

/**
 * 向交易中心注册
 */

def start = System.currentTimeMillis();

def app_code = 'ticketingsys';
def order_type = '2000,2010';
def call_back = '/incall/order/notify';
def dataformat = null;

try {
	//DataTrans ret = RemoteUtil.connect(DataTrans, 'trade', '/incall/reg/trigger', [app_code:app_code,order_type:order_type,callback:call_back]);
	DataTrans ret = JC.internal.call(DataTrans, 'trade', '/incall/reg/trigger', [app_code:app_code,order_type:order_type,callback:call_back]);
	
	if(ret==null) {
		println 'error, trade reg fail';
	}

	println ret;
	GlobalHolder.setDt(ret);
}catch (e) {
	e.printStackTrace();
}


def end = System.currentTimeMillis();
println 'interceptor:general/pre execute time=' + ((end - start)/1000) + '秒';

return true;
