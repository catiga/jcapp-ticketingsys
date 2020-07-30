package com.jeancoder.ticketingsys.ready.ticketSalesRules

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.util.StringUtil
import com.jeancoder.jdbc.JcPage
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.TicketSalesRules
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder

class ticketSalesRulesService {
	private static final JCLogger LOGGER = LoggerSource.getLogger(ticketSalesRulesService.class);
	private static final JcTemplate jcTemplate = JcTemplate.INSTANCE();
	public static Integer saveItem(TicketSalesRules rule){
		TicketSalesRules item =new TicketSalesRules();
		item.title=rule.title;
		item.a_time=new Date();
		item.info=rule.info;
		item.time_streg = rule.time_streg;
		item.content = rule.content;
		item.time_type=rule.time_type;
		item.store_type = rule.store_type;
		item.p_id=GlobalHolder.getProj().getId();
		item.store_type_name =rule.store_type_name;
		item.movie_type = rule.movie_type;
		item.c_time = new Timestamp(new Date().getTime());
		item.flag = new Integer(0);
		item.allow_low_price = rule.allow_low_price;
		item.movie_type_name =rule.movie_type_name;
		item.price_streg = rule.price_streg;
		item.apstatus = "10";
		item.hall_id = rule.hall_id;
		return jcTemplate.save(item);
	}
	public static JcPage<TicketSalesRules> getList(BigInteger pid, JcPage<TicketSalesRules> page,String apstatus){
		StringBuffer sql = new StringBuffer ("select * from TicketSalesRules where p_id =? and flag!=? ");
		if (!StringUtil.isEmpty(apstatus)) {
			sql.append("and apstatus=");
			sql.append("'"+apstatus+"'");
		}
		sql.append("order by seq desc");
		return jcTemplate.find(TicketSalesRules.class, page, sql.toString(), pid,-1);
	}

	public  static Integer updateStatus(def pid ,BigInteger id,String mstatus){
		//10未开始 20进行中 21暂停 30结束
		TicketSalesRules rule=getItem(pid, id);
		if (mstatus.equals("20")) {
			rule.apstatus="20";
			rule.c_time=new Timestamp(new Date().getTime());
			return jcTemplate.update(rule);
		}
		if (mstatus=="21") {
			rule.apstatus=mstatus;
			rule.c_time=new Timestamp(new Date().getTime());
			return jcTemplate.update(rule);
		}
		if (rule.apstatus=="20"||rule.apstatus=="21") {
			rule.apstatus="30";
			rule.c_time=new Timestamp(new Date().getTime());
			return jcTemplate.update(rule);
		}
	}
	public static TicketSalesRules getItem(def pid,def id){
		String sql="select * from TicketSalesRules where p_id =? and id=? and flag!=? ";
		return jcTemplate.get(TicketSalesRules.class, sql, pid,id,-1);
	}
	//返回可用的网售规则列表
	public static List<TicketSalesRules> getAll(def pid,def apstatus){
		String sql="select * from TicketSalesRules where p_id =? and apstatus=? and flag!=? order by seq desc"
		return jcTemplate.find(TicketSalesRules.class, sql, pid,apstatus,-1)
	}
	public static Integer updateItem(TicketSalesRules rule){
		def pid =GlobalHolder.getProj().getId();
		TicketSalesRules item =getItem(pid, rule.id);
		item.title=rule.title;
		item.info=rule.info;
		item.time_streg = rule.time_streg;
		item.content = rule.content;
		item.time_type=rule.time_type;
		item.store_type = rule.store_type;
		item.store_type_name =rule.store_type_name;
		item.movie_type = rule.movie_type;
		item.c_time = new Timestamp(new Date().getTime());
		item.allow_low_price = rule.allow_low_price;
		item.movie_type_name =rule.movie_type_name;
		item.price_streg = rule.price_streg;
		item.hall_id = rule.hall_id;
		return jcTemplate.update(item);
	}
}
