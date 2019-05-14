package com.jeancoder.ticketingsys.entry.ticketSeatsDetails

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.seatdetail.SeatDetailService
import com.jeancoder.ticketingsys.ready.seatdetail.SeatTypeDato
import com.jeancoder.ticketingsys.ready.seatdetail.entity.HallInformation

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
try {
	Long id = request.getLong("cinema_id");//门店id
	String hall_id = request.getParameter("hall_id");
	if(StringUtil.isEmpty(String.valueOf(id))||StringUtil.isEmpty(hall_id)){
		return result.setData(AvailabilityStatus.notAvailable("查询票类的参数不能为空"));
	}
	//通过查询得到排期的影厅座位图
	List<SeatTypeDato> stds = SeatDetailService.INSTANCE.get_seat_type(id,hall_id)

	if(stds.size()==0){
		return result.setData(AvailabilityStatus.notAvailable("查询影厅票类失败"));
	}

	List<HallInformation> his = SeatDetailService.INSTANCE.get_hall_by_hall_id(hall_id,id);

	if(his.size()!=0&&!his.get(0).ticket_type.equals(null)&&!his.get(0).ticket_type.equals("")){
		String[] ryps = his.get(0).ticket_type.split(",");
		StringBuffer sss = new StringBuffer();
		for(r in ryps){
			def flg = 0;
			for(s in stds){
				if(String.valueOf(r).equals(String.valueOf(s.id))){
					flg = 1;
					break;
				}
			}
			if(flg==1){sss.append(r).append(",");}
		}
		if(!sss.toString().equals(null)&&!sss.toString().equals("")){
			String ss = sss.substring(0,sss.length() - 1).toString();
			SeatDetailService.INSTANCE.update_ticket_type(id,hall_id,ss);		
		}else{
			SeatDetailService.INSTANCE.update_ticket_type(id,hall_id,"");
		}
	}

	return result.setData(AvailabilityStatus.available(stds));
}catch(Exception e){
	Logger.error("获取票类信息失败",e);
	return result.setData("error_msg","查询票类信息失败");
}
