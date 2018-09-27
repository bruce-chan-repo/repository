package com.ytem.repository.service;

import com.ytem.repository.bean.Order;
import com.ytem.repository.common.PageInfoExt;

/**
 * 订单的业务接口
 * @author 陈康敬💪
 * @date 2018年5月17日上午9:21:35
 * @description
 */
public interface OrderService {
	
	/**
	 * 保存订单
	 * @param order
	 * @return
	 */
	int saveOrder(Order order);
	
	/**
	 * 获取订单分页信息.
	 * @param condition
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfoExt<Order> getOrders(Order condition, int pageNum, int pageSize);
	
	/**
	 * 根据订单id获取订单信息.
	 * @param id
	 * @return
	 */
	Order getOrderById(Integer id);
	
	/**
	 * 批量删除订单.
	 * @param orderIds
	 * @return
	 */
	int batchDelete(String orderIds);
}
