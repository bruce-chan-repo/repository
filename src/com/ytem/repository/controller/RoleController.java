package com.ytem.repository.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

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
import com.ytem.repository.bean.Role;
import com.ytem.repository.common.JsonResult;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.common.ResponseCode;
import com.ytem.repository.service.MenuService;
import com.ytem.repository.service.RoleService;
import com.ytem.repository.service.UserService;

/**
 * 角色控制器
 * @author 陈康敬💪
 * @date 2018年5月25日下午2:31:49
 * @description
 */
@Controller
@RequestMapping("role")
public class RoleController {
	private final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 跳转到角色列表.
	 * @return
	 */
	@RequestMapping("toRoles.do")
	public ModelAndView toRoles() {
		return new ModelAndView("role/roles");
	}
	
	/**
	 * 获取角色列表.
	 * @return
	 */
	@RequestMapping("roles.do")
	public ModelAndView selectUsers(Role condition, @RequestParam(value = "curpage", defaultValue = "1") Integer pageNum, 
				@RequestParam(value = "limit", defaultValue = "10") Integer pageSize) {
		ModelAndView mv = new ModelAndView("role/rolesData");
		
		// 查询菜单信息
		PageInfoExt<Role> pageInfo = roleService.getAllRole(condition, pageNum, pageSize);
		
		mv.addObject("pageInfo", pageInfo);
		return mv;
	}
	
	/**
	 * 跳转到添加角色页面.
	 * @return
	 */
	@RequestMapping("toAddRole.do")
	public ModelAndView toAddRole() {
		ModelAndView mv = new ModelAndView("role/addRole");
		return mv;
	}
	
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	@RequestMapping("addRole.do")
	@ResponseBody
	public JsonResult addRole(Role role) {
		JsonResult result;
		
		try {
			// 执行添加操作.
			int row = roleService.addRole(role);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "添加角色成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "添加角色失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加角色信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "添加角色失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toEditRole.do")
	public ModelAndView toEditProduct(Integer roleId) {
		ModelAndView mv = new ModelAndView("role/addRole");
		
		// 获取角色信息.
		Role role = roleService.getRole(roleId);
		
		mv.addObject("role", role);
		return mv;
	}
	
	/**
	 * 修改商品信息
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "editRole.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editRole(Role role) {
		JsonResult result;
		
		try {
			// 执行修改操作.
			int row = roleService.editRole(role);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "修改角色成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "修改角色失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加角色信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "修改角色失败");
		}
		
		return result;
	}

	/**
	 * 批量删除角色.
	 * @param roleIds 角色编号组.
	 * @return
	 */
	@RequestMapping("deleteRoles.do")
	@ResponseBody
	public JsonResult deleteRole(String roleIds) {
		JsonResult result;
		
		try {
			if (StringUtils.isEmpty(roleIds)) {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
			}
			
			// 删除角色前， 查看该角色是否关联有用户
			StringTokenizer tokenizer = new StringTokenizer(roleIds, ",");
			while (tokenizer.hasMoreElements()) {
				String roleId = (String) tokenizer.nextElement();
				int count = userService.getCountByRoleId(Integer.parseInt(roleId));
				
				if (count > 0) {
					result = new JsonResult(ResponseCode.ERROR.getCode(), "该角色还有用户关联，不能删除");
					return result;
				}
			}
			
			// 执行修改操作.
			int row = roleService.batchDelete(roleIds);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "删除角色成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "删除角色失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加角色信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "删除角色失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到授权页面.
	 * @param roleId
	 * @return
	 */
	@RequestMapping("toAccredit.do")
	public ModelAndView toAccredit(HttpServletRequest request, Integer roleId) {
		ModelAndView mv = new ModelAndView("role/accredit");
		
		// 获取所有的二级节点.
		List<Integer> menuIds = new ArrayList<>(0);
		
		List<Menu> menuList = menuService.selectMenuByRoleId(roleId);
		Collections.sort(menuList, new Comparator<Menu>() {
			@Override
			public int compare(Menu o1, Menu o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
		
		for (int i = 0; i < menuList.size(); i++) {
			Menu temp = menuList.get(i);
			
			if (menuIds.contains(temp.getParentId())) {
				menuIds.remove(temp.getParentId());
			}
			
			menuIds.add(temp.getId());
		}
		
		// 将menuIds转换成  1,2,3格式的
		Object[] ids = menuIds.toArray();
		String haveNodes = StringUtils.join(ids, ",");
		
		mv.addObject("haveNodes", haveNodes);
		mv.addObject("roleId", roleId);
		return mv;
	}
	
	/**
	 * 获取菜单树
	 * @return
	 */
	@RequestMapping("getTreeData.do")
	@ResponseBody
	public List<Menu> getTreeData() {
		List<Menu> menusIsTop = menuService.getMenusIsTop();
		return menusIsTop;
	}
	
	/**
	 * 给角色授权.
	 * @param role
	 * @return
	 */
	@RequestMapping("accreditToRole.do")
	@ResponseBody
	public JsonResult accreditToRole(Role role) {
		JsonResult result;
		
		try {
			if (role.getMenuList() == null || role.getMenuList().size() == 0) {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
				return result;
			}
			
			int row = roleService.accreditToRole(role);
			
			// 判断是否授权成功.
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "授权成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "授权失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-给角色授权", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "授权失败");
		}
			
		return result;
	}
	
	/**
	 * 验证角色名是否可用.
	 * @param roleName
	 * @param id
	 * @return
	 */
	@RequestMapping("checkRoleNameIsLawful.do")
	@ResponseBody
	public JsonResult checkRoleNameIsLawful(String roleName, Integer roleId) {
		JsonResult result;
		
		if (StringUtils.isBlank(roleName)) {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		// 封装查询条件
		Role condition = new Role();
		condition.setRoleName(roleName);
		condition.setId(roleId);
		
		Role role = roleService.getRoleByRoleName(condition);
		
		// 判断该角色名是否可用.
		if (role == null) {
			result = new JsonResult(ResponseCode.SUCCESS.getCode(), "角色名称可用");
		} else {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "角色名称不可用");
		}
		
		return result;
	}
}
