package com.jeancoder.ticketingsys.interceptor.project

import com.jeancoder.annotation.urlmapped
import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.ticketingsys.ready.dto.RemoteUtil
import com.jeancoder.ticketingsys.ready.dto.sys.SysProjectInfo;
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.util.NativeUtil


//@urlmapped("/")

GlobalHolder.remove();
JCRequest request = RequestSource.getRequest();
String domain = request.getServerName();
//String domain = "jcloudapp.pdr365.com";
//String domain = "e.local";
//String domain = "127.0.0.1";
println "PreInterceptor"+request.getPathInfo();
request.setAttribute("domain", domain)
SysProjectInfo project = NativeUtil.connect(SysProjectInfo.class, 'project', '/incall/project', ["domain":domain]);
request.setAttribute("current_project", project);

//request.setAttribute('pub_bucket', 'https://cdn.iplaysky.com/static/');
// request.setAttribute('pub_bucket', 'https://static.pdr365.com/static/');
req.setAttribute('pub_bucket', 'https://ticket.wisetrip.com/res/')

GlobalHolder.setProj(project);
return true;