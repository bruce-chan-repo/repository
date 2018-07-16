package com.ytem.repository.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ytem.repository.bean.Menu;
import com.ytem.repository.common.Const;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.dao.MenuMapper;
import com.ytem.repository.service.MenuService;

/**
 * 菜单业务实现类
 * @author 陈康敬💪
 * @date 2018年5月17日上午9:19:12
 * @description
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService{
	private final Logger logger = Logger.getLogger(MenuServiceImpl.class);
	
	@Autowired
	private MenuMapper menuMapper;
	
	
	@Override
	public PageInfoExt<Menu> getMenus(Menu condition, Integer pageNum, Integer pageSize) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有菜单信息.|";
		logger.debug(opreation + ".|开始");
		
		PageHelper.startPage(pageNum, pageSize);
		
		List<Menu> menuList = menuMapper.selectMenuList(condition);
		PageInfoExt<Menu> pageInfo = new PageInfoExt<>(menuList);
		
		logger.debug(opreation + ".|结束");
		return pageInfo;
	}
	
	@Override
	public List<Menu> getMenusIsTop() {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取一级菜单.|";
		logger.debug(opreation + ".|开始");
		
		List<Menu> menuList = menuMapper.selectMenuIsTop();
		
		logger.debug(opreation + ".|结束");
		return menuList;
	}
	
	@Override
	public boolean checkMenuNameIsUseable(Menu condition) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|验证菜单是否可用.|";
		logger.debug(opreation + ".|开始");
		
		boolean isUseable = true;
		Menu menu = menuMapper.getMenuByNameAndParentId(condition);
		// 验证该菜单名称是否可用.
		if (menu != null) {
			isUseable = false;
		}
		
		logger.debug(opreation + ".|结束");
		return isUseable;
	}
	
	@Override
	public Menu getMenuById(Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|根据菜单编号获取菜单信息.|";
		logger.debug(opreation + ".|开始");
		
		Menu menu = menuMapper.selectByPrimaryKey(id);
		
		logger.debug(opreation + ".|结束");
		return menu;
	}

	@Override
	public int addMenu(Menu menu) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|添加菜单.|";
		logger.debug(opreation + ".|开始");
		
		int row = menuMapper.insertSelective(menu);
		
		logger.debug(opreation + ".|结束");
		return row;
	}

	@Override
	public int editMenu(Menu menu) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|编辑菜单.|";
		logger.debug(opreation + ".|开始");
		
		int row = menuMapper.updateByPrimaryKeySelective(menu);
		
		logger.debug(opreation + ".|结束");
		return row;
	}

	@Override
	public List<Menu> getChildById(Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|根据某节点编号获取子节点.|";
		logger.debug(opreation + ".|开始");
		
		List<Menu> children = menuMapper.selectChildByParentId(id);
		
		logger.debug(opreation + ".|结束");
		return children;
	}

	@Override
	public int batchDelete(String ids) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|批量删除菜单信息.|";
		logger.debug(opreation + ".|开始");
		
		int row = menuMapper.batchDelete(ids);
		
		logger.debug(opreation + ".|结束");
		return row;
	}

	@Override
	public List<Menu> selectMenuByRoleId(Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|批量删除菜单信息.|";
		logger.debug(opreation + ".|开始");
		
		List<Menu> menus = menuMapper.selectMenuByRoleId(id);
		
		logger.debug(opreation + ".|结束");
		return menus;
	}
}
