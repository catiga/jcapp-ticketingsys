package com.jeancoder.ticketingsys.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'dic_city')
class DicCity {

	@JCID
	Integer id;
	
	String city_no;
	
	String city_name;
	
	Integer m_level;
	
	Integer pid;
	
	String s_name;
	
	Integer flag;
}
