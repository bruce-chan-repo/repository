package com.ytem.repository.service;

import java.util.List;

import com.ytem.repository.bean.Menu;
import com.ytem.repository.common.PageInfoExt;

/**
 * 菜单业务接口
 * @author 陈康敬💪
 * @date 2018年5月17日上午9:18:45
 * @description
 */
public interface MenuService {
	/**
	 * 查询菜单分页信息
	 * @param condition 封装的条件
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfoExt<Menu> getMenus(Menu condition, Integer pageNum, Integer pageSize);
	
	/**
	 * 根据菜单编号获取菜单信息.
	 * @param id
	 * @return
	 */
	Menu getMenuById(Integer id);
	
	/**
	 * 获取一级菜单.
	 * @return
	 */
	List<Menu> getMenusIsTop();
	
	/**
	 * 验证菜单是否可用.
	 * @param menuName
	 * @param parentId
	 * @return
	 */
	boolean checkMenuNameIsUseable(Menu condition);
	
	/**
	 * 添加菜单信息。
	 * @param menu
	 * @return
	 */
	int addMenu(Menu menu);
	
	/**
	 * 编辑菜单信息.
	 * @param menu
	 * @return
	 */
	int editMenu(Menu menu);
	
	/**
	 * 批量删除菜单信息.
	 * @param ids
	 * @return
	 */
	int batchDelete(String ids);
	
	/**
	 * 根据编号查询子节点
	 * @param id
	 * @return
	 */
	List<Menu> getChildById(Integer id);
	
	/**
	 * 获取某个角色的权限.
	 * @param id
	 * @return
	 */
	List<Menu> selectMenuByRoleId(Integer id);
}
