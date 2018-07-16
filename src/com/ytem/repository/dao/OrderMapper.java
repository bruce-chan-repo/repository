package com.ytem.repository.dao;

import java.util.List;

import com.ytem.repository.bean.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    /**
     * 获取分页信息
     * @param conditon
     * @return
     */
    List<Order> selectOrders(Order condition);
}