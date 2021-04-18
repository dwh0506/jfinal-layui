package com.qinhailin.portal.dwh.msg.ctrl;

import com.jfinal.aop.Inject;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.BaseController;
import com.qinhailin.common.routes.ControllerBind;
import com.qinhailin.common.vo.Feedback;
import com.qinhailin.portal.client.service.ClientService;
import com.qinhailin.portal.dwh.msg.service.MsgService;
@ControllerBind(path="/portal/dwh/msg")
public class MSgController extends BaseController {
	@Inject
	MsgService msgservice;
    public void index() {
    	render("index.html");
    }
    public void list() {
		Record record = getAllParamsToRecord();//record存放查询条件  自动获取前端查询对象  存放到record
		
		renderJson(msgservice.page(getParaToInt("pageNumber", 1), getParaToInt("pageSize", 10), record));
		
	}
    public void delete() {
    	msgservice.deleteByIds(getIds());
		renderJson(Feedback.success());
	}
}
