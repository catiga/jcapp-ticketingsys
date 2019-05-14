package com.jeancoder.ticketingsys.ready.plan.dto;

import java.util.List;
import java.util.Map;

class HpPlanSeatsDto{

	private Integer big_col;
	
	private Integer big_row;
	
	private Integer all_row;
	
	private Integer left_seat_num;
	
	private Integer sold_seat_num;
	
	private List<Map<String, Object>> rows;

	public Integer getBig_col() {
		return big_col;
	}

	public void setBig_col(Integer big_col) {
		this.big_col = big_col;
	}

	public Integer getBig_row() {
		return big_row;
	}

	public void setBig_row(Integer big_row) {
		this.big_row = big_row;
	}

	public Integer getAll_row() {
		return all_row;
	}

	public void setAll_row(Integer all_row) {
		this.all_row = all_row;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public Integer getLeft_seat_num() {
		return left_seat_num;
	}

	public void setLeft_seat_num(Integer left_seat_num) {
		this.left_seat_num = left_seat_num;
	}

	public Integer getSold_seat_num() {
		return sold_seat_num;
	}

	public void setSold_seat_num(Integer sold_seat_num) {
		this.sold_seat_num = sold_seat_num;
	}

}
