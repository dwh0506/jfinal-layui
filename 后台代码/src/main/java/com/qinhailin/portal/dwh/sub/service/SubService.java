package com.qinhailin.portal.dwh.sub.service;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.MqttClient;
import com.qinhailin.common.model.MqttSub;
import com.qinhailin.common.vo.Grid;

public class SubService extends BaseService {
	private MqttSub subDao=new MqttSub().dao();
	@Override
	public Model<?> getDao() {
		// TODO Auto-generated method stub
		return subDao;
	}
	public Grid page(int pageNumber, int pageSize, Record record) {
		Record rd = new Record();//数据库记录  类似于map对象 创建动态sql来进行查询
		//String sql="select * from mqtt_sub";
		rd.set("clientid like", record.getStr("clientid"));//record里面是前端传过来的具体的某一个查询条件的值
		rd.set("topic like", record.getStr("topic"));//re是动态的生成动态语句的where部分  key是操作符的前一部分比如大于小于的前一部分  value是后一部分
		rd.set("qos =", record.getStr("qos"));
		//return queryForList(sql,pageNumber, pageSize, rd, null);
		return queryForListByRecord(pageNumber,pageSize,rd,"order by created desc");//按时间排序  不需要sql语句
	}
}
