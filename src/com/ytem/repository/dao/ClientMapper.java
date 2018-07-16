package com.ytem.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ytem.repository.bean.Client;

public interface ClientMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Client record);

    int insertSelective(Client record);

    Client selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Client record);

    int updateByPrimaryKey(Client record);
    
    /**
     * 获取客户信息
     * @param condition 筛选的条件.
     * @return
     */
    List<Client> selectClients(Client condition);
    
    /**
     * 批量删除客户信息.
     * @param clientIds
     * @return
     */
    int batchDelete(@Param("clientIds") String clientIds);
}