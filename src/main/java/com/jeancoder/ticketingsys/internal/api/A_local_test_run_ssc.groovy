package com.jeancoder.ticketingsys.internal.api

import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.SscAuthModel

CinemaAuthInfo cinemaAuthInfo = new CinemaAuthInfo();
cinemaAuthInfo.authChannelCode = '%k:gvv?uF>';
cinemaAuthInfo.authChannelNo = '11115';
cinemaAuthInfo.authChannelUrl = 'http://api.open.yinghezhong.com';
//cinemaAuthInfo.cinemaId = 17;
cinemaAuthInfo.cinemaCode = '20001205'
cinemaAuthInfo.systemSsCode = 'dingxin';

if(cinemaAuthInfo == null) {
	return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
}

Calendar now = Calendar.getInstance(TimeZone.getDefault());
now.setTime(new Date());

def start = new Date().getTime();
String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
String channel_url = cinemaAuthInfo.getAuthChannelUrl()
String channel_num = cinemaAuthInfo.getAuthChannelNo()
String channel_code = cinemaAuthInfo.getAuthChannelCode()
SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);

SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);

Date start_time = now.getTime()

now.add(Calendar.DATE, 3);
Date end_time = now.getTime()

CinemaPlanResult planResult = ssc_op.get_cinema_plans(cinemaAuthInfo.getCinemaCode(), start_time, end_time);
println planResult;

