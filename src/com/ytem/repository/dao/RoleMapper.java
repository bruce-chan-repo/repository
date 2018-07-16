package com.ytem.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ytem.repository.bean.Role;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    /**
     * 查询所有的角色
     * @return
     */
    List<Role> selectRoles(Role condiotn);
    
    /**
     * 根据角色编号删除该角色的所有权限
     * @param roleId
     * @return
     */
    int deleteRoleMenu(Integer roleId);
    
    /**
     * 根据角色编号删除该角色的所有权限.
     * @param roleIds
     * @return
     */
    int batchDeleteRoleMenu(@Param("roleIds") String roleIds);
    
    /**
     * 根据角色编号删除角色.
     * @param roleIds
     * @return
     */
    int batchDeleteRole(@Param("roleIds") String roleIds);
    
    /**
     * 给某个角色添加权限
     * @param role
     * @return
     */
    int addRoleMenu(Role role);
    
    /**
     * 根据条件查询角色信息
     * @param condition
     * @return
     */
    Role selectRoleByRoleName(Role condition);
}