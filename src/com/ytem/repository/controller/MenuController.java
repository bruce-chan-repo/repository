package com.ytem.repository.controller;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ytem.repository.bean.Menu;
import com.ytem.repository.common.JsonResult;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.common.ResponseCode;
import com.ytem.repository.service.MenuService;

/**
 * 菜单控制层
 * @author 陈康敬💪
 * @date 2018年5月23日下午9:34:09
 * @description
 */
@Controller
@RequestMapping("menu")
public class MenuController {
	private final Logger logger = Logger.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuService;
	
	
	/**
	 * 跳转到菜单列表页
	 * @return
	 */
	@RequestMapping("toMenus.do")
	public ModelAndView toMenus() {
		return new ModelAndView("menu/menus");
	}
	
	/**
	 * 获取菜单列表
	 * @return
	 */
	@RequestMapping("menus.do")
	public ModelAndView selectUsers(Menu condition, @RequestParam(value = "curpage", defaultValue = "1") Integer pageNum, 
				@RequestParam(value = "limit", defaultValue = "10") Integer pageSize) {
		ModelAndView mv = new ModelAndView("menu/menusData");
		
		// 查询菜单信息
		PageInfoExt<Menu> pageInfo = menuService.getMenus(condition, pageNum, pageSize);
		
		mv.addObject("pageInfo", pageInfo);
		return mv;
	}
	
	/**
	 * 跳转到添加菜单页面
	 * @return
	 */
	@RequestMapping("toAddMenu.do")
	public ModelAndView toAddMenu() {
		ModelAndView mv = new ModelAndView("menu/addMenu");
		
		// 获取一级菜单信息.
		List<Menu> menus = menuService.getMenusIsTop();
		
		mv.addObject("parents", menus);
		return mv;
	}
	
	/**
	 * 添加菜单信息
	 * @param Menu
	 * @return
	 */
	@RequestMapping(value = "addMenu.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addMenu(Menu menu) {
		JsonResult result;
		
		try {
			// 执行添加操作.
			int row = menuService.addMenu(menu);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "添加菜单成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "添加菜单失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加菜单信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "添加菜单失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toEditMenu.do")
	public ModelAndView toEditMenu(Integer id) {
		ModelAndView mv = new ModelAndView("menu/addMenu");
		
		// 获取菜单信息.
		Menu menu = menuService.getMenuById(id);
		
		// 获取一级菜单信息.
		List<Menu> menus = menuService.getMenusIsTop();
		
		mv.addObject("menu", menu);
		mv.addObject("parents", menus);
		return mv;
	}
	
	/**
	 * 修改菜单信息
	 * @param Menu
	 * @return
	 */
	@RequestMapping(value = "editMenu.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editMenu(Menu menu) {
		JsonResult result;
		
		try {
			// 执行修改操作.
			int row = menuService.editMenu(menu);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "修改菜单成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "修改菜单失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加菜单信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "修改菜单失败");
		}
		
		return result;
	}
	
	/**
	 * 删除菜单信息
	 * @param Menu
	 * @return
	 */
	@RequestMapping(value = "deleteMenus.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteMenus(String menuIds, String parentIds) {
		JsonResult result;
		
		try {
			// 校验参数
			if (StringUtils.isBlank(menuIds)) {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
				return result;
			}
			
			// 如果删除的菜单包含一级菜单.
			if (StringUtils.isNotBlank(parentIds)) {
				StringTokenizer token = new StringTokenizer(parentIds, ",");
				while (token.hasMoreTokens()) {
					String id = token.nextToken();
					
					// 获取删除节点的字节点.
					List<Menu> chilren = menuService.getChildById(Integer.parseInt(id));
					int size = chilren.size();
					for (int i = 0; i < size; i++) {
						Menu vo = chilren.get(i);
						menuIds += "," + vo.getId();
					}
				}
			} 
			
			// 执行添加操作.
			int row = menuService.batchDelete(menuIds);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "删除菜单成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "删除菜单失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加菜单信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "删除菜单失败");
		}
		
		return result;
	}
	
	/**
	 * 验证菜单是否可用
	 * @param menuName
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "checkMenuIsUseable.do")
	@ResponseBody
	public JsonResult checkMenuNameIsUseable(Menu condition) {
		JsonResult result;
		
		boolean isUseable = menuService.checkMenuNameIsUseable(condition);
		if ( isUseable ) {
			result = new JsonResult(ResponseCode.SUCCESS.getCode(), "该菜单可用");
		} else {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "该菜单不可用");
		}
		
		return result;
	}
}
