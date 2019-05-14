package com.jeancoder.ticketingsys.ready.order.dto

import java.sql.Timestamp

import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveInfo
import com.jeancoder.ticketingsys.ready.order.entity.TicketSaleInfo
import com.jeancoder.ticketingsys.ready.util.DateUtil

class TicketSaleInfoDto {
	String order_no;//订单编号
	String store_name;//门店名字
	String hall_name;//演出厅
	String film_name;//演出内容
	String plan_date;//演出日期
	String plan_time;
	String pic_url;
	String o_c;
	String refund_time;
	String remark;
	String pay_type;//支付方式
	Integer ticket_sum;//票数
	String total_amount//应收
	String pay_amount//实收
	String handle_fee//手续费
	String seats;
	
	public TicketSaleInfoDto() {
	}
	
	
	public TicketSaleInfoDto(TicketSaleInfo item) {
		this.order_no = item.order_no;//订单编号
		this.store_name = item.store_name;//门店名字
		this.hall_name = item.hall_name;//演出厅
		this.film_name = item.film_name;//演出内容
		this.plan_date = item.plan_date;//演出日期
		this.plan_time = item.plan_time;
		this.pic_url = item.pic_url;
		this.o_c = item.o_c;
		if (item.refund_time != null) {
			this.refund_time = DateUtil.getDate(item.refund_time.getTime());
		}
		this.remark = item.remark;
		this.pay_type = item.pay_type;//支付方式
		this.ticket_sum = item.ticket_sum;//票数
		this.total_amount = item.total_amount;//应收
		this.pay_amount = item.pay_amount;//实收
		this.handle_fee = item.handle_fee//手续费
	}
	
	public TicketSaleInfoDto(TicketReserveInfo item) {
		this.order_no = item.order_no;//订单编号
		this.store_name = item.store_name;//门店名字
		this.hall_name = item.hall_name;//演出厅
		this.film_name = item.film_name;//演出内容
		this.plan_date = item.plan_date;//演出日期
		this.plan_time = item.plan_time;
		this.pic_url = item.pic_url;
		this.o_c = item.o_c;
		if (item.refund_time != null) {
			this.refund_time = DateUtil.getDate(item.refund_time.getTime());
		}
		this.remark = item.remark;
		this.pay_type = item.pay_type;//支付方式
		this.ticket_sum = item.ticket_sum;//票数
		this.total_amount = item.total_amount;//应收
		this.pay_amount = item.pay_amount;//实收
		this.handle_fee = item.handle_fee//手续费
	}
	
}
