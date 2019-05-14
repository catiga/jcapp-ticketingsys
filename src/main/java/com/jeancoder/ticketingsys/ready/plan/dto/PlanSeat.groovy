package com.jeancoder.ticketingsys.ready.plan.dto

import com.jeancoder.ticketingsys.ready.order.dto.SeatOrderDto

class PlanSeat {
	private String seatNo;
	
	private String seatPieceNo;
	
	private String graphRow;
	
	private String graphCol;
	
	private String seatRow;
	
	private String seatCol;
	
	private String seatState;
	
	
	private String cineSeatId;
	
	private String cinemaId;

	private int xCoord;

	private int yCoord;

	private String loveseats;

	private String row;

	private String column;

	private String status;

	private String type;
	
	private String area_no;
	
	private SeatOrderDto reserveOrder;
	private SeatOrderDto buyOrder;
	private SeatOrderDto lockOrder;
	private SeatOrderDto prelockOrder;

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getSeatPieceNo() {
		return seatPieceNo;
	}

	public void setSeatPieceNo(String seatPieceNo) {
		this.seatPieceNo = seatPieceNo;
	}

	public String getGraphRow() {
		return graphRow;
	}

	public void setGraphRow(String graphRow) {
		this.graphRow = graphRow;
	}

	public String getGraphCol() {
		return graphCol;
	}

	public void setGraphCol(String graphCol) {
		this.graphCol = graphCol;
	}

	public String getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(String seatRow) {
		this.seatRow = seatRow;
	}

	public String getSeatCol() {
		return seatCol;
	}

	public void setSeatCol(String seatCol) {
		this.seatCol = seatCol;
	}

	public String getCineSeatId() {
		return cineSeatId;
	}

	public void setCineSeatId(String cineSeatId) {
		this.cineSeatId = cineSeatId;
	}

	public String getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public String getLoveseats() {
		return loveseats;
	}

	public void setLoveseats(String loveseats) {
		this.loveseats = loveseats;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getArea_no() {
		return area_no;
	}

	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}

	public String getSeatState() {
		return seatState;
	}

	public void setSeatState(String seatState) {
		this.seatState = seatState;
	}

	public SeatOrderDto getReserveOrder() {
		return reserveOrder;
	}

	public void setReserveOrder(SeatOrderDto reserveOrder) {
		this.reserveOrder = reserveOrder;
	}

	public SeatOrderDto getBuyOrder() {
		return buyOrder;
	}

	public void setBuyOrder(SeatOrderDto buyOrder) {
		this.buyOrder = buyOrder;
	}

	public SeatOrderDto getLockOrder() {
		return lockOrder;
	}

	public void setLockOrder(SeatOrderDto lockOrder) {
		this.lockOrder = lockOrder;
	}

	public SeatOrderDto getPrelockOrder() {
		return prelockOrder;
	}

	public void setPrelockOrder(SeatOrderDto prelockOrder) {
		this.prelockOrder = prelockOrder;
	}
	
}
