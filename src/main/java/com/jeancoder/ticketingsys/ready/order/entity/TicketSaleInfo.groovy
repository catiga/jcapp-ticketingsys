package com.jeancoder.ticketingsys.ready.order.entity

import java.sql.Timestamp
import java.util.Date

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID
import com.jeancoder.jdbc.bean.JCNotColumn

@JCBean(tbname="data_tc_ss_sale_order_info")
class TicketSaleInfo  {
	@JCID
	BigInteger id;
	String order_no;//订单编号
	String original_no;
	BigInteger user_id;
	String mobile;
	String order_status;//状态
	Date a_time;
	Date pay_time;
	Date check_time;
	Date deliver_time;
	BigInteger store_id;
	String store_name;//门店名字
	String hall_id;
	String hall_name;//演出厅
	String film_name;//演出内容
	String plan_id;
	String plan_date;//演出日期
	String plan_time;
	String pic_url;
	Timestamp c_time;//操作日期
	BigInteger proj_id;
	Integer flag;
	String o_c;
	BigInteger com_order;
	BigInteger acmid;
	Date drawback_time;
	Date refund_time;
	String remark;
	BigInteger tclass_id;
	String pay_type;//支付方式
	Integer ticket_sum;//票数
	String total_amount//应收
	String pay_amount//实收
	
	BigDecimal handle_fee//手续费
	
	@JCNotColumn
	String seats;
}
