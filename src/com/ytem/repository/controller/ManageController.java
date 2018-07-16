package com.ytem.repository.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ytem.repository.bean.User;
import com.ytem.repository.common.Const;

@Controller
@RequestMapping("manage")
public class ManageController {
	private final Logger logger = Logger.getLogger(ManageController.class);

	@RequestMapping("index.do")
	public ModelAndView toIndex(HttpServletRequest request) {
		logger.debug(Const.LOGGER_PREFIX_DEBUG + "跳转到主页.|");
		
		ModelAndView mv = new ModelAndView("index");
		
		Subject subject = SecurityUtils.getSubject();
		String username = subject.getPrincipal().toString();
		
		// 获取用户信息
		User currUser = (User) request.getSession().getAttribute(username);
		
		// 获取用户所拥有的权限
		
		
		mv.addObject("currUser", currUser);
		return mv;
	}
}
