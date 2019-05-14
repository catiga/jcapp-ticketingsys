package com.jeancoder.ticketingsys.ready.order.dto.ticketing

import com.jeancoder.ticketingsys.ready.order.entity.TicketReserveSeat
import java.sql.Timestamp

class TicketGatherDto {
	String order_no;//订单编号
	String store_name;//门店名字
	String hall_name;//演出厅
	String film_name;//演出内容
	String plan_date;//演出日期
	Timestamp c_time;//操作日期
	String order_status;//状态
	String pay_type;//支付方式
	Integer ticket_sum;//票数
	String total_amount//应收
	String pay_amount//实收
	String handle_fee//手续费

	String ticket_refund;//是否退票  remote

	List<TicketSeatDto> ticketSeat;//座位信息
	def seat_no;//座位号
	String seat_sr;//排
	String seat_sc;//座
}
