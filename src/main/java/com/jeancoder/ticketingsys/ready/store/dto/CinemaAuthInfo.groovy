package com.jeancoder.ticketingsys.ready.store.dto

class CinemaAuthInfo {
	private Long id;
	
	private String cinemaId;
	
	private String cinemaCode;
	private String authChannelCode;
	private String authChannelNo;
	private String authChannelUrl;
	private String systemSsCode;
	
	Long pid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCinemaId() {
		return cinemaId;
	}
	public void setCinemaId(String cinemaId) {
		this.cinemaId = cinemaId;
	}
	public String getCinemaCode() {
		if(cinemaId!=null) {
			return cinemaId;
		}
		return cinemaCode;
	}
	public void setCinemaCode(String cinemaCode) {
		this.cinemaCode = cinemaCode;
	}
	public String getAuthChannelCode() {
		return authChannelCode;
	}
	public void setAuthChannelCode(String authChannelCode) {
		this.authChannelCode = authChannelCode;
	}
	public String getAuthChannelNo() {
		return authChannelNo;
	}
	public void setAuthChannelNo(String authChannelNo) {
		this.authChannelNo = authChannelNo;
	}
	public String getAuthChannelUrl() {
		return authChannelUrl;
	}
	public void setAuthChannelUrl(String authChannelUrl) {
		this.authChannelUrl = authChannelUrl;
	}
	public String getSystemSsCode() {
		return systemSsCode;
	}
	public void setSystemSsCode(String systemSsCode) {
		this.systemSsCode = systemSsCode;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	
}