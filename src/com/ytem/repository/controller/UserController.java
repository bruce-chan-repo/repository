package com.ytem.repository.controller;

import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ytem.repository.bean.Product;
import com.ytem.repository.bean.Role;
import com.ytem.repository.bean.Stock;
import com.ytem.repository.bean.User;
import com.ytem.repository.common.Const;
import com.ytem.repository.common.JsonResult;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.common.ResponseCode;
import com.ytem.repository.service.RoleService;
import com.ytem.repository.service.StockService;
import com.ytem.repository.service.UserService;



@Controller
@RequestMapping("user")
public class UserController {
	private final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Autowired
	private StockService stockService;
	
	@RequestMapping("login.do")
	public ModelAndView login(@RequestParam("username") String username, 
				@RequestParam("password") String password, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("redirect:/manage/index.do");
		
		Subject currUser = SecurityUtils.getSubject();
		
		if (!currUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			
			try {
				// 执行登录.
				currUser.login(token);
			} catch (AuthenticationException e) {
				logger.error("异常信息-登录认证失败.|", e);
				request.getSession().setAttribute("errorMsg", e.getMessage());
			}
		}
		
		// 如果用户登录成功.
		if (currUser.isAuthenticated()) {
			// 将登录用户的值放到session
			String currUsername = currUser.getPrincipal().toString();
			User user = userService.selectUserByUsername(currUsername, null);
			request.getSession().setAttribute(currUsername, user);
		}
		
		return mv;
	}
	
	/**
	 * 跳转到登录页面
	 * @return
	 */
	@RequestMapping("toLogin.do")
	public ModelAndView toLogin(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("login");
		
		String errorMsg = (String) request.getSession().getAttribute("errorMsg");
		request.getSession().removeAttribute("errorMsg");
		mv.addObject("error", errorMsg);
		
		return mv;
	}
	
	/**
	 * 查看用户详情.
	 * @return
	 */
	@RequestMapping("detail.do")
	public ModelAndView getUserDetail(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("user/userDetail");
		
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = (User) request.getSession().getAttribute(username);
		mv.addObject("currUser", user);
		
		return mv;
	}
	
	/**
	 * 跳转到用户列表页
	 * @return
	 */
	@RequestMapping("toUsers.do")
	public ModelAndView toUsers() {
		return new ModelAndView("user/users");
	}
	
	/**
	 * 获取用户列表
	 * @return
	 */
	@RequestMapping("users.do")
	public ModelAndView selectUsers(@RequestParam(value = "curpage", defaultValue = "1") String pageNum, 
				@RequestParam(value = "limit", defaultValue = "10") String pageSize) {
		ModelAndView mv = new ModelAndView("user/usersData");
		
		Integer page = Integer.parseInt(pageNum);
		Integer limit = Integer.parseInt(pageSize);
		
		PageInfoExt<User> pageInfo = userService.getUsers(page, limit);
		mv.addObject("pageInfo", pageInfo);
		
		return mv;
	}
	
	@RequestMapping("toAddUser.do")
	public ModelAndView toAddUser(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("user/addUser");
		
		// 查询所有的角色
		List<Role> roleList = roleService.getAllRole();
		mv.addObject("roles", roleList);
		
		return mv;
	}
	
	/**
	 * 添加用户.
	 * @return
	 */
	@RequestMapping(value = "addUser.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addUser(HttpServletRequest request, User user) {
		JsonResult result;
		
		try {
			int row = userService.addUser(user);
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "添加用户成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "添加用户失败");
			}
		} catch (Exception e) {
			logger.error("异常信息-添加用户失败", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "添加用户失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到编辑用户的界面
	 * @param request
	 * @return
	 */
	@RequestMapping("toEditUser")
	public ModelAndView toEditUser(HttpServletRequest request, Integer userId) {
		ModelAndView mv = new ModelAndView("user/addUser");
		
		// 查询所有的角色
		List<Role> roleList = roleService.getAllRole();
		mv.addObject("roles", roleList);
		
		// 查询编辑用户的信息
		User user = userService.selectUserById(userId);
		
		mv.addObject("user", user);
		return mv;
	}
	
	/**
	 * 修改用户信息
	 * @param request
	 * @param user 用户信息
	 * @return
	 */
	@RequestMapping(value = "editUser.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editUser(HttpServletRequest request, User user) {
		JsonResult result;
		
		try {
			int row = userService.editUser(user);
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "修改用户信息成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "修改用户信息失败");
			}
		} catch (Exception e) {
			logger.error("异常信息-修改用户失败", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "修改用户信息失败");
		}
		
		return result;
	}
	
	/**
	 * 删除用户.
	 * @return
	 */
	@RequestMapping("deleteUsers.do")
	@ResponseBody
	public JsonResult deleteUsers(String userIds) {
		JsonResult result;
		
		try {
			if (StringUtils.isBlank(userIds)) {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
				return result;
			}
			
			// 先校验该用户名下是否存在关联的库存信息.
			StringTokenizer tokenizer = new StringTokenizer(userIds, ",");
			while (tokenizer.hasMoreElements()) {
				String userId = (String) tokenizer.nextElement();
				
				Stock condition = new Stock();
				condition.setProductName("");
				condition.setUserId(Integer.parseInt(userId));
				condition.setProduct(new Product());
				
				PageInfoExt<Stock> pageInfo = stockService.getStocks(condition, 1, 10);
				if (pageInfo != null && pageInfo.getList() != null && pageInfo.getList().size() > 0) {
					return new JsonResult(ResponseCode.ERROR.getCode(), "该用户还关联有库存商品，不能删除");
				}
			}
			
			int row = userService.deleteUsers(userIds);
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "删除用户成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "删除用户失败");
			}
		} catch (Exception e) {
			logger.error("异常信息-删除用户失败", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "删除用户失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到重置密码页面
	 * @return
	 */
	@RequestMapping("toResetPwd")
	public ModelAndView toResetPwd(Integer userId) {
		ModelAndView mv = new ModelAndView("user/resetPassword");
		mv.addObject("id", userId);
		return mv;
	}
	
	/**
	 * 重置密码
	 * @param userId
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "resetPassword.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult resetPassword(User user) {
		JsonResult result;
		
		if (user.getId() == null || StringUtils.isBlank(user.getPassword())) {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		try {
			// 获取当前重置密码的用户名
			User currUser = userService.selectUserById(user.getId());
			
			// 盐值
			ByteSource salt = ByteSource.Util.bytes(currUser.getUsername());
			
			// 对密码进行加密
			SimpleHash simpHash = new SimpleHash(Const.HASH_ALGORITHMNAME, user.getPassword(), salt, Const.HASH_ITERATIONS);
			user.setPassword(simpHash.toString());
			
			int row = userService.editUser(user);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "重置密码成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "重置密码失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-重置密码失败");
			result = new JsonResult(ResponseCode.ERROR.getCode(), "重置密码失败");
		}
		
		return result;
	}
	
	/**
	 * 验证用户名是否合法
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "checkUsernameIsLawful.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult checkUsernameIsLawful(String username, Integer userId) {
		JsonResult result;
		
		if (StringUtils.isBlank(username)) {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		User user = userService.selectUserByUsername(username, userId);
		if (user == null) {
			result = new JsonResult(ResponseCode.SUCCESS.getCode(), "用户名合法");
		} else {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "用户名不合法");
		}
		
		return result;
	}
}
