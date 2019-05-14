package com.jeancoder.ticketingsys.ready.seatdetail.seatdto

import java.util.List

import com.jeancoder.ticketingsys.ready.seatdetail.dto.SeatRowDetailDto

class PlanSeatsDto {
	Integer big_col;
	
	 Integer big_row;
	
	 Integer all_row;
	 List<SeatRowDetailDto> rows;
}
