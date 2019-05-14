package com.jeancoder.ticketingsys.ready.store.dto

import com.jeancoder.ticketingsys.ready.system.dto.SystemMinInfo

class StoreInfo {
	private Long id;
	
	Long store_basic;
	
	private String store_no;
	private String store_name;
	private String province;
	private String city;
	private String zone;
	private String address;
	private String province_no;
	private String city_no;
	private String zone_no;
	private Long config_id;
	private String physics_name;
	private def systemInfo;
	private Date c_time;
	
	Long proj_id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStore_no() {
		return store_no;
	}
	public void setStore_no(String store_no) {
		this.store_no = store_no;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince_no() {
		return province_no;
	}
	public void setProvince_no(String province_no) {
		this.province_no = province_no;
	}
	public String getCity_no() {
		return city_no;
	}
	public void setCity_no(String city_no) {
		this.city_no = city_no;
	}
	public String getZone_no() {
		return zone_no;
	}
	public void setZone_no(String zone_no) {
		this.zone_no = zone_no;
	}
	public Long getConfig_id() {
		return config_id;
	}
	public void setConfig_id(Long config_id) {
		this.config_id = config_id;
	}
	public String getPhysics_name() {
		return physics_name;
	}
	public void setPhysics_name(String physics_name) {
		this.physics_name = physics_name;
	}
	public def getSystemInfo() {
		return systemInfo;
	}
	public void setSystemInfo(def systemInfo) {
		this.systemInfo = systemInfo;
	}
	public Date getC_time() {
		return c_time;
	}
	public void setC_time(Date c_time) {
		this.c_time = c_time;
	}
}
