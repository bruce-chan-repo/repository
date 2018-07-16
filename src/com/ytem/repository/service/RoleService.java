package com.ytem.repository.service;

import java.util.List;

import com.ytem.repository.bean.Role;
import com.ytem.repository.common.PageInfoExt;

public interface RoleService {
	/**
	 * 获取所有角色
	 * @return
	 */
	List<Role> getAllRole();
	
	/**
	 * 获取所有的用户角色
	 * @return
	 */
	PageInfoExt<Role> getAllRole(Role condition, Integer pageNum, Integer pageSize);
	
	/**
	 * 给某个角色进行授权
	 * @param role
	 */
	int accreditToRole(Role role);
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	int addRole(Role role);
	
	/**
	 * 编辑角色
	 * @param role
	 * @return
	 */
	int editRole(Role role);
	
	/**
	 * 根据角色名称获取角色
	 * @param condition
	 * @return
	 */
	Role getRoleByRoleName(Role condition);
	
	/**
	 * 获取角色
	 * @param id
	 * @return
	 */
	Role getRole(Integer id);
	
	/**
	 * 批量删除角色.
	 * @param ids
	 * @return
	 */
	int batchDelete(String ids);
}
