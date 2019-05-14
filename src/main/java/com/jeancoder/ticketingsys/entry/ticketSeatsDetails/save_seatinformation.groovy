package com.jeancoder.ticketingsys.entry.ticketSeatsDetails

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.seatdetail.SeatDetailService
import com.jeancoder.ticketingsys.ready.seatdetail.entity.HallInformation

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());

try {
	String ticket_types = request.getParameter("ticket_types");
	String id = request.getParameter("cinema_id");
	String hall_id = request.getParameter("hall_id");
	String seats_typesls = request.getParameter("seats_typesls");
	if(StringUtil.isEmpty(seats_typesls)){
		return result.setData(AvailabilityStatus.notAvailable("座位信息不能为空"));
	}
	if(!StringUtil.isEmpty(ticket_types)){
		SeatDetailService.INSTANCE.update_ticket_type(id,hall_id,ticket_types.substring(0,ticket_types.length() - 1));
	}
	List<HallInformation> his = SeatDetailService.INSTANCE.get_hall_by_hall_id(hall_id, id);
	SeatDetailService.INSTANCE.update_seat_type(his.get(0).id, seats_typesls);
	return result.setData(AvailabilityStatus.available("更改作为信息成功"));
}catch(Exception e){
	Logger.error("保存座位信息失败",e);
	return result.setData("error_msg","保存座位信息失败");
}