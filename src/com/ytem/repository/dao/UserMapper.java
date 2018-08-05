package com.ytem.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ytem.repository.bean.User;


public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User selectByUsername(@Param("username") String username, @Param("id") Integer id);
    
    /**
     * 查询所有用户信息
     * @return
     */
    List<User> selectUserList();
    
    /**
     * 根据用户组编号删除用户.
     * @param ids
     * @return
     */
    int deleteUsers(@Param("userIds") String userIds);
    
    /**
     * 获取所有的客户
     * @return
     */
    List<User> selectAllClients();
    
    /**
     * 根据角色编号获取对应的用户数量
     * @param roleId
     * @return
     */
    int getCountByRoleId(Integer roleId);
}