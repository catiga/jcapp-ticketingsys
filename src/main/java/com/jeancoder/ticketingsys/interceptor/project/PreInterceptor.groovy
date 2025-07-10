package com.jeancoder.ticketingsys.interceptor.project

//import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.sys.SysProjectInfo;
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.util.NativeUtil


//@urlmapped("/")

GlobalHolder.remove();
JCRequest req = JC.request.get();
String domain = req.getServerName();
//String domain = "jcloudapp.pdr365.com";
//String domain = "e.local";
//String domain = "127.0.0.1";



req.setAttribute("domain", domain)
SysProjectInfo project = NativeUtil.connect(SysProjectInfo.class, 'project', '/incall/project', ["domain":domain]);
req.setAttribute("current_project", project);

//req.setAttribute('pub_bucket', 'http://static.jcloudapp.chinaren.xyz/static/')
//req.setAttribute('pub_bucket', 'https://static.hash.bid/static/')
req.setAttribute('pub_bucket', 'https://static.qrcinema.cn/')

GlobalHolder.setProj(project);
return true;