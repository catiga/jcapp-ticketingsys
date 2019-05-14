package com.jeancoder.ticketingsys.entry.common

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.dto.sys.CinemaHallDto
import com.jeancoder.ticketingsys.ready.dto.sys.CinemaHallListDto
import com.jeancoder.ticketingsys.ready.dto.sys.CinemaHallResultListDto
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaHall
import com.piaodaren.ssc.model.SscAuthModel

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
JCRequest request = RequestSource.getRequest();
try {
	def pid = GlobalHolder.getProj().getId();
	List<CommunicationParam> params = new ArrayList<CommunicationParam>();
	def list = StoreService.INSTANCE.find_stores(pid);
	if (list == null||list.size()<=0) {
		return AvailabilityStatus.notAvailable("影城列表为空，查询不到影厅信息");
	}
	List<CinemaHallDto> halls= [];
	for(StoreInfo store:list){
		def id = store.id;
		CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
		if (cinemaAuthInfo !=null) {
			CinemaHallDto dto =new CinemaHallDto();
			String tc_ss_type = cinemaAuthInfo.getSystemSsCode();
			String channel_url = cinemaAuthInfo.getAuthChannelUrl();
			String channel_num = cinemaAuthInfo.getAuthChannelNo();
			String channel_code = cinemaAuthInfo.getAuthChannelCode();
			SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
			SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
			if(ssc_op!=null) {
				def hall = ssc_op.get_cinema_hall_list(cinemaAuthInfo.getCinemaCode());
				dto.cinema=hall.result;
				dto.id=String.valueOf(store.id);
				dto.store_name=store.store_name;
				halls.add(dto);
			}
		}
	}
	List<CinemaHallListDto> resultHall=new ArrayList<CinemaHallListDto>();
	if (halls!=null&&halls.size()>0) {
		for (CinemaHallDto dto:halls) {
			CinemaHallListDto resultDto7 = new CinemaHallListDto();
			resultDto7.id = dto.id;
			resultDto7.CinemaStoreName=dto.store_name;//门店名称
			List<CinemaHallResultListDto> resultDto1 =new ArrayList<CinemaHallResultListDto>();
			for(CinemaHall cinema:dto.cinema){
				CinemaHallResultListDto resultDto = new CinemaHallResultListDto();
				resultDto.id= dto.id+'-'+cinema.id;
				resultDto.CinemaHallName=cinema.name;//影厅
				resultDto1.add(resultDto);
			}
			resultDto7.hall = resultDto1;
			resultHall.add(resultDto7);
		}
	}
	result.setData(AvailabilityStatus.available(resultHall));
} catch (Exception e) {
	Logger.error("查询影厅列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询影厅列表失败"));
}
return result;