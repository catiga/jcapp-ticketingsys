package com.jeancoder.ticketingsys.interceptor.mod

import com.jeancoder.annotation.urlmapped
import com.jeancoder.annotation.urlpassed
import com.jeancoder.app.sdk.JC
import com.jeancoder.core.http.JCRequest
import com.jeancoder.ticketingsys.ready.dto.FuncUtil
import com.jeancoder.ticketingsys.ready.dto.sys.AppFunction
import com.jeancoder.ticketingsys.ready.dto.sys.SysFunction
import com.jeancoder.ticketingsys.ready.dto.sys.SysProjectInfo
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.jeancoder.ticketingsys.ready.util.NativeUtil


@urlmapped("/")
@urlpassed(['/incall', '/outcall','/api'])

AppFunction mod_g_1 = FuncUtil.build_app_func(1, '售票系统', null, 'system/list', 'fa-shopping-cart');

AppFunction mod_g_2 = FuncUtil.build_app_func(2, '影城管理', null, 'store/list', 'fa-shopping-cart');
AppFunction mod_g_3 = FuncUtil.build_app_func(3, '影讯管理', null, 'movie/list', 'fa-shopping-cart');
AppFunction mod_g_4 = FuncUtil.build_app_func(4, '票类管理', null, 'schema/list', 'fa-shopping-cart');
AppFunction mod_g_5 = FuncUtil.build_app_func(5, '调用日志', null, 'ssclog/list', 'fa-shopping-cart');
AppFunction mod_g_6 = FuncUtil.build_app_func(6, '价格策略', null, 'ticketSalesRules/list', 'fa-shopping-cart');
AppFunction mod_g_7 = FuncUtil.build_app_func(7, '选座订单', null, 'order/index', 'fa-shopping-cart');

List<AppFunction> result = [mod_g_1];
result.addAll([mod_g_2]);
result.addAll([mod_g_3]);
result.addAll([mod_g_4]);
result.addAll([mod_g_5]);
result.addAll([mod_g_6]);
result.addAll([mod_g_7]);



AppFunction mod_g_main = new AppFunction();
mod_g_main.func_name = '票务系统';
mod_g_1.func_info = '配置售票的API地址以及名称';
mod_g_2.func_info = '配置影城的基本信息和可使用的票类';
mod_g_3.func_info = '影讯同步';
mod_g_4.func_info = '设置票类的基本信息';
mod_g_5.func_info = '可查看系统日志';
mod_g_6.func_info = '设置价格优惠策略以及适用的时间';
List<AppFunction> appFun = new ArrayList<AppFunction>();
appFun.add(mod_g_1);
appFun.add(mod_g_2);
appFun.add(mod_g_3);
appFun.add(mod_g_4);
appFun.add(mod_g_5);
appFun.add(mod_g_6);

JCRequest request = JC.request.get();
request.setAttribute("appMain", mod_g_main);
request.setAttribute('appFun', appFun);
def uri = request.getRequestURI();
def context = request.getContextPath();

def uri_without_code = uri[context.length()+1..-1];
if(uri_without_code.endsWith("/"))
	uri_without_code = uri_without_code[0..-2];
request.setAttribute("__now_uri_", uri_without_code);

//println "ticketingsys_____" + GlobalHolder.getToken().user.id + "____" + GlobalHolder.getProj().id;
List<AppFunction> functions = NativeUtil.connectAsArray(AppFunction.class, 'project', '/incall/mod/mods', [pid:GlobalHolder.getProj().id,user_id:GlobalHolder.getToken().user.id,app_code:'ticketingsys', accept:URLEncoder.encode(JackSonBeanMapper.listToJson(result), 'UTF-8')]);

//functions = result;

Map<AppFunction, List<AppFunction>> my_funcs = my_funcs(functions);
request.setAttribute("user_roles_functions", my_funcs);
return true;

def get_by_id(def id, List<AppFunction> functions) {
	for(AppFunction f : functions) {
		if(f.id==id) {
			return f;
		}
	}
	return null;
}



def Map<AppFunction, List<AppFunction>> my_funcs(List<AppFunction> functions) {
	Map<AppFunction, List<AppFunction>> parent_functions = new LinkedHashMap<AppFunction, List<AppFunction>>();
	SysProjectInfo project = GlobalHolder.getProj();
	if(functions!=null&&!functions.isEmpty()) {
		for(AppFunction f : functions) {
			AppFunction parent_f = null;
			List<AppFunction> result_f = new ArrayList<AppFunction>();
			
			//只取两级的判断
			if(f.getLevel().equals(1)) {
				//表示当前这个为一级模块
				parent_f = f;
				for(AppFunction f_2 : functions) {
					if('0000'.equals(f_2.getFunc_type())){
						continue;
					}
					if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
						if(f_2.getLimpro().equals("0")&&!project.root) {
							continue;
						}
						result_f.add(f_2);
					}
				}
			} else if(f.getLevel().equals(2)) {
				//表示当前这个为二级模块
				parent_f = get_by_id(f.getParent_id(), functions);
				if(parent_f==null) {
					continue;
				}
				for(AppFunction f_2 : functions) {
					if('0000'.equals(f_2.getFunc_type())){
						continue;
					}
					if(f_2.getParent_id()!=null&&f_2.getParent_id().equals(parent_f.getId())) {
						if(f_2.getLimpro().equals("0")&&!project.root) {
							continue;
						}
						result_f.add(f_2);
					}
				}
			}
			parent_functions.put(parent_f, result_f);
		}
	}
	return parent_functions;
}