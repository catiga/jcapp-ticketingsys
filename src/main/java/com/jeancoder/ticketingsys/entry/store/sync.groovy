package com.jeancoder.ticketingsys.entry.store
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.jeancoder.ticketingsys.ready.system.TicketingSystemService
import com.jeancoder.ticketingsys.ready.system.dto.SystemCodeInfo
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.Cinema
import com.piaodaren.ssc.model.CinemaResult
import com.piaodaren.ssc.model.SscAuthModel

JCRequest req = RequestSource.getRequest();
Result result = new Result();

JCLogger logger = LoggerSource.getLogger();

try {
	String cinema_code = req.getParameter("cinema_code");
	String config_id = req.getParameter("config_id");
	
	Long systemId = null;
	if(config_id != null && !"".equals(config_id)) {
		systemId = Long.valueOf(config_id);
	}
	
	SystemCodeInfo sysinfo = TicketingSystemService.INSTANCE.getById(systemId);
	
	String tc_ss_type = sysinfo.getSs_code();
	String channel_url = sysinfo.getAuth_chan_url();
	String channel_num = sysinfo.getAuth_chan_num();
	String channel_code = sysinfo.getAuth_chan_code();
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
	SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
	logger.info('ssc_op======' + ssc_op.toString() + ', and auth_model=' + auth_model.toString() + ' , and channel_num=' + auth_model.getAuthNum());
	logger.info('config_id=' + config_id + ', tcsstype=' + tc_ss_type + ', ch_url=' + channel_url + ', ch_num=' + channel_num + ', and cn_code=' + channel_code);
	CinemaResult cinemas = ssc_op.get_cinema_list();
	logger.info(JackSonBeanMapper.toJson(cinemas));
	if(!"0".equals(cinemas.getCode())) {
		logger.info("sync is failure" + JackSonBeanMapper.toJson(cinemas));
		result.setData(Res.Failed(Codes.CINEMA_CONN_FAILED));
		return result;
	}
	
	if(cinemas == null || cinemas.getResult() == null || cinemas.getResult().size() < 1) {
		result.setData(Res.Failed(Codes.CINEMA_EMPTY_CINEMA));
		return result;
	}
	
	Cinema foundCinema = null;
	for(Cinema cinema : cinemas.getResult()) {
		def uni_cinema_code = cinema.getCinemaId();
		if(tc_ss_type=='yueke_yun'||tc_ss_type=='huolieniao') {
			uni_cinema_code = cinema.getCinemaNumber();
		}
		if(uni_cinema_code.equals(cinema_code)) {
			foundCinema = cinema;
			break;
		}
	}
	
	if(foundCinema == null) {
		result.setData(Res.Failed(Codes.CINEMA_CHANNEL_CODE_NOT_MATCH));
		return result;
	}
	
	result.setData(Res.Success(foundCinema));
	return result;

}catch(Exception e) {
	logger.info("sync",e)
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}
