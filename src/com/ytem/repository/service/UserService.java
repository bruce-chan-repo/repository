package com.ytem.repository.service;

import java.util.List;

import com.ytem.repository.bean.User;
import com.ytem.repository.common.PageInfoExt;

/**
 * 用户业务层
 * @author 陈康敬💪
 * @date 2018年5月8日下午11:00:43
 * @description
 */
public interface UserService {
	/**
	 * 根据用户名获取用户信息.
	 * @param username
	 * @return
	 */
	User selectUserByUsername(String username, Integer id);
	
	/**
	 * 获取所有用户信息.
	 * @return
	 */
	PageInfoExt<User> getUsers(Integer pageNum, Integer pageSize);
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	int addUser(User user);
	
	/**
	 * 删除用户
	 * @param ids
	 * @return
	 */
	int deleteUsers(String ids);
	
	/**
	 * 根据用户编号获取用户信息.
	 * @param id
	 * @return
	 */
	User selectUserById(Integer id);
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	int editUser(User user);
	
	/**
	 * 获取所有的客户.
	 * @return
	 */
	List<User> getAllClients();
	
	 /**
     * 根据角色编号获取对应的用户数量
     * @param roleId
     * @return
     */
    int getCountByRoleId(Integer roleId);
}
