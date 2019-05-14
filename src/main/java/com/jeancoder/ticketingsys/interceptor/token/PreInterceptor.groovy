package com.jeancoder.ticketingsys.interceptor.token

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCCookie
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.dto.sys.AuthToken
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.util.NativeUtil


/**
 * @urlmapped("/")
*
 */

@urlmapped("/")
@urlpassed(['/incall', '/outcall','/api'])

JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
JCRequest req = JC.request.get();
JCResponse response = ResponseSource.getResponse();
String domain = req.getServerName();
int port = req.getServerPort();
String login = "http://" + domain + ":" + port + "/project/index/index";
try {
	String _c_u_k_ = null;
	JCCookie[] cookies = req.getCookies()
	if(cookies!=null&&cookies.length>0) {
		for(JCCookie c : cookies) {
			if(c.getName().equals("_c_u_k_adm_")) {
				try {
					_c_u_k_ = URLDecoder.decode(c.getValue(), "utf-8");
				}catch(Exception e){
				}
				break;
			}
		}
	}
	if (_c_u_k_ == null || _c_u_k_.length() == 0) {
		response.sendRedirect(login);
		return new Result().setRedirectResource("/project/index/index");
	}
	
	AuthToken token = NativeUtil.connect(AuthToken.class, 'project', '/incall/token', ["pid":GlobalHolder.proj.id,"token":_c_u_k_]);
	if (token == null) {
		response.sendRedirect(login);
		return new Result().setRedirectResource("/project/index/index");
	}
	GlobalHolder.setToken(token);
	req.setAttribute('current_token', token);
	return true;
}catch(Exception e) {
	Logger.error("未登陆",e);
	response.sendRedirect(login);
	return new Result().setRedirectResource("/project/index/index");
}
