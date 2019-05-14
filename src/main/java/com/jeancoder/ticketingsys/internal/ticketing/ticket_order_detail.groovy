package com.jeancoder.ticketingsys.internal.ticketing

import java.net.URLDecoder
import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.util.DateUtil

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

return SimpleAjax.available('', [order, seats]);
