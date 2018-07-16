package com.ytem.repository.service;

import com.ytem.repository.bean.Client;
import com.ytem.repository.common.PageInfoExt;

/**
 * 客户业务接口
 * @author 陈康敬💪
 * @date 2018年6月13日下午11:39:08
 * @description
 */
public interface ClientService {
	/**
	 * 获取客户的分页信息.
	 * @param condition
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfoExt<Client> getClients(Client condition, Integer pageNum, Integer pageSize);
	
	/**
	 * 获取客户信息。
	 * @param id
	 * @return
	 */
	Client getClient(Integer id);
	
	/**
	 * 添加客户信息
	 * @param record
	 * @return
	 */
	int addClient(Client record);
	
	/**
	 * 修改客户信息
	 * @param record
	 * @return
	 */
	int updateClient(Client record);
	
	/**
	 * 批量删除客户信息.
	 * @param clientIds
	 * @return
	 */
	int batchDelClient(String clientIds);
}
