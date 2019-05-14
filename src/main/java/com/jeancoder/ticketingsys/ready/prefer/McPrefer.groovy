package com.jeancoder.ticketingsys.ready.prefer

import com.jeancoder.app.sdk.JC
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.incall.MCCompute
import com.jeancoder.ticketingsys.ready.prep.McForCashier
import com.jeancoder.ticketingsys.ready.prep.McInfo
import com.jeancoder.ticketingsys.ready.prep.PrepData
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

class McPrefer extends Prefer {

	def oc;

	SimpleAjax compute(def order_param, def card_code, def op, def g_list) {
		def pid = order_param['pid']
		def sid = order_param['sid']
		def oid = order_param['id']
		def on = order_param['on']
		def oc = order_param['oc']
		def lock_flag = order_param['lock_flag']
		
		def param = [card_code:card_code,g:g_list,lock_flag:lock_flag];
		MCCompute ret_data = null;
		try {
			def p = JackSonBeanMapper.mapToJson(param);
			def request_param = [p:p,pid:pid,sid:sid,on:on,oc:oc];
			if(op) {
				request_param.put('op', op);
			}
			def ret_json = JC.internal.call('crm', '/api/order/compute_mc_movie_price', request_param);
			ret_data = JackSonBeanMapper.fromJson(ret_json, MCCompute);
		}catch(any) {
			any.printStackTrace();
		}

		if(ret_data==null) {
			return SimpleAjax.notAvailable('comm_error,服务通讯失败');
		}
		if(ret_data.code=='0') {
			//成功
			McInfo mc_info = new McInfo(card_num:ret_data.accountMc.cn,name:ret_data.accountMc.mcname,phone:ret_data.accountMc.mcmobile,levname:ret_data.accountMc.levelname,balance:ret_data.accountMc.balance);
			McForCashier mfc_obj = new McForCashier(mc:mc_info);
			mfc_obj.items = ret_data.items;

			PrepData for_trade_prep = new PrepData(order_id:oid,oc:oc,prefcode:'100',other:mfc_obj);
			for_trade_prep.pref_amount = ret_data.offerAmount;
			for_trade_prep.pay_amount = ret_data.totalAmount;
			return SimpleAjax.available('', for_trade_prep);
		} else {
			return SimpleAjax.notAvailable('compute_fail,' + ret_data.msg);
		}
	}
}
