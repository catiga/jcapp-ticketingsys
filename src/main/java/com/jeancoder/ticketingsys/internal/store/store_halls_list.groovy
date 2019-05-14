package com.jeancoder.ticketingsys.internal.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.common.AvailabilityStatus
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.dto.sys.CinemaHallDto
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.SscAuthModel

import groovy.xml.SAXBuilder
/*
 * 获取该项目所有影城对应的影厅
 * @parm pid
 * @return halls
 */
JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
Result result = new Result();
try {
	def pid = JC.internal.param("pid");
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
			def hall = ssc_op.get_cinema_hall_list(cinemaAuthInfo.getCinemaCode());
			dto.cinema = hall.result;
			dto.id = String.valueOf(store.id);
			dto.store_basic = store.store_basic.toString();
			dto.store_name = store.store_name;
			halls.add(dto);
		}
	}
	return SimpleAjax.available("",halls)
} catch (Exception e) {
	Logger.error("查询影厅列表失败", e);
	result.setData(AvailabilityStatus.notAvailable("查询影厅列表失败"));
}
