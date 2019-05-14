package com.jeancoder.ticketingsys.entry.system
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.CinemaConfig
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.jeancoder.ticketingsys.ready.system.TicketingSystemService

Result result = new Result();
JCLogger logger = LoggerSource.getLogger(this.class);

String idStr = JC.request.param("id");
String config_name = JC.request.param("config_name");
String channel_code = JC.request.param("channel_code");
String channel_key = JC.request.param("channel_key");
String channel_address = JC.request.param("channel_address");
String system_code = JC.request.param("system_code");
String system_name = TicketingSysTypeHelper.getSysName(system_code);
def pid = GlobalHolder.proj.id;

try {
	Long id = null;
	if(idStr != null && !"".equals(idStr)) {
		id = Long.valueOf(idStr);
	}
	CinemaConfig cinema_config = null;
	def update = true;
	if(id!=null) {
		cinema_config = JcTemplate.INSTANCE().get(CinemaConfig, 'select * from CinemaConfig where id=?', id);
		if(cinema_config==null) {
			return Res.Failed(Codes.COMMON_PARAM_ERROR);
		}
	} else {
		cinema_config = new CinemaConfig();
		cinema_config.pid = GlobalHolder.proj.id;
		cinema_config.flag = 0;
		update = false;
	}
	cinema_config.config_name = config_name;
	cinema_config.ss_code = system_code;
	cinema_config.ss_name = system_name;
	cinema_config.auth_chan_num = channel_code;
	cinema_config.auth_chan_code = channel_key;
	cinema_config.auth_chan_url = channel_address;
	if(update) {
		JcTemplate.INSTANCE().update(cinema_config);
	} else {
		JcTemplate.INSTANCE().save(cinema_config);
	}
	
//	TicketingSystemService.INSTANCE.addOrUpdate(id, config_name,channel_code, channel_key, channel_address, system_code,system_name,pid);
	result.setData(Res.Success());
	return result;
}catch(Exception e) {
	logger.error("添加售票系统失败",e)
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}


