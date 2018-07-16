package com.ytem.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ytem.repository.bean.Product;


public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKeyWithBLOBs(Product record);

    int updateByPrimaryKey(Product record);
    
    /**
     * 查询所有的商品
     * @return
     */
    List<Product> selectProducts(Product condition);
    
    /**
     * 批量删除
     * @return
     */
    int batchDelete(@Param("productIds") String productIds);
    
    /**
     * 根据产品编号获取产品信息.
     * @param code
     * @return
     */
    Product selectByCode(@Param("code") String code, @Param("id") Integer id);
}