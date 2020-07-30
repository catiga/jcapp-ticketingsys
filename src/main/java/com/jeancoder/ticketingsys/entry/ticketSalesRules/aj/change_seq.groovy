package com.jeancoder.ticketingsys.entry.ticketSalesRules.aj

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.TicketSalesRules
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder

def id = JC.request.param('id');
def seq = JC.request.param('seq');

try {
	seq = Integer.valueOf(seq);
	if(seq<0) {
		return SimpleAjax.notAvailable('seq_error,排序参数为非负整数');
	}
} catch(any) {
	return SimpleAjax.notAvailable('seq_error,排序参数为非负整数');
}

String sql = "select * from TicketSalesRules where p_id =? and id=? and flag!=?";

TicketSalesRules tsr = JcTemplate.INSTANCE().get(TicketSalesRules, sql, GlobalHolder.proj.id, id, -1);
if(tsr==null) {
	return SimpleAjax.notAvailable('obj_not_found,价格策略未找到');
}

tsr.seq = seq;
JcTemplate.INSTANCE().update(tsr);

return SimpleAjax.available();
