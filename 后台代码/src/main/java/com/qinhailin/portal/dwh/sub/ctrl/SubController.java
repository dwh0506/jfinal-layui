package com.qinhailin.portal.dwh.sub.ctrl;

import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.model.MqttSub;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.Feedback;
import com.qinhailin.portal.dwh.sub.service.SubService;
@ControllerBind(path="/portal/dwh/sub")
public class SubController extends BaseController {
	@Inject
	private SubService subservice;
	
    public void index() {
    	render("index.html");
    }
    public void list() {
		Record record = getAllParamsToRecord();//record存放查询条件  自动获取前端查询对象  存放到record
		
		renderJson(subservice.page(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10), record));
		
	}
    public void add() {
    	render("edit.html");
    }
    public void edit() {
   	//带参方式portal/dwh/sub/edit/id值，restful风格
   	    //String id=getPara(0);
    	
   	    String id=get(0);
    	MqttSub mqttSub=(MqttSub) subservice.findById(id);
    	setAttr("mqttSub",mqttSub);
    	render("edit.html");
    	
    }
    public void saveOrUpdate() {
    	
    	MqttSub mqttSub=getModel(MqttSub.class,"");//model方式接受表单参数的方法
    	//MqttSub mqttSub=getBean(MqttSub.class,"");
    	if(mqttSub.getId()==null) {
    		mqttSub.save();//执行insert操作，并且能够直接insert后的自增id
    	}else {
    		mqttSub.update();//执行的update操作
    	}
    	setAttr("mqttSub",mqttSub);
    	//setAttr是set的一种形式
    	render("edit.html");
    	
    }
 public void saveOrUpdate1() {
    	
	String id=get(0);
  	MqttSub mqttSub=(MqttSub) subservice.findById(id);
  	setAttr("mqttSub",mqttSub);
    mqttSub.update();//执行的update操作
    	
    	
    	//setAttr是set的一种形式
    	render("edit.html");
    	
    }
    public void delete() {
		subservice.deleteByIds(getIds());
		renderJson(Feedback.success());
	}

    
}
