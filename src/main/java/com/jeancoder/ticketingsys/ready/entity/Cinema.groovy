package com.jeancoder.ticketingsys.ready.entity

import java.sql.Timestamp

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCForeign
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn
import com.jeancoder.ticketingsys.ready.system.dto.SystemMinInfo

@JCBean(tbname = 'data_store_info')
class Cinema {

	@JCID
	BigInteger id;
	
	@JCForeign
	BigInteger store_basic;
	
	String store_name;
	
	String store_no;
	
	String store_logo;
	
	String province;
	
	String province_no;
	
	String city;
	
	String city_no;
	
	String zone;
	
	String zone_no;
	
	String address;
	
	String introduction;
	
	String latitude;
	
	String longitude;
	
	String physics_name;
	
	BigInteger config_id;
	
	BigInteger proj_id;
	
	String time_type;
	
	String time_quantity;
	
	Timestamp c_time;
	
	Integer pubflag = 1;	//默认为出票
	
	@JCNotColumn
	SystemMinInfo systemInfo;

}
