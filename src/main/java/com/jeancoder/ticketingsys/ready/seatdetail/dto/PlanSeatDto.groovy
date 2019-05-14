package com.jeancoder.ticketingsys.ready.seatdetail.dto

import com.jeancoder.ticketingsys.ready.order.dto.SeatOrderDto

class PlanSeatDto {
	 String seatNo;
	
	 String seatPieceNo;
	
	 String graphRow;
	
	 String graphCol;
	
	 String seatRow;
	
	 String seatCol;
	
	 String seatState;
	
	
	 String cineSeatId;
	
	 String cinemaId;

	 int xCoord;

	 int yCoord;

	 String loveseats;

	 String row;

	 String column;

	 String status;

	 String type;
	
	 String area_no;
	
	 SeatOrderDto reserveOrder;
	 SeatOrderDto buyOrder;
	 SeatOrderDto lockOrder;
	 SeatOrderDto prelockOrder;
}
