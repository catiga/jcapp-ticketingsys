package com.jeancoder.ticketingsys.entry.store
import java.util.List

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.entity.TicketHandleFee
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.schema.dto.GroupWithSchema
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaHall
import com.piaodaren.ssc.model.CinemaHallResult
import com.piaodaren.ssc.model.SscAuthModel

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

Long store_id = req.getLong("store_id");

CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(store_id);

String tc_ss_type = cinemaAuthInfo.getSystemSsCode();
String channel_url = cinemaAuthInfo.getAuthChannelUrl();
String channel_num = cinemaAuthInfo.getAuthChannelNo();
String channel_code = cinemaAuthInfo.getAuthChannelCode();
SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);

SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);

CinemaHallResult hall_result = ssc_op.get_cinema_hall_list(cinemaAuthInfo.getCinemaCode());
List<CinemaHall> halls = null;
if(hall_result) {
	halls = hall_result.getResult();
}
//获取影城信息
Cinema cinema = JcTemplate.INSTANCE().get(Cinema, 'select * from Cinema where flag!=? and id=?', -1, store_id);

List<TicketHandleFee> fees = JcTemplate.INSTANCE().find(TicketHandleFee, 'select * from TicketHandleFee where flag!=? and store_id=?', -1, store_id);

def store_default_fee = null;

if(fees) {
	for(x in fees) {
		if(x.hall_num=='-1') {
			//影城／门店层的默认手续费
			store_default_fee = x.fee;
			break;
		}
	}
}

def hall_with_fee = [];
if(halls) {
	for(x in halls) {
		def hall_fee = null;
		if(fees) {
			for(y in fees) {
				if(x.id==y.hall_num) {
					hall_fee = y; break;
				}
			}
		}
		hall_with_fee.add([x, hall_fee]);
	}
}

result.addObject("hall_with_fee", hall_with_fee);
result.addObject("store_id", store_id);
result.addObject('store', cinema);

result.addObject('fees', fees);
result.addObject('store_default_fee', store_default_fee);
result.setView("store/handle_fee");
return result;
