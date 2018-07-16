package com.ytem.repository.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.ytem.repository.bean.Client;
import com.ytem.repository.common.Const;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.dao.ClientMapper;
import com.ytem.repository.service.ClientService;

/**
 * 客户业务实现
 * @author 陈康敬💪
 * @date 2018年6月13日下午11:39:56
 * @description
 */
@Repository("clientService")
public class ClientServiceImpl implements ClientService {
	private Logger logger = Logger.getLogger(ClientService.class);
	
	@Autowired
	private ClientMapper clientMapper;
	
	
	@Override
	public PageInfoExt<Client> getClients(Client condition, Integer pageNum, Integer pageSize) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有客户信息.|";
		logger.debug(opreation + ".|开始");
		
		PageHelper.startPage(pageNum, pageSize);
		List<Client> productList = clientMapper.selectClients(condition);
		
		PageInfoExt<Client> pageInfo = new PageInfoExt<>(productList);
		
		logger.debug(opreation + ".|结束");
		return pageInfo;
	}

	@Override
	public int addClient(Client record) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|添加客户信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = clientMapper.insertSelective(record);
		
		logger.debug(opreation + ".|结束");
		return result;
	}

	@Override
	public int updateClient(Client record) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改客户信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = clientMapper.updateByPrimaryKeySelective(record);
		
		logger.debug(opreation + ".|结束");
		return result;
	}

	@Override
	public int batchDelClient(String clientIds) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|批量删除客户信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = clientMapper.batchDelete(clientIds);
		
		logger.debug(opreation + ".|结束");
		return result;
	}

	@Override
	public Client getClient(Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|批量删除客户信息.|";
		logger.debug(opreation + ".|开始");
		
		Client client = clientMapper.selectByPrimaryKey(id);
		
		logger.debug(opreation + ".|结束");
		return client;
	}
	
}
