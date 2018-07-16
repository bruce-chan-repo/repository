package com.ytem.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ytem.repository.bean.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    /**
     * 根据条件获取菜单列表
     * @param condition
     * @return
     */
    List<Menu> selectMenuList(Menu condition);
    
    /**
     * 批量删除菜单
     * @param menuIds
     * @return
     */
    int batchDelete(@Param("menuIds") String menuIds);
    
    /**
     * 根据父级编号获取所有子节点.
     * @param parentId
     * @return
     */
    List<Menu> selectChildByParentId(Integer parentId);
    
    /**
     * 查询一级菜单
     * @return
     */
    List<Menu> selectMenuIsTop();
    
    /**
     * 根据菜单名称和父级编号获取菜单信息.
     * @param menuName
     * @param parentId
     * @return
     */
    Menu getMenuByNameAndParentId(Menu condition);
    
    /**
     * 根据角色编号获取该角色所拥有的权限 
     * @param id
     * @return
     */
    List<Menu> selectMenuByRoleId(Integer id);
}