package com.jeancoder.ticketingsys.internal.api

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.dto.TradeInfo
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat
import com.jeancoder.ticketingsys.ready.support.Res

def ap_id = JC.internal.param('ap_id');
def pid = JC.internal.param('pid');
def plan_id = JC.internal.param("plan_id");
def cinema_id = Long.valueOf(JC.internal.param("cinema_id"));

SaleOrder recent_order = null;
if(ap_id) {
	recent_order = JcTemplate.INSTANCE().get(SaleOrder, 'select * from SaleOrder where flag!=? and user_id=? and order_status=? and store_id=? and plan_id=? order by a_time desc', -1, ap_id, '0000', cinema_id, plan_id);
	if(recent_order) {
		//判断时间
		def time_diff = System.currentTimeMillis() - recent_order.a_time.getTime();
		if(time_diff>=15*60*1000l) {
			recent_order = null;
		}
	}
}
def trade = null;
if(recent_order) {
	SimpleAjax trade_ajax = JC.internal.call(SimpleAjax, 'trade', '/query/trade_by_order', [pid:pid,order_id:recent_order.id, order_no:recent_order.order_no,order_oc:recent_order.o_c]);
	if(trade_ajax && trade_ajax.available) {
		trade = trade_ajax.data;
	}
}

def order_seats = null;
if(recent_order) {
	//查找座位信息并返回
	order_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where flag!=? and order_id=?', -1, recent_order.id);
}

return Res.Success([trade, recent_order, order_seats]);

