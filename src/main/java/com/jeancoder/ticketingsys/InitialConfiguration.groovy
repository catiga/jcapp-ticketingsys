package com.jeancoder.ticketingsys

import com.jeancoder.app.sdk.JC
import com.jeancoder.ticketingsys.ready.dto.FuncUtil
import com.jeancoder.ticketingsys.ready.dto.sys.AppFunction
import com.jeancoder.ticketingsys.ready.uncertain.KeepCertain
import com.jeancoder.ticketingsys.ready.uncertain.TcssCacheTask
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.jeancoder.ticketingsys.ready.util.NativeUtil

JC.interceptor.add('project/PreInterceptor', null);
JC.interceptor.add('token/PreInterceptor', null);
JC.interceptor.add('mod/PreInterceptor', null);
JC.interceptor.add('general/PreInterceptor', null);

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
result.add(mod_g_6);
result.add(mod_g_7);

NativeUtil.connect( 'project', '/incall/mod/mods', [app_code:'ticketingsys', accept:URLEncoder.encode(JackSonBeanMapper.listToJson(result), 'UTF-8')]);

/**
 * 任务代码
 */
def default_exe_time_diff = 12*1000L;	//默认12秒
default_exe_time_diff = 9*60*1000L;	//改为9分钟执行一次

JC.thread.timeTask(10000, default_exe_time_diff, {
	KeepCertain.fuckoff(null);
});

//新增一个任务，每20分钟刷新一次影讯缓存
/*
def tcss_cache_time_diff = 10*60*1000L;    //默认10分钟刷新一次
JC.thread.timeTask(30*1000, tcss_cache_time_diff, {
	TcssCacheTask.sync_data();
});
*/

