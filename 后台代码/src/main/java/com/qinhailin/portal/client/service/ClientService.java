package com.qinhailin.portal.client.service;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.service.BaseService;

import com.qinhailin.common.model.MqttClient;
import com.qinhailin.common.vo.Grid;

public class ClientService extends BaseService {
    private MqttClient clientDao=new MqttClient().dao();
	@Override
	public Model<?> getDao() {
		// TODO Auto-generated method stub
		return clientDao;
	}
	
	public Grid page(int pageNumber, int pageSize, Record record) {
		Record rd = new Record();//数据库记录  类似于map对象 创建动态sql来进行查询
		String sql="select * from mqtt_client";
		rd.set("clientid like", record.getStr("clientid"));//record里面是前端传过来的具体的某一个查询条件的值
		rd.set("created like", record.getStr("created"));//re是动态的生成动态语句的where部分  key是操作符的前一部分比如大于小于的前一部分  value是后一部分
		rd.set("state =", record.getStr("state"));
		return queryForList(sql,pageNumber, pageSize, rd, null);
	}
}