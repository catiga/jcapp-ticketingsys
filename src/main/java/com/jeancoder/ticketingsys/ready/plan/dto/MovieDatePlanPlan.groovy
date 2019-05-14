package com.jeancoder.ticketingsys.ready.plan.dto

import com.jeancoder.ticketingsys.ready.schema.dto.PlanSchema

class MovieDatePlanPlan {
	private String cinemaId;
	private String planId;
	private String startTime;
	private String endTime;
	private String subtitle;
	private String language;
	private String format;
	private String hallId;
	private String hall;
	private String dimensional
	private String size;
	private String lastUpdateTime;
	private String planDate;
	private String sell_code;
	
	Integer buy_flag = 1;	//默认允许购买

	private String standard_price;
	private String current_price;

	private List<PlanSchema> schemas;
	
	public String getSell_code() {
		return sell_code;
	}
	public void setSell_code(String sell_code) {
		this.sell_code = sell_code;
	}
	
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getHall() {
		return hall;
	}
	public void setHall(String hall) {
		this.hall = hall;
	}
	public String getDimensional() {
		return dimensional;
	}
	public void setDimensional(String dimensional) {
		this.dimensional = dimensional;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public List<PlanSchema> getSchemas() {
		return schemas;
	}
	public void setSchemas(List<PlanSchema> schemas) {
		this.schemas = schemas;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getHallId() {
		return hallId;
	}
	public void setHallId(String hallId) {
		this.hallId = hallId;
	}
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public String getStandard_price() {
		return standard_price;
	}
	public void setStandard_price(String standard_price) {
		this.standard_price = standard_price;
	}
	public String getCurrent_price() {
		return current_price;
	}
	public void setCurrent_price(String current_price) {
		this.current_price = current_price;
	}
	public String getStartClock() {
		if(startTime.indexOf(" ")>-1) {
			return startTime.split(" ")[1].substring(0, 5);
		}
		return startTime;
	}
	public String getEndClock() {
		if(endTime.indexOf(" ")>-1) {
			return endTime.split(" ")[1].substring(0, 5);
		}
		return endTime;
	}
}
