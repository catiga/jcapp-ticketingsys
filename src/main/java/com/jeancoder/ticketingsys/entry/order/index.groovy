package com.jeancoder.ticketingsys.entry.order

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

JCLogger logger = LoggerSource.getLogger();

def pn = JC.request.param('pn');
def ps = JC.request.param('ps');

def mck = JC.request.param('mck');

def pid = GlobalHolder.proj.id;

try {
	pn = Integer.valueOf(pn);
} catch(any) {
	pn = 1;
}

try {
	ps = Integer.valueOf(ps);
} catch(any) {
	ps = 20;
}
def user_id = null;
if(mck) {
	//获取对应的用户信息
	SimpleAjax result = JC.internal.call(SimpleAjax, 'crm', '/mc/query', [pid:pid,mck:mck]);
	if(result && result.available && result.data) {
		logger.info(JackSonBeanMapper.toJson(result.data));
		user_id = result.data[0]['basic_id'];
	}
}
def sql = 'select * from SaleOrder where flag!=? and proj_id=?';
def params = []; params.add(-1); params.add(pid);
if(user_id) {
	sql += ' and user_id=?';
	params.add(user_id);
}
sql += ' order by a_time desc';
JcPage<SaleOrder> page = new JcPage<SaleOrder>();
page.pn = pn;
page.ps = ps;

page = JcTemplate.INSTANCE().find(SaleOrder, page, sql, params.toArray());
Result view = new Result().setView('order/index');
view.addObject('page', page);
view.addObject('oss', OrderConstants.all());
if(mck) {
	view.addObject('mck', mck);
}
return view;



