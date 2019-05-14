package com.jeancoder.ticketingsys.ready.plan.dto

import java.text.SimpleDateFormat

class MovieDatePlanDate {
	private String date;
	private String aliasName;
	private List<MovieDatePlanPlan> plans = new ArrayList<MovieDatePlanPlan>();
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
		
		try {
			SimpleDateFormat dateSDF = new SimpleDateFormat("yyyy-MM-dd");
			Date targetDate = dateSDF.parse(date);
			Calendar c = Calendar.getInstance(TimeZone.default);
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND,0);
			c.set(Calendar.MILLISECOND,0);
			int step = 1;
			if(c.getTime().compareTo(targetDate) == 1) {
				this.setAliasName(date);
			}else {
				int catday = 0;
				while(!dateSDF.format(c.getTime()).equals(date)) {
					c.add(Calendar.DATE, 1);
					catday ++;
				}
				if(catday == 0) {
					this.setAliasName("今天");
				}else if(catday == 1) {
					this.setAliasName("明天");
				}else if(catday == 2) {
					this.setAliasName("后天");
				}else {
					this.setAliasName(date);
				}
			}
		}catch(Exception e) {
			this.setAliasName(date);
		}
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public List<MovieDatePlanPlan> getPlans() {
		return plans;
	}
	public void setPlans(List<MovieDatePlanPlan> plans) {
		this.plans = plans;
	}
}
