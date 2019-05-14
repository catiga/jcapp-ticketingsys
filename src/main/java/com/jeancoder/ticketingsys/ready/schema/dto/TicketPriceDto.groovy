package com.jeancoder.ticketingsys.ready.schema.dto

import java.text.SimpleDateFormat

class TicketPriceDto {
	static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	Long id;
	String running_time;//放映时间
	String currt_running_time;
	String movie_dimensional;//影片类型
	String movie_size;//影片尺寸
	BigDecimal price;//影片价格
	BigDecimal min_price;//最低价格
	String movie_limit;//限制影片
	String store_limit;//限制影城
	String hall_limit; //影厅限制
	String status;//10代表结算页面
	def mch_id;
	def running_time_by_plan(def plan_start_time) {
		if(plan_start_time) {
			String []start_times = plan_start_time.split(' ');
			String date_currt=start_times[0];
			
			Calendar c = Calendar.getInstance();
			c.setTime(format1.parse(date_currt));
			def week_date = c.get(Calendar.DAY_OF_WEEK);
			week_date = (week_date+6)>7?(week_date+6-7):(week_date+6);	//tranfer
			def time_value = week_date + ',' + start_times[1];
			this.running_time = time_value;
		}
	}
}
