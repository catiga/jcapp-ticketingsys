package com.jeancoder.ticketingsys.ready.schema

import java.math.RoundingMode
import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.ticketingsys.ready.constant.TicketingsysConstant
import com.jeancoder.ticketingsys.ready.dto.sys.MarketInfoDto
import com.jeancoder.ticketingsys.ready.dto.sys.MarketTicketRuleDto
import com.jeancoder.ticketingsys.ready.entity.TicketSalesRules
import com.jeancoder.ticketingsys.ready.schema.dto.GroupInfo
import com.jeancoder.ticketingsys.ready.schema.dto.GroupWithSchema
import com.jeancoder.ticketingsys.ready.schema.dto.HallSchemaWithItem
import com.jeancoder.ticketingsys.ready.schema.dto.PlanSchema
import com.jeancoder.ticketingsys.ready.schema.dto.SchemaChildItem
import com.jeancoder.ticketingsys.ready.schema.dto.SchemaWithItem
import com.jeancoder.ticketingsys.ready.schema.dto.TicketPriceDto
import com.jeancoder.ticketingsys.ready.schema.entity.TicketPriceSchema
import com.jeancoder.ticketingsys.ready.schema.entity.TicketPriceSchemaGroup
import com.jeancoder.ticketingsys.ready.schema.entity.TicketPriceSchemaItem
import com.jeancoder.ticketingsys.ready.ticketSalesRules.ticketSalesRulesService
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.jeancoder.ticketingsys.ready.util.StringUtil
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanMovie

