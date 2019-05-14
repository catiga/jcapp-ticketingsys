package com.jeancoder.ticketingsys;

import com.jeancoder.app.sdk.server.DevServer;

public class TicketingSysServer {
	public static void main(String[] args) {
		String fp = System.getProperty("user.dir");
		DevServer.start(8881, fp);
	}
}
