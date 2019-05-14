package com.jeancoder.ticketingsys.ready.system.dto

class SystemCodeInfo {
	private Long id;
	private String config_name;
	private String ss_code;
	private String ss_name;
	private String auth_chan_num;
	private String auth_chan_code;
	private String auth_chan_url;
	private Date c_time;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConfig_name() {
		return config_name;
	}
	public void setConfig_name(String config_name) {
		this.config_name = config_name;
	}
	public String getSs_code() {
		return ss_code;
	}
	public void setSs_code(String ss_code) {
		this.ss_code = ss_code;
	}
	public String getSs_name() {
		return ss_name;
	}
	public void setSs_name(String ss_name) {
		this.ss_name = ss_name;
	}
	public String getAuth_chan_num() {
		return auth_chan_num;
	}
	public void setAuth_chan_num(String auth_chan_num) {
		this.auth_chan_num = auth_chan_num;
	}
	public String getAuth_chan_code() {
		return auth_chan_code;
	}
	public void setAuth_chan_code(String auth_chan_code) {
		this.auth_chan_code = auth_chan_code;
	}
	public String getAuth_chan_url() {
		return auth_chan_url;
	}
	public void setAuth_chan_url(String auth_chan_url) {
		this.auth_chan_url = auth_chan_url;
	}
	public Date getC_time() {
		return c_time;
	}
	public void setC_time(Date c_time) {
		this.c_time = c_time;
	}
}
