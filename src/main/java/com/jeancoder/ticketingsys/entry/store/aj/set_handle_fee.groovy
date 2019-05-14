package com.jeancoder.ticketingsys.entry.store.aj

import java.sql.Timestamp

import com.jeancoder.app.sdk.JC
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.entity.TicketHandleFee
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder

def store_id = JC.request.param('store_id');
def hall_num = JC.request.param('hall_num');
def fee = JC.request.param('fee');

try {
	fee = new BigDecimal(fee).multiply(new BigDecimal(100));
	if(fee<0) {
		return SimpleAjax.notAvailable('param_error,服务费参数错误');
	}
} catch(any) {
	return SimpleAjax.notAvailable('param_error,服务费参数错误');
}
//获取影城信息
Cinema cinema = JcTemplate.INSTANCE().get(Cinema, 'select * from Cinema where flag!=? and id=?', -1, store_id);
if(!cinema) {
	return SimpleAjax.notAvailable('store_not_found,影城未找到');
}
//删除所有对应手续费
def sql = 'update TicketHandleFee set flag=-1 where pid=? and flag!=? and store_id=?';
def params = []; params.add(GlobalHolder.proj.id); params.add(-1); params.add(store_id);
if(hall_num) {
	sql += ' and hall_num=?';
	params.add(hall_num);
}
JcTemplate.INSTANCE().batchExecute(sql, params.toArray());

TicketHandleFee thfee = new TicketHandleFee();
thfee.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
thfee.c_time = thfee.a_time;
thfee.fee = new BigDecimal(fee);
thfee.flag = 0;
thfee.hall_num = hall_num;
thfee.pid = GlobalHolder.proj.id;
thfee.store_basic = cinema.store_basic;
thfee.store_id = cinema.id;
JcTemplate.INSTANCE().save(thfee);

return SimpleAjax.available();
