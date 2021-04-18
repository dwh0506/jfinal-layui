/**
 * Copyright 2019 覃海林(qinhaisenlin@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package com.qinhailin.portal.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.SysFunction;
import com.qinhailin.common.vo.Grid;
import com.qinhailin.common.vo.TreeNode;

/**
 * 功能
 * 
 * @author qinhailin
 *
 */
public class SysFuncService extends BaseService {

	private SysFunction dao = new SysFunction().dao();
	
	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.BaseService#getDao()
	 */
	@Override
	public Model<?> getDao() {
		return dao;
	}
	
	
	public Grid page(int pageNumber, int pageSize, Record record) {
		return queryForListEq(pageNumber, pageSize, record, " order by func_type,order_no asc");
	}


	/**
	 * 新增功能同时授权给超级管理员
	 * @param entity
	 * @return
	 */
	public boolean saveEntity(SysFunction entity) {
		if(isExist(entity.getId())) {
			return false;
		}
		//系统权限
		Db.update(Db.getSql("core.saveRoleFunction"), "sys_" + entity.getId(), entity.getId(), "sys");
		//超级管理员权限
		Db.update(Db.getSql("core.saveRoleFunction"), "superadmin_" + entity.getId(), entity.getId(), "superadmin");
		return entity.save();
	}

	@Override
	public boolean deleteById(String id) {
		deleteRoleFuncByFunctionId(id);
		return dao.deleteById(id);
	}
	
	@Override
	public void deleteByIds(List<String> ids){
		Object[][] paras=new Object[ids.size()][1];
		for(int i=0;i<ids.size();i++) {
			paras[i][0]=ids.get(i);
			deleteRoleFuncByFunctionId(ids.get(i));
		}
		String sql="delete from "+getTable()+" where id=?";
		Db.batch(sql, paras, 100);
	}
	
	public boolean isExist(String id) {
		if (findById(id)!=null) {
			return true;
		}
		return false;
	}

	private void deleteRoleFuncByFunctionId(String functionId) {
		String sql = "delete from sys_role_function where function_id=?";
		Db.update(sql, functionId);
	}

	public Collection<TreeNode> getFunctionTree(String treeNodeId) {
		List<Record> list = queryForList(Db.getSql("core.getFunctionTree"), treeNodeId);
		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Record func : list) {

			TreeNode node = new TreeNode();
			if (func.getStr("icon") != null && func.getStr("icon").trim().length() > 0) {
				node.setIcon("");
			}
			node.setId(func.getStr("id"));
			node.setPid(func.getStr("parent_code"));
			node.setText(func.getStr("func_name"));
			node.setSpread(0==func.getInt("spread")?true:false);
			node.setUrl(func.getStr("link_page"));

			Collection<TreeNode> children = this.getFunctionTree(func.getStr("id"));
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}

	public Collection<TreeNode> getUserFunctionTree(String userCode, String treeNodeId) {
		String sql=Db.getSql("core.getUserFunctionTree");
		List<Record> list = Db.find(sql, userCode, treeNodeId);

		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		for (int i = 0; i < list.size(); i++) {
			Record func = list.get(i);
			TreeNode node = new TreeNode();

			node.setId(func.getStr("id"));
			node.setPid(func.getStr("parent_code"));
			node.setText(func.getStr("func_name"));
			node.setUrl(func.getStr("link_page"));
			node.setIcon(func.getStr("icon"));
			node.setSpread(0==func.getInt("spread")?true:false);
			Collection<TreeNode> children = this.getUserFunctionTree(userCode, func.getStr("id"));
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}

	public Collection<TreeNode> getRoleFunctionTree(String roleCode, String treeNodeId) {
		String sql=Db.getSql("core.getRoleFunctionTree");
		List<Record> list = Db.find(sql, roleCode, treeNodeId);

		Collection<TreeNode> nodes = new ArrayList<TreeNode>();
		for (int i = 0; i < list.size(); i++) {
			Record func = list.get(i);
			TreeNode node = new TreeNode();

			node.setId(func.getStr("id"));
			node.setPid(func.getStr("parent_code"));
			node.setText(func.getStr("func_name"));
			node.setUrl(func.getStr("link_page"));
			node.setIcon(func.getStr("icon"));
			node.setSpread(0==func.getInt("spread")?true:false);
			Collection<TreeNode> children = this.getRoleFunctionTree(roleCode, func.getStr("id"));
			node.setChildren(children);
			nodes.add(node);
		}
		return nodes;
	}
	
}
