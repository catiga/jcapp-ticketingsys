package com.jeancoder.ticketingsys.ready.prefer

import com.jeancoder.app.sdk.JC
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.incall.MCCompute
import com.jeancoder.ticketingsys.ready.prep.McForCashier
import com.jeancoder.ticketingsys.ready.prep.McInfo
import com.jeancoder.ticketingsys.ready.prep.PrepData
import com.milepai.core.utils.web.JackSonBeanMapper

/**
 * 活动价格计算器
 * @author jackielee
 *
 */
class MarketPrefer extends Prefer {

	def oc;
	
	SimpleAjax compute(def order_param, Object market_id, def op, Object de_list) {
		def pid = order_param['pid']
		def sid = order_param['sid']
		def oid = order_param['id']
		def on = order_param['on']
		def oc = order_param['oc']
		def hall_id = order_param['hall_id']
		def film_no = order_param['film_no']
		def mobile = order_param['mobile'];
		def ap_id = order_param['ap_id'];
		def film_dimen = order_param['film_dimen'];
		
		def param = [market_id:market_id,g:de_list,film_no:film_no,film_dimen:film_dimen,hall_id:hall_id.toString()];
		MCCompute ret_data = null;
		try {
			def p = JackSonBeanMapper.mapToJson(param);
			def request_param = [p:p,pid:pid,sid:sid,on:on,oc:oc, mobile:mobile, ap_id:ap_id];
			if(op) {
				request_param.put('op', op);
			}
			def ret_json = JC.internal.call('market', '/market/compute_movie_price', request_param)

			ret_data = JackSonBeanMapper.fromJson(ret_json, MCCompute);
		}catch(any) {
			any.printStackTrace();
		}

		if(ret_data==null) {
			return SimpleAjax.notAvailable('comm_error,服务通讯失败');
		}
		if(ret_data.code=='0') {
			//成功
			McInfo mc_info = new McInfo();
			McForCashier mfc_obj = new McForCashier(prep_amount:ret_data.offerAmount);
			mfc_obj.items = ret_data.items;

			PrepData for_trade_prep = new PrepData(order_id:oid,oc:oc,prefcode:'200',other:mfc_obj);
			for_trade_prep.pref_amount = ret_data.offerAmount;		//券最大可优惠金额
			for_trade_prep.pay_amount = ret_data.totalAmount;
			return SimpleAjax.available('', for_trade_prep);
		} else {
			//return SimpleAjax.notAvailable('compute_fail,优惠价计算失败');
			return SimpleAjax.notAvailable('compute_fail,' + ret_data.msg);
		}
	}

}
