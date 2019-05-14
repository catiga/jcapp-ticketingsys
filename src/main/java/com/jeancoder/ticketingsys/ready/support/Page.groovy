package com.jeancoder.ticketingsys.ready.support

class Page {
	//输入参数 每页数据数
	private int pageSize = 20;
	//输入参数 当前页码
	private int pageNo = 1;
	//输出参数 总记录数
	private int totalCount = 0;
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	//输出参数 总页数
	public int getTotalPage() {
		return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize +1;
	}
	
	//输入参数
	public String limitExp() {
		return ((pageNo - 1) * pageSize) + "," + pageSize;
	}
}
