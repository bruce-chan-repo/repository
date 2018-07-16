package com.ytem.repository.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ytem.repository.bean.Client;
import com.ytem.repository.common.JsonResult;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.common.ResponseCode;
import com.ytem.repository.service.ClientService;

/**
 * 客户控制层. （弃用）
 * @author 陈康敬💪
 * @date 2018年6月13日下午11:40:36
 * @description
 */
@Controller
@RequestMapping("client")
public class ClientController {
	private final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private ClientService clientService;
	
	
	/**
	 * 跳转到客户列表.
	 * @return
	 */
	@RequestMapping("toClients.do")
	public ModelAndView toClients() {
		return new ModelAndView("client/clients");
	}
	
	/**
	 * 获取客户列表.
	 * @return
	 */
	@RequestMapping("clients.do")
	public ModelAndView selectClients(Client condition, @RequestParam(value = "curpage", defaultValue = "1") Integer pageNum, 
				@RequestParam(value = "limit", defaultValue = "10") Integer pageSize) {
		ModelAndView mv = new ModelAndView("client/clientsData");
		
		// 查询客户信息
		PageInfoExt<Client> clients = clientService.getClients(condition, pageNum, pageSize);
		
		mv.addObject("pageInfo", clients);
		return mv;
	}
	
	/**
	 * 跳转到添加客户信息页面。
	 * @return
	 */
	@RequestMapping("toAddClient")
	public String toAddClient() {
		return "client/addClient";
	}
	
	/**
	 * 获取客户列表.
	 * @return
	 */
	@RequestMapping(value = "addClient.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addClient(Client client) {
		JsonResult result;
		
		try {
			// 执行添加操作.
			int row = clientService.addClient(client);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "添加客户成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "添加客户失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加客户信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "添加客户失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到编辑页面.
	 * @param id
	 * @return
	 */
	@RequestMapping("toEditClient.do")
	public ModelAndView toEditClient(Integer id) {
		ModelAndView mv = new ModelAndView("client/addClient");
		
		// 获取客户信息.
		Client client = clientService.getClient(id);
		
		mv.addObject("client", client);
		return mv;
	}
	
	/**
	 * 修改客户信息.
	 * @param client
	 * @return
	 */
	@RequestMapping(value = "editClient.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult updateClient(Client client) {
		JsonResult result;
		
		try {
			// 执行添加操作.
			int row = clientService.updateClient(client);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "修改客户成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "修改客户失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加客户信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "修改客户失败");
		}
		
		return result;
	}
	
	/**
	 * 删除客户信息
	 * @param clientIds
	 * @return
	 */
	@RequestMapping("deleteClients.do")
	@ResponseBody
	public JsonResult deleteClient(String clientIds) {
		JsonResult result;
		
		// 参数校验
		if (StringUtils.isEmpty(clientIds)) {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		try {
			// 执行添加操作.
			int row = clientService.batchDelClient(clientIds);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "删除客户成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "删除客户失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-删除客户信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "删除客户失败");
		}
		
		return result;
	}
}
