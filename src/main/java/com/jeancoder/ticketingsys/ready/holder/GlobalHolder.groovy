package com.jeancoder.ticketingsys.ready.holder

import com.jeancoder.ticketingsys.ready.dto.sys.AuthToken
import com.jeancoder.ticketingsys.ready.dto.sys.SysProjectInfo
import com.jeancoder.ticketingsys.ready.trade.DataTrans

class GlobalHolder {
	
	private static final ThreadLocal<DataTrans> _data_trans_ = new ThreadLocal<DataTrans>();
	
	private static ThreadLocal<SysProjectInfo> _sys_project_ = new ThreadLocal<SysProjectInfo>();
	private static ThreadLocal<AuthToken> _token_ = new ThreadLocal<AuthToken>();
	
	static setDt(DataTrans dt) {
		_data_trans_.set(dt);
	}
	static getDt() {
		_data_trans_.get();
	}
	
	public static void setProj(SysProjectInfo e) {
		_sys_project_.set(e);
	}
	
	public static SysProjectInfo getProj() {
		return _sys_project_.get();
		
	}
	
	public static void setToken(AuthToken token) {
		_token_.set(token);
	}
	
	public static AuthToken getToken() {
		return _token_.get();
	}
	
	public static void remove() {
		_sys_project_.remove();
		_data_trans_.remove();
		_token_.remove();
	}
	
}
