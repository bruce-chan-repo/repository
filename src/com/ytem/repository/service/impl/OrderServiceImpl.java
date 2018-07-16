package com.ytem.repository.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ytem.repository.bean.Order;
import com.ytem.repository.bean.OrderItem;
import com.ytem.repository.bean.Stock;
import com.ytem.repository.common.Const;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.dao.OrderItemMapper;
import com.ytem.repository.dao.OrderMapper;
import com.ytem.repository.dao.StockMapper;
import com.ytem.repository.service.OrderService;

/**
 * 订单的业务实现
 * @author 陈康敬💪
 * @date 2018年5月17日上午9:22:09
 * @description
 */

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	private final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private StockMapper stockMapper;
	

	@Override
	@Transactional
	public int saveOrder(Order order) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|保存订单信息.|";
		logger.debug(opreation + ".|开始");
		
		// step 1: 保存订单信息.
		int result = orderMapper.insertSelective(order);
		
		// step 2: 保存订单明细信息
		order.setTableNumber(order.getId() % 10);
		result += orderItemMapper.batchInsert(order);
		
		// step 3: 将库存状态置为出售状态
		result += stockMapper.batchUpdateStatus(order);
		
		// step 4: 将配件减库存.
		List<OrderItem> orderItems = order.getOrderItems();
		int size = orderItems.size();
		for (int i = 0; i < size; i++) {
			OrderItem orderItem = orderItems.get(i);
			// 判断是否是配件.
			if ("-".equals(orderItem.getProductCode()) || StringUtils.isEmpty(orderItem.getProductCode())) {
				// 查询该配件的数量.
				Stock stock = new Stock();
				stock.setUserId(order.getUserId());
				stock.setProductName(orderItem.getProductName());
				
				int quantity = stockMapper.getQuantityByCondition(stock);
				int newQuantity = quantity - orderItem.getQuantity();
				
				stock.setQuantity(newQuantity);
				stockMapper.updateQuantiyByCondition(stock);
			}
		}
		
		logger.debug(opreation + ".|完成");
		return result;
	}


	@Override
	public PageInfoExt<Order> getOrders(Order condition, int pageNum, int pageSize) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有订单信息.|";
		logger.debug(opreation + ".|开始");
		
		PageHelper.startPage(pageNum, pageSize);
		
		List<Order> orders = orderMapper.selectOrders(condition);
		PageInfoExt<Order> pageInfo = new PageInfoExt<>(orders);
		
		logger.debug(opreation + ".|完成");
		return pageInfo;
	}


	@Override
	public Order getOrderById(Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|根据订单编号获取订单信息.|";
		logger.debug(opreation + ".|开始");
		
		Order order = orderMapper.selectByPrimaryKey(id);
		
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderNo(order.getId());
		orderItem.setTableNumber(order.getId() % 10);
		
		List<OrderItem> orderItems = orderItemMapper.selectOrderItems(orderItem);
		order.setOrderItems(orderItems);
		
		logger.debug(opreation + ".|完成");
		return order;
	}

}
