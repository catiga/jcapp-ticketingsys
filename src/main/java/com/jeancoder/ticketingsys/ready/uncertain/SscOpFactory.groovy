package com.jeancoder.ticketingsys.ready.uncertain

import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.SscAuthModel

class SscOpFactory {

	public static SscOp generateSscOp(SscAuthModel auth_model, def user_id) {
		SscOp ssc = TicketingsysFactory.generateSscOp(auth_model);
		
		SscOpProxy proxy = new SscOpProxy();
		proxy.instance = ssc;
		proxy.user_id = user_id;
		return proxy;
	}
}
