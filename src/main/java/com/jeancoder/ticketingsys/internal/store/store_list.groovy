package com.jeancoder.ticketingsys.internal.store

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema

JCLogger logger = LoggerSource.getLogger();
def pid = JC.internal.param('pid');

List<Cinema> stores = JcTemplate.INSTANCE().find(Cinema, 'select * from Cinema where proj_id=? and flag!=?', pid, -1);
return SimpleAjax.available('', stores)

