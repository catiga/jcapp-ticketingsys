package com.jeancoder.ticketingsys.ready.dto.store

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

class StoreGeneral {
	
	BigInteger id;

	BigInteger store_basic;
	
	String store_logo;
	
	String store_no;
	
	String store_name;
	
	String province;
	
	String city;
	
	String province_no;
	
	String city_no;
	
	String zone_no;
	
	String zone;
	
	String address;
	
	String phone;
	
	String introduction;
	
	BigInteger manager_id;
	
	Timestamp c_time;
	
	String latitude;
	
	String longitude;
	
	BigInteger utimekey;
	
	BigInteger duty_manager_id;
	
	BigInteger proj_id;
	
	String domain;
	
	Integer flag;

}
