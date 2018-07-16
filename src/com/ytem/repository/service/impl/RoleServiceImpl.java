package com.ytem.repository.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ytem.repository.bean.Role;
import com.ytem.repository.common.Const;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.dao.RoleMapper;
import com.ytem.repository.service.RoleService;

/**
 * 角色业务层.
 * @author 陈康敬💪
 * @date 2018年5月25日下午2:34:23
 * @description
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
	private final Logger logger = Logger.getLogger(RoleServiceImpl.class);
	
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Override
	public List<Role> getAllRole() {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有的角色.|";
		logger.debug(opreation + ".|开始");
		
		Role condition = new Role();
		condition.setAvailable(true);
		
		List<Role> roles = roleMapper.selectRoles(condition);
		
		logger.debug(opreation + ".|完成");
		return roles;
	}
	
	@Override
	public PageInfoExt<Role> getAllRole(Role condition, Integer pageNum, Integer pageSize) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有的角色分页数据.|";
		logger.debug(opreation + ".|开始");
		
		PageHelper.startPage(pageNum, pageSize);
		List<Role> roles = roleMapper.selectRoles(condition);
		
		PageInfoExt<Role> pageInfo = new PageInfoExt<>(roles);
		
		logger.debug(opreation + ".|完成");
		return pageInfo;
	}

	@Override
	public int accreditToRole(Role role) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|给某个角色进行授权.|";
		logger.debug(opreation + ".|开始");
		
		// 调用删除.
		roleMapper.deleteRoleMenu(role.getId());
		
		// 重新授权.
		int row = roleMapper.addRoleMenu(role);
		
		logger.debug(opreation + ".|完成");
		return row;
	}

	@Override
	public int addRole(Role role) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|添加角色.|";
		logger.debug(opreation + ".|开始");
		
		int row = roleMapper.insertSelective(role);
		
		logger.debug(opreation + ".|完成");
		return row;
	}

	@Override
	public int editRole(Role role) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改角色.|";
		logger.debug(opreation + ".|开始");
		
		int row = roleMapper.updateByPrimaryKeySelective(role);
		
		logger.debug(opreation + ".|完成");
		return row;
	}

	@Override
	public Role getRoleByRoleName(Role condition) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改角色.|";
		logger.debug(opreation + ".|开始");
		
		Role role = roleMapper.selectRoleByRoleName(condition);
		
		logger.debug(opreation + ".|完成");
		return role;
	}

	@Override
	public Role getRole(Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改角色.|";
		logger.debug(opreation + ".|开始");
		
		Role role = roleMapper.selectByPrimaryKey(id);
		
		logger.debug(opreation + ".|完成");
		return role;
	}

	@Override
	public int batchDelete(String ids) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改角色.|";
		logger.debug(opreation + ".|开始");
		
		int result = 0;
		// step 1: 先删除关系表数据.
		result = roleMapper.batchDeleteRoleMenu(ids);
		
		// step 2: 删除角色表数据.
		result += roleMapper.batchDeleteRole(ids);
		
		logger.debug(opreation + ".|完成");
		return result;
	}
}