class SchemaService {
	public static final SchemaService INSTANCE = new SchemaService();
	JCLogger logger = JCLoggerFactory.getLogger(this.getClass());
	public void addOrUpdateGroup(TicketPriceSchemaGroup group) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		dbpower.doUpdateSerialize(group, "id");
	}

	public GroupInfo getGroupById(Long id,def pid) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT id,group_name,a_time FROM ticket_price_schema_group WHERE id = ?"
		if(!StringUtil.isEmpty(String.valueOf(pid))&&pid!=1){
			sql += " and pid=" + pid;
		}
		return dbpower.doQueryUnique(GroupInfo.class, sql,id);
	}

	public List<GroupInfo> getGroupByName(String group_name,def pid) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT id,group_name,a_time FROM ticket_price_schema_group WHERE group_name = ? and flag!=?"
		if(!StringUtil.isEmpty(String.valueOf(pid))&&pid!=1){
			sql += " and pid=" + pid;
		}
		return dbpower.doQueryList(GroupInfo.class, sql,group_name,-1);
	}

	/**
	 * 根据名称和类型查询分组
	 * @param group_name
	 * @param group_type
	 * @return
	 */
	public List<GroupInfo> getGroupByNameAndType(String group_name,String group_type,def pid) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT id,group_name,a_time FROM ticket_price_schema_group WHERE group_name = ? and group_type = ? and flag!=?"
		if(!StringUtil.isEmpty(String.valueOf(pid))&&pid!=1){
			sql += " and pid=" + pid;
		}
		return dbpower.doQueryList(GroupInfo.class, sql,group_name,group_type,-1);
	}

	/**
	 * 删除一个分组  并将该分组下所有票类置为未分组 使用此方法需要自行处理事务
	 * @param id
	 */
	public void deleteGroup(Long id){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String upSchemaSql = "UPDATE ticket_price_schema SET group_id = -1 WHERE group_id = ?";
		String sql  ="UPDATE ticket_price_schema_group SET flag = -1 WHERE id = ?";
		dbpower.doUpdate(upSchemaSql, id);
		dbpower.doUpdate(sql, id);
	}

	public void addOrUpdateSchema(TicketPriceSchema schema) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		dbpower.doUpdateSerialize(schema, "id");
	}

	/**
	 * 删除一个票类下原有明细
	 * @param schema_id
	 */
	public void deleteSchemaOldItem(Long schema_id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "UPDATE ticket_price_schema_item SET flag = ?,delete_time = ? where flag != ? and schema_id = ?";
		dbpower.doUpdate(sql, -1,new Date(),-1,schema_id);
	}

	public SchemaWithItem getSchemaWithItemById(Long id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "select * from ticket_price_schema WHERE id = ?";
		SchemaWithItem schema = dbpower.doQueryUnique(SchemaWithItem.class, sql, id);
		if(schema != null) {
			schema.setItems(getSchemaChildItems(id));
		}
		return schema;
	}

	public List<SchemaChildItem> getSchemaChildItems(Long schemaId) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * FROM ticket_price_schema_item WHERE flag != ? and schema_id = ?";
		return dbpower.doQueryList(SchemaChildItem.class, sql, -1,schemaId);
	}

	public void addOrUpdateItem(TicketPriceSchemaItem item) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		dbpower.doUpdateSerialize(item, "id");
	}

	public void deleteItem(Long id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "UPDATE ticket_price_schema_item set flag = ? where id = ?";
		dbpower.doUpdate(sql, -1,id);
	}

	public List<GroupWithSchema> getAllSchemaGroup(def pid){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * from ticket_price_schema_group WHERE pid=? and flag != ?";
		List<GroupWithSchema> groups = dbpower.doQueryList(GroupWithSchema.class, sql,pid,-1);
		if(groups != null) {
			for(GroupWithSchema group : groups) {
				if (StringUtil.isEmpty(group.group_type)) {
					group.group_type = '-1';
				}
				group.setSchemas(getGroupSchema(group.getId()));
			}
		}
		List<SchemaWithItem> ungroupSchemas = getGroupSchema(-1);
		if(groups == null) {
			groups = new ArrayList<GroupWithSchema>();
		}
		GroupWithSchema ungroup = new GroupWithSchema();
		ungroup.setId(-1);
		ungroup.setGroup_name("未分组");
		ungroup.setSchemas(ungroupSchemas);
		groups.add(0, ungroup);
		return groups;
	}

	public List<GroupWithSchema> getAllSchemaGroup(Long storeId,String hallId,def pid){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * from ticket_price_schema_group WHERE pid=? and flag != ?";
		List<GroupWithSchema> groups = dbpower.doQueryList(GroupWithSchema.class, sql,pid,-1);
		if(groups != null) {
			for(GroupWithSchema group : groups) {
				group.setSchemas(getGroupSchema(group.getId()));
			}
		}
		List<SchemaWithItem> ungroupSchemas = getGroupSchema(-1);
		if(groups == null) {
			groups = new ArrayList<GroupWithSchema>();
		}
		GroupWithSchema ungroup = new GroupWithSchema();
		ungroup.setId(-1);
		ungroup.setGroup_name("未分组");
		ungroup.setSchemas(ungroupSchemas);
		groups.add(0, ungroup);

		String selSql = "SELECT id FROM hall_schema WHERE store_id = ? AND hall_id = ? AND schema_id = ? AND flag != ?";
		if(groups != null) {
			for(GroupWithSchema gp : groups) {
				if(gp.getSchemas() != null) {
					for(SchemaWithItem sm : gp.getSchemas()) {
						Long selid = dbpower.doQueryUniqueScalar(Long.class, selSql,storeId,hallId,sm.getId(),-1);
						if(selid != null) {
							sm.setSelected(true);
						}
					}
				}
			}
		}

		return groups;
	}

	public List<SchemaWithItem> getGroupSchema(Long groupId){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * FROM ticket_price_schema WHERE flag != ? and group_id = ?";
		List<SchemaWithItem> schemas = dbpower.doQueryList(SchemaWithItem.class, sql, -1,groupId);
		if(schemas != null) {
			for(SchemaWithItem schema : schemas) {
				schema.setItems(getSchemaChildItems(schema.getId()));
			}
		}
		return schemas;
	}

	public void changeSchemaGroup(Long schemaId,Long groupId) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "UPDATE ticket_price_schema SET group_id = ? WHERE id = ?";
		dbpower.doUpdate(sql, groupId,schemaId);
	}

	public void deleteSchema(Long id) {
		String sql  = "UPDATE ticket_price_schema SET flag = - 1 WHERE id = ?";
		DatabaseSource.getDatabasePower().doUpdate(sql, id);
	}

	public void schemaToTop(Long id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			UPDATE 
			ticket_price_schema a
			join
			(select max(sort_num)+1 as target_num from ticket_price_schema where flag != -1) b
			on 1=1
			set a.sort_num = b.target_num
			where a.id = ?
			''';
		dbpower.doUpdate(sql, id);
	}

	public void schemaToBottom(Long id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			UPDATE 
			ticket_price_schema a
			join
			(select min(sort_num)-1 as target_num from ticket_price_schema where flag != -1) b
			on 1=1
			set a.sort_num = b.target_num
			where a.id = ?
			''';
		dbpower.doUpdate(sql, id);
	}

	public void schemaIndexChange(Long src_id,Long tar_id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			update
			ticket_price_schema a
			join
			ticket_price_schema b
			on 1=1
			set a.sort_num = b.sort_num,b.sort_num = a.sort_num
			where a.id = ? and b.id = ?
			'''
		dbpower.doUpdate(sql, src_id,tar_id);
	}

	public void itemToTop(Long id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			UPDATE 
			ticket_price_schema_item a
			join
			(select max(sort_num)+1 as target_num from ticket_price_schema_item where flag != -1) b
			on 1=1
			set a.sort_num = b.target_num
			where a.id = ?
			''';
		dbpower.doUpdate(sql, id);
	}

	public void itemToBottom(Long id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			UPDATE 
			ticket_price_schema_item a
			join
			(select min(sort_num)-1 as target_num from ticket_price_schema_item where flag != -1) b
			on 1=1
			set a.sort_num = b.target_num
			where a.id = ?
			''';
		dbpower.doUpdate(sql, id);
	}

	public void itemIndexChange(Long src_id,Long tar_id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			update
			ticket_price_schema_item a
			join
			ticket_price_schema_item b
			on 1=1
			set a.sort_num = b.sort_num,b.sort_num = a.sort_num
			where a.id = ? and b.id = ?
			'''
		dbpower.doUpdate(sql, src_id,tar_id);
	}

	/**
	 * 获取某影城的全部票类
	 * @param schemaId
	 * @return
	 */
	public List<HallSchemaWithItem> getCinemaSchemas(Long schemaId) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			a.hall_id,
			b.*
			from
			hall_schema a
			join
			ticket_price_schema b
			on a.`schema_id` = b.id
			where 1=1
			and a.store_id = ?
			and a.flag != -1 
			and b.flag != -1
			'''
		List<HallSchemaWithItem> schemas = dbpower.doQueryList(HallSchemaWithItem.class, sql,schemaId);
		if(schemas != null) {
			for(HallSchemaWithItem schema : schemas) {
				schema.setItems(getSchemaChildItems(schema.getId()));
			}
		}
		return schemas;
	}

	/**
	 * 根据分组类型获取分组
	 * @param group_type
	 * @return
	 */
	public List<GroupInfo> getSchemas_group(String group_type,String group_name,def pid){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT id FROM ticket_price_schema_group WHERE group_type = ? and flag!=?"
		if(!StringUtil.isEmpty(String.valueOf(pid))&&pid!=1){
			sql += " and pid=" + pid;
		}
		List<GroupInfo> list = dbpower.doQueryList(GroupInfo.class, sql,group_type,-1);
		if (list == null || list.isEmpty()) {
			return new ArrayList<GroupInfo>();
		}
		return list;
	}

	/**
	 * 获取收银台分组下的票类
	 * @param schemaId
	 * @return
	 */
	public List<HallSchemaWithItem> getCinemaSchemas_counter(Long schemaId,def pid) {
		List<GroupInfo> list = getSchemas_group(TicketingsysConstant.counter_schema_group,TicketingsysConstant.counters_schema_group,pid);
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			a.hall_id,
			b.*
			from
			hall_schema a
			join
			ticket_price_schema b
			on a.`schema_id` = b.id
			where 1=1
			and a.store_id = ?
			and a.flag != -1 
			and b.flag != -1
            and b.group_id = ?
			'''
		List<HallSchemaWithItem> schemas = new ArrayList<HallSchemaWithItem>();
		//获取收银台类型的所有分组      还有没有做筛选
		for (l in list) {
			List<HallSchemaWithItem> schs = dbpower.doQueryList(HallSchemaWithItem.class, sql,schemaId,l.id);
			schemas.addAll(schs);
		}
		if(schemas != null) {
			for(HallSchemaWithItem schema : schemas) {
				schema.setItems(getSchemaChildItems(schema.getId()));
			}
		}
		return schemas;
	}

	/**
	 * 获取网售分组下的票类
	 * @param schemaId
	 * @return
	 */
	public List<HallSchemaWithItem> getCinemaSchemas_online(Long schemaId,def pid) {
		List<GroupInfo> list = getSchemas_group(TicketingsysConstant.online_schema_group,TicketingsysConstant.default_schema_group,pid);
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			a.hall_id,
			b.*
			from
			hall_schema a
			join
			ticket_price_schema b
			on a.`schema_id` = b.id
			where 1=1
			and a.store_id = ?
			and a.flag != -1 
			and b.flag != -1
            and b.group_id = ?
			'''
		List<HallSchemaWithItem> schemas = new ArrayList<HallSchemaWithItem>();
		//获取收银台类型的所有分组      还有没有做筛选
		for (l in list) {
			List<HallSchemaWithItem> schs = dbpower.doQueryList(HallSchemaWithItem.class, sql,schemaId,l.id);
			schemas.addAll(schs);
		}
		if(schemas != null) {
			for(HallSchemaWithItem schema : schemas) {
				schema.setItems(getSchemaChildItems(schema.getId()));
			}
		}
		return schemas;
	}

	/**
	 * 获取默认分组下的票类
	 * @param schemaId
	 * @return
	 */	
	public List<HallSchemaWithItem> getDefaultCinemaSchemas(Long schemaId,def pid) {
		List<GroupInfo> list = getGroupByName(TicketingsysConstant.default_schema_group,pid);
		if (list == null || list.isEmpty()) {
			return new ArrayList<HallSchemaWithItem>();
		}
		println "group_id__" + list.get(0).id + "__" + schemaId;
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			a.hall_id,
			b.*
			from
			hall_schema a
			join
			ticket_price_schema b
			on a.`schema_id` = b.id
			where 1=1
			and a.store_id = ?
			and a.flag != -1 
			and b.flag != -1
			and b.group_id = ?
			'''
		List<HallSchemaWithItem> schemas = dbpower.doQueryList(HallSchemaWithItem.class, sql,schemaId,list.get(0).id);
		if(schemas != null) {
			for(HallSchemaWithItem schema : schemas) {
				schema.setItems(getSchemaChildItems(schema.getId()));
			}
		}
		return schemas;
	}


	/**
	 * 从一个票类列表中匹配到排期可使用的票类
	 * @param plan
	 * @param cinemaSchemas
	 * @return
	 */
	public List<PlanSchema> matchPlanSchemas(CinemaPlan plan,CinemaPlanMovie pmovie,List<HallSchemaWithItem> cinemaSchemas) {
		List<PlanSchema> planSchemas = new ArrayList<PlanSchema>();
		try {
			for(HallSchemaWithItem schema : cinemaSchemas) {
				//判断影厅
				if(!plan.getHallId().equals(schema.getHall_id())) {
					//影厅不匹配
					continue ;
				}

				//判断周策略
				Calendar c = Calendar.getInstance(TimeZone.getDefault());
				c.setTime(new Date());
				int dayweek = c.get(Calendar.DAY_OF_WEEK);
				if(dayweek == 1) {
					dayweek = 7;
				}else {
					dayweek = dayweek - 1;
				}
				String dayweekStr = ""+dayweek;

				if(schema.getWeek_rule() != null && !"".equals(schema.getWeek_rule()) && !"-".equals(schema.getWeek_rule())) {
					//说明设置了周策略
					String[] supps = schema.getWeek_rule().split(",");
					if(!supps.contains(dayweekStr)) {
						//周策略不适用
						continue;
					}
				}
				
				//判断时间策略
				if(schema.getTime_rule() != null && !"".equals(schema.getTime_rule()) && !"-".equals(schema.getTime_rule())) {
					//说明设置了时间策略
					SimpleDateFormat datetimesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat hourmintssdf = new SimpleDateFormat("HH:mm");
					String[] timeRuleSE = schema.getTime_rule().split(",");
					String currentTimeStr = hourmintssdf.format(new Date());
					String stimeStr = timeRuleSE[0];
					String etimeStr = timeRuleSE[1];

					Date currentTime = datetimesdf.parse("2000-01-01 "+currentTimeStr+":00");
					Date stime = datetimesdf.parse("2000-01-01 "+stimeStr+":00");
					Date etime = datetimesdf.parse("2000-01-01 "+etimeStr+":00");

					if(currentTime.getTime() < stime.getTime() || currentTime.getTime() > etime.getTime()) {
						//时间策略不匹配
						continue;
					}
				}
				String movieDimensional = pmovie.getMovieDimensional();// 影片类型
				String movieSize = pmovie.getMovieSize(); // 影片尺寸
				Map<String,SchemaChildItem> itemMap = getSchemaMap(schema.getItems());
				SchemaChildItem item = null;
				// 第一种全查
				item = itemMap.get((movieDimensional + "__" + movieSize).toLowerCase());
				if (item == null) {
					item = itemMap.get((movieDimensional + "__不限").toLowerCase());
				}
				if (item == null) {
					item = itemMap.get(("不限__" + movieSize).toLowerCase());
				}
				// 查询所有不限的
				if (item == null) {
					item = itemMap.get(( "不限__不限").toLowerCase());
				}
				if (item == null) {
					continue;
				}
				PlanSchema matchSchema = new PlanSchema();
				matchSchema.setPrice(item.getPrice())
				matchSchema.setA_time(schema.getA_time())
				matchSchema.setMonth_rule(schema.getMonth_rule())
				matchSchema.setTime_rule(schema.getTime_rule())
				matchSchema.setWeek_rule(schema.getWeek_rule())
				matchSchema.setBy_permission(schema.getBy_permission())
				matchSchema.setSchema_status(schema.getSchema_status())
				matchSchema.setSort_num(schema.getSort_num())
				matchSchema.setSchema_name(schema.getSchema_name())
				matchSchema.setGroup_id(schema.getGroup_id())
				matchSchema.setId(schema.getId());
				matchSchema.setItem_id(item.getId());
				matchSchema.setIs_custom(item.getIs_custom())
				planSchemas.add(matchSchema);
			}
		} catch (anf) {
			logger.error("",anf);
		}
		return planSchemas;
	}
	/**
	 * 使用网售规则进行二次筛选
	 * @return price价格
	 */
	public def filterPriceRlues(TicketPriceDto ticketPriceDto,def pid) {
		//def pid=GlobalHolder.getProj().getId();
		//def pid = 1;
		def apstatus = '20';//状态20为网售规则已开启状态
		List<TicketSalesRules> rules = ticketSalesRulesService.getAll(pid, apstatus)//查询所有可用的网售规则
		for(TicketSalesRules item : rules) {
			//门店限制
			String store_ids = ticketPriceDto.store_limit;
			if(!StringUtil.isEmpty(store_ids)&&!StringUtil.isEmpty(item.store_type)){
				Boolean flag1 = true;
				String [] store_id = store_ids.split(',');
				String [] store_type = item.store_type.split(',');
				int t=0;
				for (int i=0;i<store_id.length;i++) {
					for(int j=0;j<store_type.length;j++){
						if (store_id[i].equals(store_type[j])) {
							flag1 = false;
							t++;
						}
					}
				}
				if (flag1||t!=store_id.length) {
					//门店不匹配
					if(item.id==new BigInteger(22) && ticketPriceDto.movie_limit=='001100942009') {
						logger.info('风声电影22因为影城限制被跳过')
					}
					continue;
				}
			}
			String hall_id = ticketPriceDto.hall_limit;
			if(!StringUtil.isEmpty(hall_id)&&!StringUtil.isEmpty(item.hall_id)){
				Boolean flag3 = true;
				String [] hall_ids = item.hall_id.split(",");//6-52,6-51
				for(int i = 0 ; i<hall_ids.length;i++){
					String s = hall_ids[i].split("-")[1];
					if(s.equals(hall_id)){
						flag3 = false;
						break;
					}
				}
				if (flag3) {
					//影厅不匹配
					if(item.id==new BigInteger(22) && ticketPriceDto.movie_limit=='001100942009') {
						logger.info('风声电影22因为影厅限制被跳过')
					}
					continue;
				}
			}
			//影片限制
			String movie_ids = ticketPriceDto.movie_limit;
			if (!StringUtil.isEmpty(movie_ids)&&!StringUtil.isEmpty(item.movie_type)) {
				Boolean flag2 = true;
				String[] movie_id = movie_ids.split(',');
				String[] movie_type = item.movie_type.split(',');
				int t=0;
				for (int i=0;i<movie_id.length;i++) {
					for(int j=0;j<movie_type.length;j++){
						if (movie_id[i].equals(movie_type[j])) {
							flag2=false;
							t++;
						}
					}
				}
				if (flag2||t!=movie_id.length) {
					//影片不匹配
					if(item.id==new BigInteger(22) && ticketPriceDto.movie_limit=='001100942009') {
						logger.info('风声电影22因为影片限制被跳过')
					}
					continue;
				}
			}
			//判断时间策略
			if (item.time_type.equals('w')) {//按周分时设置
				String [] time_value = item.time_streg.split('/');
				Boolean flag = true;
				String []time_run = ticketPriceDto.running_time.split(',');//数据类型为1,14:00(周1下午2点)
				for(int i=0;i<time_value.length;i++){
					String [] time_value1 = time_value[i].split(';');//1,2,3,4;00:00;23:59类型时间
					String [] time_value3 = time_value1[0].split(',');
					for (int j=0;j<time_value3.length;j++) {
						if(time_value3[j].equals(time_run[0])){
							SimpleDateFormat hourmintssdf = new SimpleDateFormat("HH:mm");//时间格式转换
							Date start_time= hourmintssdf.parse(time_value1[1]);
							Date end_time=hourmintssdf.parse(time_value1[2]);
							Date curt_time=hourmintssdf.parse(time_run[1]);
							if (start_time.compareTo(curt_time)<=0&&end_time.compareTo(curt_time)>=0){
								flag=false;
								break;
							}
						}
					}
				}
				if (flag) {
					if(item.id==new BigInteger(22) && ticketPriceDto.movie_limit=='001100942009') {
						logger.info('风声电影22因为时间限制被跳过')
					}
					continue;
				}
			}

			//尺寸，影片类型判断
			BigDecimal price = null;//价格变动值
			String price_type = '';//价格类型
			String movie_price_streg = item.price_streg;
			//logger.info('取得的匹配价格策略为=' + JackSonBeanMapper.toJson(item));
			if (!StringUtil.isEmpty(movie_price_streg)) {
				Boolean status = true;
				String [] movie_type = movie_price_streg.split('/');//w,2D,普通,700/d,All,中国巨幕,8000
				for (int i=0;i<movie_type.length;i++) {
					String [] movie_type1 = movie_type[i].split(',');
					if (ticketPriceDto.getMovie_size().equals(movie_type1[2])&&ticketPriceDto.getMovie_dimensional().equals(movie_type1[1])) {
						price = new BigDecimal(movie_type1[3]);
						price_type = movie_type1[0];
						status = false;
						break;
					}else if (ticketPriceDto.getMovie_size().equals(movie_type1[2])&&movie_type1[1].equals('All')) {//当网售规则的影票类型为不限时
						price = new BigDecimal(movie_type1[3]);
						price_type = movie_type1[0];
						status = false;
						break;
					}else if (ticketPriceDto.getMovie_size().equals(movie_type1[2])&&movie_type1[1].equals('不限')) {//当网售规则的影票类型为不限时
						price = new BigDecimal(movie_type1[3]);
						price_type = movie_type1[0];
						status = false;
						break;
					}else if (movie_type1[2].equals('不限')&&ticketPriceDto.getMovie_dimensional().equals(movie_type1[1])) {
						price = new BigDecimal(movie_type1[3]);
						price_type = movie_type1[0];
						status = false;
						break;
					}else if(movie_type1[2].equals('不限')&&movie_type1[1].equals('不限')){
						price = new BigDecimal(movie_type1[3]);
						price_type = movie_type1[0];
						status = false;
						break;
					}
				}
				if (status) {
					//尺寸和类型不匹配
					logger.info('unmatched:' + ticketPriceDto.movie_limit + '------' + ticketPriceDto.movie_dimensional)
					continue;
				}
			}

			def ret_price = ticketPriceDto.price;
			//计算价格
			if (price!=null&&!StringUtil.isEmpty(price_type)) {
				if (price_type.equals('w')) {
					//BigDecimal new_price=price.divide(100,2,RoundingMode.HALF_UP);
					BigDecimal old_price = (ticketPriceDto.price);
					ret_price = old_price.add(price);
				} else if (price_type.equals('d')) {
					BigDecimal new_price = price.divide(100,2,RoundingMode.HALF_UP);
					BigDecimal old_price = ticketPriceDto.price;
					ret_price = old_price.multiply(new_price).setScale(2,BigDecimal.ROUND_HALF_DOWN);
					ret_price = ret_price.divide(100,2,RoundingMode.HALF_UP);
				} else if (price_type.equals('s')) {	//制定销售价格
					//BigDecimal new_price=price.divide(100,2,RoundingMode.HALF_UP);
					BigDecimal new_price = price;
					ret_price = new_price;
				} else if (price_type.equals('y')) {//最低价格变动
					ret_price = ticketPriceDto.min_price.add(price);
				}
				if(item.allow_low_price == 0){//保护低于最低票价
					if(ret_price.compareTo(ticketPriceDto.min_price)<0) {
						ret_price = ticketPriceDto.min_price;
					}
				}
			}
			return ret_price;
		}
	}
	public def filter_price_with_rules(def pid,TicketPriceDto ticketPriceDto,def market_info){
		logger.info("==================ticketPriceDto==============="+JackSonBeanMapper.toJson(ticketPriceDto));
		for(MarketInfoDto infodto : market_info) {
			def ret_price = ticketPriceDto.price;
			//时间策略判断
			String s_time = infodto.start_time;//格式为yyyy-MM-dd HH:mm:ss
			String e_time = infodto.end_time;
			int min = s_time.compareTo(ticketPriceDto.currt_running_time+":00");
			int max = e_time.compareTo(ticketPriceDto.currt_running_time+":00");
			if(min>0||max<0){
				continue;//时间策略不匹配
			}
			for(MarketTicketRuleDto item:infodto.market){
				if(ticketPriceDto.status.equals('10')){
					if (item.is_mc_rule.equals('0')||StringUtil.isEmpty(item.is_mc_rule)) {//结算时，显示仅会员参与的活动规则
						continue;
					}else if(item.is_mc_rule.equals('1')){
						//会员限制
						if(!StringUtil.isEmpty(item.member_card_rule)&&!StringUtil.isEmpty(ticketPriceDto.mch_id)){
							boolean flag = true;
							String [] member_card = item.member_card_rule.split(",");
							String [] mch_ids = ticketPriceDto.mch_id.split(",");
							for (int i = 0;i < member_card.length;i++) {
								for(int j = 0;j<mch_ids.length;j++ ){
									if (member_card[i].equals(mch_ids[j])) {
										flag = false;
										break;
									}
								}
							}
							if (flag) {
								continue;//会员卡不匹配
							}
						}
					}
				}else {
					if(item.is_mc_rule.equals('1')){//展示时，不限制用户是否为会员
						continue;
					}
				}

				//影城限制
				String store_ids=ticketPriceDto.store_limit;
				if(!StringUtil.isEmpty(store_ids)&&!StringUtil.isEmpty(item.store_id)){
					Boolean flag1=true;
					String [] store_id=store_ids.split(',');
					String [] store_type=item.store_id.split(',');
					int t=0;
					for (int i=0;i<store_id.length;i++) {
						for(int j=0;j<store_type.length;j++){
							if (store_id[i].equals(store_type[j])) {
								flag1=false;
								t++;
							}
						}
					}
					if (flag1||t!=store_id.length) {
						//门店不匹配
						continue;
					}
				}
				//限制影厅
				String hall_ids =ticketPriceDto.hall_limit;
				if (StringUtil.isEmpty(hall_ids)) {
					continue;
				}
				if(!StringUtil.isEmpty(item.hall_id)){
					Boolean flag_1=true;
					String [] hall_id=hall_ids.split(',');
					String [] hall_type=item.hall_id.split(',');//3-400,3-438,3-439
					int t=0;
					for (int i=0;i<hall_id.length;i++) {
						for(int j=0;j<hall_type.length;j++){//3-400
							String [] hall_id2 = hall_type[j].split("-");//3 400
							if (store_ids.equals(hall_id2[0])&&hall_id[i].equals(hall_id2[1])) {
								flag_1=false;
								t++;
							}
						}
					}
					if (flag_1||t!=hall_id.length) {
						//影厅不匹配
						continue;
					}
				}
				//影片限制
				String movie_ids=ticketPriceDto.movie_limit;
				if (!StringUtil.isEmpty(movie_ids)&&!StringUtil.isEmpty(item.desi_movie)) {
					Boolean flag2=true;
					String[] movie_id=movie_ids.split(',');
					String[] movie_type=item.desi_movie.split(',');
					int t=0;
					for (int i=0;i<movie_id.length;i++) {
						for(int j=0;j<movie_type.length;j++){
							if (movie_id[i].equals(movie_type[j])) {
								flag2=false;
								t++;
							}
						}
					}
					if (flag2||t!=movie_id.length) {
						//影片不匹配
						continue;
					}
				}
				//判断时间策略
				if (item.time_type.equals('w')) {//按周分时设置
					String [] time_value=item.spru_time_spec.split('/');
					Boolean flag=true;
					String []time_run=ticketPriceDto.running_time.split(',');//数据类型为1,14:00(周1下午2点)
					for(int i=0;i<time_value.length;i++){
						String [] time_value1=time_value[i].split(';');//1,2,3,4;00:00;23:59类型时间
						String [] time_value3=time_value1[0].split(',');
						for (int j=0;j<time_value3.length;j++) {
							if(time_value3[j].equals(time_run[0])){
								SimpleDateFormat hourmintssdf = new SimpleDateFormat("HH:mm");//时间格式转换
								Date start_time= hourmintssdf.parse(time_value1[1]);
								Date end_time=hourmintssdf.parse(time_value1[2]);
								Date curt_time=hourmintssdf.parse(time_run[1]);
								if (start_time.compareTo(curt_time)<=0&&end_time.compareTo(curt_time)>=0){
									flag=false;
									break;
								}
							}
						}
					}
					if (flag) {
						continue;
					}
				}else if(item.time_type.equals("d")){//按天时分策略判断
					String time_values1 = item.spru_time_spec;//格式为yyyy-MM-dd HH:mm
					boolean flag7 = true;
					if(!StringUtil.isEmpty(time_values1)){
						String [] time_values2 = time_values1.split("/");//2018-12-06;2018-12-18;06:00;19:00
						for(int i = 0 ;i < time_values2.length;i++){
							String [] time_values3 = time_values2[i].split(";")
							String start_time = time_values3[0]+" "+time_values3[2];
							String end_time = time_values3[1]+" "+time_values3[3];
							int min1 = start_time.compareTo(ticketPriceDto.currt_running_time);
							int max1 = end_time.compareTo(ticketPriceDto.currt_running_time);
							if(min1<0||max1>0){
								flag7 = false;//满足一条时间策略就返回
								break;
							}
						}
					}
					if(flag7){
						continue;//时间策略不匹配
					}
				}
				//影片类型判断
				BigDecimal price=null;//价格变动值
				String price_type='';//价格类型
				String movie_price_streg=item.mc_p_streg;
				if (!StringUtil.isEmpty(movie_price_streg)) {
					Boolean status=true;
					String [] movie_type=movie_price_streg.split('/');//w,2D,700/d,All,8000
					for (int i=0;i<movie_type.length;i++) {
						String [] movie_type1=movie_type[i].split(',');
						if (movie_type1[1].equals('不限')||ticketPriceDto.getMovie_dimensional().equals(movie_type1[1])) {
							price = new BigDecimal(movie_type1[2]);
							price_type= movie_type1[0];
							status = false;
							break;
						}
					}
					if (status) {
						//类型不匹配
						continue;
					}
				}

				//计算价格
				if (price!=null&&!StringUtil.isEmpty(price_type)) {
					if (price_type.equals('w')) {
						//BigDecimal new_price=price.divide(100,2,RoundingMode.HALF_UP);
						BigDecimal old_price = (ticketPriceDto.price);
						ret_price = old_price.add(price);
					} else if (price_type.equals('d')) {
						BigDecimal new_price = price.divide(100,2,RoundingMode.HALF_UP);
						BigDecimal old_price = ticketPriceDto.price;
						ret_price = old_price.multiply(new_price).setScale(2,BigDecimal.ROUND_HALF_DOWN);
						ret_price = ret_price.divide(100,2,RoundingMode.HALF_UP);
					} else if (price_type.equals('s')) {
						//BigDecimal new_price=price.divide(100,2,RoundingMode.HALF_UP);
						BigDecimal new_price = price;
						ret_price = new_price;
					} else if (price_type.equals('y')) {//最低价格变动
						ret_price = ticketPriceDto.min_price.add(price);
					}
				}
			}
			logger.info("==================价格变动值为==============="+ret_price);
			return ret_price;
		}
	}

	public SchemaChildItem getSchemaItemById(Long itemId) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * FROM ticket_price_schema_item WHERE id = ?";
		return dbpower.doQueryUnique(SchemaChildItem.class, sql,itemId);
	}

	private Map<String,SchemaChildItem> getSchemaMap(List<SchemaChildItem> items){
		Map<String,SchemaChildItem>  rules = new HashMap<String,SchemaChildItem>();
		for (SchemaChildItem item : items) {
			rules.put((item.getMovie_dimensional() + "__" + item.getMovie_size()).toLowerCase(), item);
		}
		return rules;
	}
	
	
	
	public static void main(String[] argx) {
		SchemaService ss = new SchemaService();
		String plan_s = '{"id":"3978201812220054","movieInfo":[{"code":"0","msg":"success","rmCode":null,"rmMsg":null,"cineMovieId":null,"cineMovieNum":"012102102018","movieName":"龙猫（数字）","movieLanguage":"日语","movieSubtitle":null,"movieFormat":null,"movieDimensional":"普通","movieSize":null,"joinStartTime":null,"joinEndTime":null,"success":true}],"hallId":"0000000000000003","hallName":"3号激光厅","startTime":"2018-12-22 17:35:00","endTime":"2018-12-22 19:02:00","priceType":null,"price":"40.00","marketPrice":"40.00","lowestPrice":"20.00","seatTotalNum":"0","seatAvailableNum":"0","allowBook":"1","cineUpdateTime":null,"cinePlayId":null,"partnerPrice":"40.00","priceStrategySnapshootId":null,"priceActivitySnapshootId":null,"businessDate":"2018-12-22","settlePrice":null}'
		CinemaPlan plan = JackSonBeanMapper.fromJson(plan_s, CinemaPlan.class);
		String pmovie_str ='{"code":"0","msg":"success","rmCode":null,"rmMsg":null,"cineMovieId":null,"cineMovieNum":"012102102018","movieName":"龙猫（数字）","movieLanguage":"日语","movieSubtitle":null,"movieFormat":null,"movieDimensional":"普通","movieSize":null,"joinStartTime":null,"joinEndTime":null,"success":true}'
		CinemaPlanMovie pmovie = JackSonBeanMapper.fromJson(pmovie_str, CinemaPlanMovie.class);

		String cinemaSchemas_str ='[{"id":1,"hall_id":"1","group_id":1,"schema_name":"网售标准票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"1,2,3,4,5,6,7","time_rule":"00:00,23:59","month_rule":"-","a_time":1541383483000,"items":[{"id":3,"schema_id":1,"movie_dimensional":"2D","movie_size":"普通","price":3000,"sort_num":1,"a_time":1541414881000,"is_custom":false,"priceYuan":"30.00"},{"id":4,"schema_id":1,"movie_dimensional":"3D","movie_size":"普通","price":3500,"sort_num":2,"a_time":1541414881000,"is_custom":false,"priceYuan":"35.00"}]},{"id":1,"hall_id":"2","group_id":1,"schema_name":"网售标准票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"1,2,3,4,5,6,7","time_rule":"00:00,23:59","month_rule":"-","a_time":1541383483000,"items":[{"id":3,"schema_id":1,"movie_dimensional":"2D","movie_size":"普通","price":3000,"sort_num":1,"a_time":1541414881000,"is_custom":false,"priceYuan":"30.00"},{"id":4,"schema_id":1,"movie_dimensional":"3D","movie_size":"普通","price":3500,"sort_num":2,"a_time":1541414881000,"is_custom":false,"priceYuan":"35.00"}]},{"id":1,"hall_id":"3","group_id":1,"schema_name":"网售标准票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"1,2,3,4,5,6,7","time_rule":"00:00,23:59","month_rule":"-","a_time":1541383483000,"items":[{"id":3,"schema_id":1,"movie_dimensional":"2D","movie_size":"普通","price":3000,"sort_num":1,"a_time":1541414881000,"is_custom":false,"priceYuan":"30.00"},{"id":4,"schema_id":1,"movie_dimensional":"3D","movie_size":"普通","price":3500,"sort_num":2,"a_time":1541414881000,"is_custom":false,"priceYuan":"35.00"}]},{"id":1,"hall_id":"4","group_id":1,"schema_name":"网售标准票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"1,2,3,4,5,6,7","time_rule":"00:00,23:59","month_rule":"-","a_time":1541383483000,"items":[{"id":3,"schema_id":1,"movie_dimensional":"2D","movie_size":"普通","price":3000,"sort_num":1,"a_time":1541414881000,"is_custom":false,"priceYuan":"30.00"},{"id":4,"schema_id":1,"movie_dimensional":"3D","movie_size":"普通","price":3500,"sort_num":2,"a_time":1541414881000,"is_custom":false,"priceYuan":"35.00"}]},{"id":1,"hall_id":"5","group_id":1,"schema_name":"网售标准票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"1,2,3,4,5,6,7","time_rule":"00:00,23:59","month_rule":"-","a_time":1541383483000,"items":[{"id":3,"schema_id":1,"movie_dimensional":"2D","movie_size":"普通","price":3000,"sort_num":1,"a_time":1541414881000,"is_custom":false,"priceYuan":"30.00"},{"id":4,"schema_id":1,"movie_dimensional":"3D","movie_size":"普通","price":3500,"sort_num":2,"a_time":1541414881000,"is_custom":false,"priceYuan":"35.00"}]},{"id":4,"hall_id":"0000000000000001","group_id":3,"schema_name":"测试票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"4,6","time_rule":"00:00,23:59","month_rule":"-","a_time":1545281858000,"items":[{"id":8,"schema_id":4,"movie_dimensional":"不限","movie_size":"不限","price":10000,"sort_num":1,"a_time":1545414283000,"is_custom":false,"priceYuan":"100.00"}]},{"id":4,"hall_id":"0000000000000002","group_id":3,"schema_name":"测试票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"4,6","time_rule":"00:00,23:59","month_rule":"-","a_time":1545281858000,"items":[{"id":8,"schema_id":4,"movie_dimensional":"不限","movie_size":"不限","price":10000,"sort_num":1,"a_time":1545414283000,"is_custom":false,"priceYuan":"100.00"}]},{"id":4,"hall_id":"0000000000000003","group_id":3,"schema_name":"测试票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"4,6","time_rule":"00:00,23:59","month_rule":"-","a_time":1545281858000,"items":[{"id":8,"schema_id":4,"movie_dimensional":"不限","movie_size":"不限","price":10000,"sort_num":1,"a_time":1545414283000,"is_custom":false,"priceYuan":"100.00"}]},{"id":4,"hall_id":"0000000000000004","group_id":3,"schema_name":"测试票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"4,6","time_rule":"00:00,23:59","month_rule":"-","a_time":1545281858000,"items":[{"id":8,"schema_id":4,"movie_dimensional":"不限","movie_size":"不限","price":10000,"sort_num":1,"a_time":1545414283000,"is_custom":false,"priceYuan":"100.00"}]},{"id":4,"hall_id":"0000000000000005","group_id":3,"schema_name":"测试票","sort_num":0,"schema_status":"00","by_permission":false,"week_rule":"4,6","time_rule":"00:00,23:59","month_rule":"-","a_time":1545281858000,"items":[{"id":8,"schema_id":4,"movie_dimensional":"不限","movie_size":"不限","price":10000,"sort_num":1,"a_time":1545414283000,"is_custom":false,"priceYuan":"100.00"}]}]'
		List<HallSchemaWithItem> cinemaSchemas = JackSonBeanMapper.jsonToList(cinemaSchemas_str, HallSchemaWithItem.class);
		List<PlanSchema> list = ss.matchPlanSchemas(plan, pmovie, cinemaSchemas);
		println JackSonBeanMapper.toJson(list);
		println list.size();
		//		"".equalsIgnoreCase(nu'l)

		//		pmovie__//		plan__
		//pmovie__{"id":"317662","movieInfo":[{"code":"0","msg":"success","rmCode":null,"rmMsg":null,"cineMovieId":"49","cineMovieNum":"001101912016","movieName":"设计未来(编码测试1)","movieLanguage":"德语","movieSubtitle":"中文","movieFormat":"数字","movieDimensional":"3D","movieSize":"普通","joinStartTime":"","joinEndTime":"","success":true}],"hallId":"439","hallName":"爱在西元前厅","startTime":"2018-09-14 20:20:00","endTime":"2018-09-14 22:15:00","priceType":"1","price":"60","marketPrice":"60","lowestPrice":"30","seatTotalNum":"141","seatAvailableNum":"141","allowBook":"1","cineUpdateTime":"2018-09-13 09:27:27","cinePlayId":"95585","partnerPrice":null,"priceStrategySnapshootId":null,"priceActivitySnapshootId":null,"businessDate":"2018-09-14","settlePrice":null}
		//cinemaSchemas__{"id":"317662","movieInfo":[{"code":"0","msg":"success","rmCode":null,"rmMsg":null,"cineMovieId":"49","cineMovieNum":"001101912016","movieName":"设计未来(编码测试1)","movieLanguage":"德语","movieSubtitle":"中文","movieFormat":"数字","movieDimensional":"3D","movieSize":"普通","joinStartTime":"","joinEndTime":"","success":true}],"hallId":"439","hallName":"爱在西元前厅","startTime":"2018-09-14 20:20:00","endTime":"2018-09-14 22:15:00","priceType":"1","price":"60","marketPrice":"60","lowestPrice":"30","seatTotalNum":"141","seatAvailableNum":"141","allowBook":"1","cineUpdateTime":"2018-09-13 09:27:27","cinePlayId":"95585","partnerPrice":null,"priceStrategySnapshootId":null,"priceActivitySnapshootId":null,"businessDate":"2018-09-14","settlePrice":null}
		//planSchemal
	}
}
