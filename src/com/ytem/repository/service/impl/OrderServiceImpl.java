package com.ytem.repository.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

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
import com.ytem.repository.utils.DateTimeUtil;

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
		order.setCreateTime(DateTimeUtil.date2String(new Date()));
		int result = orderMapper.insertSelective(order);
		
		// step 2: 保存订单明细信息
		order.setTableNumber(order.getId() % 10);
		result += orderItemMapper.batchInsert(order);
		
		//result += stockMapper.dealWithQuantity(order);
		
		// step 3: 减库存.
		List<OrderItem> orderItems = order.getOrderItems();
		int size = orderItems.size();
		for (int i = 0; i < size; i++) {
			OrderItem orderItem = orderItems.get(i);
			
			if ("-".equals(orderItem.getProductCode()) || StringUtils.isEmpty(orderItem.getProductCode())) {
				// 查询该配件的数量.
				Stock stock = new Stock();
				stock.setUserId(order.getUserId());
				stock.setProductName(orderItem.getProductName());
				
				int quantity = stockMapper.getQuantityByCondition(stock);
				int newQuantity = quantity - orderItem.getQuantity();
				
				stock.setQuantity(newQuantity);
				stockMapper.updateQuantiyByCondition(stock);
			} else {
				int quantity = orderItem.getQuantity();
				
				Order tempOrder = new Order();
				tempOrder.setId(quantity);    // 辅助存储数量
				
				List<OrderItem> items = new ArrayList<>();
				items.add(orderItem);
				
				tempOrder.setOrderItems(items);
				stockMapper.dealWithQuantity(tempOrder);
			}
		}
		
		// step 5: 将库存为0的库存删除.
		result += stockMapper.batchUpdateStatus();
		
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


	@Override
	public int batchDelete(String orderIds) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|批量删除订单信息.|";
		logger.debug(opreation + ".|开始");
		int result = 0;
		
		// 删除操作.
		StringTokenizer token = new StringTokenizer(orderIds, ",");
		while (token.hasMoreElements()) {
			Integer orderId = (Integer) token.nextElement();
			Integer tableNum = orderId % 10;
			
			// 删除分表中的数据
			orderItemMapper.batchDeleteByOrderId(orderId, tableNum);
			
			// 删除订单详情
			result += orderMapper.deleteByPrimaryKey(orderId);
		}
		
		return result;
	}

}
