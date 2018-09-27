package com.ytem.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ytem.repository.bean.Order;
import com.ytem.repository.bean.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
    
    /**
     * 批量添加订单明细.
     * @param order
     * @return
     */
    int batchInsert(Order order);
    
    /**
     * 查询订单明细.
     * @param item
     * @return
     */
    List<OrderItem> selectOrderItems(OrderItem item);
    
    /**
     * 批量删除订单明细
     * @param orderId
     * @return
     */
    int batchDeleteByOrderId(@Param("orderId") Integer orderId, @Param("tableNum") Integer tableNum);
}