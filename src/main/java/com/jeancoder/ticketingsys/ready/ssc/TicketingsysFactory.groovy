package com.jeancoder.ticketingsys.ready.ssc

import com.milepai.core.mc.McProtocol
import com.milepai.core.ssc.TcSsConstants
import com.piaodaren.ssc.factory.AuthModelTrans
import com.piaodaren.ssc.factory.SscFactory
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.SscAuthModel

class TicketingsysFactory {
	public static SscAuthModel direct_generateAuthModel(String ss_type, String channel_url, String channel_num, String channel_code) {
		return SscFactory.direct_generateAuthModel(ss_type, channel_url, channel_num, channel_code);
	}
	
	public static SscAuthModel direct_generateAuthModel(String ss_type, AuthModelTrans auth_trans) {
		 return SscFactory.direct_generateAuthModel(ss_type, auth_trans);
	}
	
	public static SscAuthModel generateAuthModel(McProtocol protocol) {
		 return SscFactory.generateAuthModel(protocol);
	}
	
	public static SscOp generateSscOp(SscAuthModel auth_model) {
		if(auth_model.getTsType().equals(TcSsConstants._tc_ss_pdr)) {
			return  (SscOp)new PdrSscOp(auth_model);
		}
		return SscFactory.generateSscOp(auth_model);
	}
}
