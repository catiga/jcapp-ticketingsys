package com.jeancoder.ticketingsys.entry.ticketSeatsDetails

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.core.util.StringUtil
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.seatdetail.SeatDetailService
import com.jeancoder.ticketingsys.ready.seatdetail.dto.PlanSeatDto
import com.jeancoder.ticketingsys.ready.seatdetail.dto.SeatRowDetailDto
import com.jeancoder.ticketingsys.ready.seatdetail.entity.HallInformation
import com.jeancoder.ticketingsys.ready.seatdetail.entity.SeatingDetails
import com.jeancoder.ticketingsys.ready.seatdetail.seatdto.HallInformationDto
import com.jeancoder.ticketingsys.ready.seatdetail.seatdto.PlanSeatsDto
import com.jeancoder.ticketingsys.ready.seatdetail.seatdto.SeatDetailDto
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper

Result result = new Result();
JCRequest request = RequestSource.getRequest();
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
PlanSeatsDto hpsd = new PlanSeatsDto();
try {
	Long id = request.getLong("cinema_id");//门店id
	String plan_id = request.getParameter("plan_id");//排期id
	String last_update_time = request.getParameter("last_update_time");
	String hall_id = request.getParameter("hall_id");//影厅id
	if(StringUtil.isEmpty(String.valueOf(id))||StringUtil.isEmpty(plan_id)||StringUtil.isEmpty(last_update_time)||StringUtil.isEmpty(hall_id)){
		return result.setData(AvailabilityStatus.notAvailable("查询影厅的参数不能为空"));
	}
	//通过查询得到排期的影厅座位图
	SimpleAjax ret_result = JC.internal.call(SimpleAjax, 'ticketingsys', '/plan/seats', [cinema_id:id, plan_id:plan_id, last_update_time:last_update_time, hall_id:hall_id]);
	println ret_result.data;
	if(ret_result.data.equals(null)||ret_result.data.equals("")){
		return result.setData(AvailabilityStatus.notAvailable("查询排期的座位影厅图失败"));
	}
	hpsd.big_row = ret_result.data.big_row;
	hpsd.big_col = ret_result.data.big_col;
	hpsd.all_row = ret_result.data.all_row;
	println JackSonBeanMapper.toJson(ret_result.data.rows);
	def list = JackSonBeanMapper.jsonToList(JackSonBeanMapper.toJson(ret_result.data.rows),SeatRowDetailDto);

	List<PlanSeatDto> psds = new ArrayList();
	for(SeatRowDetailDto l:list){
		for(PlanSeatDto p:l.seats){
			PlanSeatDto psd = new PlanSeatDto();
			psd.graphRow = p.graphRow;
			psd.graphCol = p.graphCol;
			psd.seatNo = p.seatNo;
			psd.type = p.type;
			psds.add(psd);
		}
	}
	//先用hall_id在我本地影厅信息
	List<HallInformation> his = SeatDetailService.INSTANCE.get_hall_by_hall_id(hall_id,id);
	SeatingDetails sd = new SeatingDetails();
	if(his.size()==0){ //如果为空直接插入本地数据库
		HallInformation hif = new HallInformation();
		hif.hall_id = new BigInteger(hall_id);
		hif.cinema_id = new BigInteger(String.valueOf(id));
		SeatDetailService.INSTANCE.add_hall(hif);
		HallInformation c_inf = SeatDetailService.INSTANCE.get_hall_information();
		for(PlanSeatDto p:psds){
			sd.dhi_id = c_inf.id;
			sd.seat_gr = p.graphRow;
			sd.seat_gc = p.graphCol;
			sd.seat_no = p.seatNo;
			sd.a_time = new Date();
			sd.seat_status = p.type;
			SeatDetailService.INSTANCE.add_seat_detail(sd);
		}
	}else{ //如果有数据
		List<SeatingDetails> sds = SeatDetailService.INSTANCE.get_seat_detail(his.get(0).id);
		for(PlanSeatDto p:psds){
			def flg = 0;
			for(SeatingDetails sdt:sds){
				if(p.seatNo.equals(sdt.seat_no)){
					flg = 1;
					break;
				}
			}
			if(flg==0){
				SeatingDetails sddl = new SeatingDetails();
				sddl.dhi_id = new BigInteger(String.valueOf(his.get(0).id));
				sddl.seat_gr = p.graphRow;
				sddl.seat_gc = p.graphCol;
				sddl.seat_no = p.seatNo;
				sddl.seat_status = p.type;
				sddl.a_time = new Date();
				SeatDetailService.INSTANCE.add_seat_detail(sddl);}
			else{continue;}
		}
		for(SeatingDetails sdt:sds){
			def flg = 0;
			for(PlanSeatDto p:psds){
				if(sdt.seat_no.equals(p.seatNo)){
					flg = 1;
					break;
				}
			}
			if(flg==0){
				SeatDetailService.INSTANCE.update_seat_detail(his.get(0).id,sdt.seat_no);
			}else{continue;}
		}
	}
	
	//得到排期的座位图（根据影厅得到本地座位图）
	List<HallInformationDto> hids = new ArrayList();
	//先用hall_id在我本地影厅信息
	List<HallInformation> hiss = SeatDetailService.INSTANCE.get_hall_by_hall_id(hall_id,id);
	for(int i=1;i<=hpsd.big_row;i++){
		List<SeatDetailDto> sds1 = SeatDetailService.INSTANCE.get_seat_detailbyseat(hiss.get(0).id,i);
		HallInformationDto hid = new HallInformationDto();
		hid.row = i;
		hid.seats = sds1;
		hids.add(hid);
	}
	hpsd.rows = hids;
	return result.setData(AvailabilityStatus.available(hpsd));
}catch(Exception e){
	Logger.error("获取座位信息失败",e);
	return result.setData("error_msg","查询座位信息失败");
}
