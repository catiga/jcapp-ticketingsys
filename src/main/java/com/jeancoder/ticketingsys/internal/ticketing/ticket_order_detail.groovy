package com.jeancoder.ticketingsys.internal.ticketing

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat
import com.jeancoder.ticketingsys.ready.entity.TicketSchema

// order_id, order_seat_id 这样入参
JCLogger Logger = LoggerSource.getLogger();
def order_id = JC.internal.param('order_id')?.toString();
if(!order_id) {
	return SimpleAjax.notAvailable('param_error,请检查参数');
}

SaleOrder order = JcTemplate.INSTANCE().get(SaleOrder, 'select * from SaleOrder where id=?', order_id);
if(order==null) {
	return SimpleAjax.notAvailable('obj_not_found,票务订单未找到');
}

//查找座位信息
List<SaleSeat> seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', order_id);
if(seats==null || seats.empty) {
	return SimpleAjax.notAvailable('obj_not_found,座位信息为空');
}

for(x in seats) {
	BigInteger tclass_id = x.tclass_id;
	if(tclass_id) {
		TicketSchema schema = JcTemplate.INSTANCE().get(TicketSchema, 'select * from TicketSchema where id=?', tclass_id);
		if(schema!=null) {
			x.tclass_name = schema.schema_name;
		}
	}
}

return SimpleAjax.available('', [order, seats]);
