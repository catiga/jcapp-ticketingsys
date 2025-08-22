package com.jeancoder.ticketingsys.entry.store
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.CinemaHall
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.schema.SchemaService
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaHallResult
import com.piaodaren.ssc.model.SscAuthModel

JCLogger logger = JCLoggerFactory.getLogger('')
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

CinemaHallResult halls = ssc_op.get_cinema_hall_list(cinemaAuthInfo.getCinemaCode());
//logger.info("store_id:{} - {}", store_id, JackSonBeanMapper.toJson(halls))
def schemas = SchemaService.INSTANCE.getAllSchemaGroup(GlobalHolder.proj.id);

if (halls.isSuccess() && halls.getResult() != null && !halls.getResult().isEmpty()) {
    JcTemplate.INSTANCE().batchExecute('delete from CinemaHall where store_id = ?', store_id)
    for (x in halls.getResult()) {
        CinemaHall ch = new CinemaHall(store_id: store_id, hall_id: x.id, hall_name: x.name, seat_num: x.seatNum,
                hall_type: x.type, audio_type: x.audioType, screen_code: x.screenCode, cine_hall_id: x.cineHallId)
        JcTemplate.INSTANCE().save(ch)
    }
}
result.addObject("halls", halls.getResult());
result.addObject("schemas", schemas);
result.addObject("store_id", store_id);

result.setView("store/hall_schema");
return result;