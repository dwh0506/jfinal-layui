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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.qinhailin.common.base.service.BaseService;
import com.qinhailin.common.model.SysUserRole;
import com.qinhailin.common.vo.Grid;

/**
 * 用户角色关系
 * 
 * @author qinhailin
 *
 */
public class SysUserRoleService extends BaseService {

	private SysUserRole dao = new SysUserRole().dao();
	
	/* (non-Javadoc)
	 * @see com.qinhailin.common.base.service.BaseService#getDao()
	 */
	@Override
	public Model<?> getDao() {
		return dao;
	}


	/**
	 * 查询用户角色列表
	 * @param userCode
	 * @return
	 */
	public List<Record> queryUserRoleList(String userCode){
		return queryForList("select * from sys_user_role where user_code=?", userCode);
	}
	
	/**
	 * 查询用户角色(登录用户没有的角色)
	 * @param userCode
	 * @param loginUserCode
	 * @return
	 */
	public List<Record> queryUserRoleList(String userCode,String loginUserCode){
		return queryForList(Db.getSql("core.queryUserRoleList"), userCode,loginUserCode);
	}
	

	/**
	 * 查询角色用户列表
	 * @param pageNumber
	 * @param pageSize
	 * @param roleCode
	 * @return
	 */
	public Grid queryRoleUserList(int pageNumber,int pageSize,String roleCode,Record record){
		Record rd=new Record();
		rd.set("a.user_code like '%" + record.getStr("userName") + "%' or a.user_name", record.get("userName"));
		rd.set("org_id", record.get("orgId"));
		
		String sql=Db.getSql("core.queryRoleUserList");
		sql=sql.replace("?", roleCode);

		return queryForList(sql,pageNumber, pageSize,rd,null);
	}
	
	/**
	 * 查询角色未选用户列表
	 * @param pageNumber
	 * @param pageSize
	 * @param roleCode
	 * @return
	 */
	public Grid queryUserListNotInRoleCode(int pageNumber,int pageSize,String roleCode,Record record){
		Record rd=new Record();
		rd.set("a.user_code like '%" + record.getStr("userName") + "%' or a.user_name", record.get("userName"));
		rd.set("org_id", record.get("orgId"));
		
		String sql=Db.getSql("core.queryUserListNotInRoleCode");
		sql=sql.replace("?", roleCode);
		
		return queryForList(sql, pageNumber, pageSize,rd,null);
	}

	/**
	 * 配置用户角色
	 * @param userCode
	 * @param roles
	 * @return
	 */
	public boolean saveUserRole(String userCode,String[] roles){
		try {
			return Db.tx(new IAtom() {
				
				@Override
				public boolean run() throws SQLException {
					String sql="delete from sys_user_role where user_code=?";
					Db.update(sql, userCode);
					List<SysUserRole> modelList=new ArrayList<>();
					for(String role:roles){
						SysUserRole entity =new SysUserRole();
						entity.setId(role+"-"+userCode);
						entity.setUserCode(userCode);
						entity.setRoleCode(role);
						modelList.add(entity);
					}
					Db.batchSave(modelList, modelList.size());
					//清除用户菜单缓存
					CacheKit.remove("userFunc", "funcList"+userCode);
					return true;
				}
			});			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 配置角色用户
	 * @param role
	 * @param user
	 * @return
	 */
	public boolean saveRoleUser(String roleCode,String[] users){
		try {
			return Db.tx(new IAtom() {
				
				@Override
				public boolean run() throws SQLException {
					List<SysUserRole> modelList=new ArrayList<>();
					for(String user:users){	
						SysUserRole entity =new SysUserRole();
						entity.setId(roleCode+"-"+user);
						entity.setUserCode(user);
						entity.setRoleCode(roleCode);
						modelList.add(entity);
					}
					//清除所有用户菜单缓存，这里比较暴力，（因为这里无法获取角色移除的用户）
					CacheKit.removeAll("userFunc");
					Db.batchSave(modelList, modelList.size());
					return true;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 删除角色用户关系
	 * @param userCode
	 * @param roleCode
	 * @return
	 */
	public boolean deleteRoleUser(String userCode,String roleCode){
		String sql="delete from sys_user_role where user_code=? and role_code=?";
		Db.update(sql, userCode,roleCode);
		return true;		
	}

}
