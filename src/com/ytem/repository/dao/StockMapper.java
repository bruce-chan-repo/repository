package com.ytem.repository.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ytem.repository.bean.ImportStock;
import com.ytem.repository.bean.Order;
import com.ytem.repository.bean.Stock;

public interface StockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
    
    /**
     * 查询库存分页信息.
     * @param stock
     * @return
     */
    List<Stock> selectStocks(Stock condition);
    
    /**
     * 根据条件查询库存信息.
     * @param condition
     * @return
     */
    Stock selectByCondition(Stock condition);
    
    /**
     * 获取所有库存信息.
     * @return
     */
    List<Stock> getStocks(@Param("userId") Integer userId);
    
	/**
	 * 批量添加
	 * @param list
	 * @return
	 */
    int batchInsert(List<Stock> list);
    
    /**
     * 添加库存信息.
     * @param stock
     * @return
     */
    int addStock(Stock stock);
    
    /**
     * 获取需要采购的产品
     * @return
     */
    List<Stock> selectNeedPurchase();
    
    /**
     * 处理库存数量.
     * @return
     */
    int dealWithQuantity(Order order);
    
    /**
     * 修改库存数量为0的库存为出售状态.
     * @param order
     * @return
     */
    int batchUpdateStatus();
    
    /**
     * 获取某个产品的库存数量.
     * @param stock
     * @return
     */
    int getQuantityByCondition(Stock stock);
    
    /**
     * 更新某个库存的数量.
     * @param stock
     * @return
     */
    int updateQuantiyByCondition(Stock stock);
    
    /**
     * 批量删除
     * @param stockIds
     * @return
     */
    int batchDelete(@Param("stockIds") String stockIds);
    
    /**
     * 获取某个产品的库存数量.
     * @param productCode
     * @return
     */
    List<Integer> getStockCountByProductCode(Map<String, Object> paramMap);
    
    /**
     * 获取某个产品的库存数量.
     * @param productCode
     * @return
     */
    Stock getStockCountByProductName(Map<String, Object> paramMap);
    
    /**
     * 根据产品编号获取产品数量.
     * @param productId
     * @return
     */
    int getProductStockCount(Integer productId);
    
    /**
     * 获取统计库存信息.
     * @param userId
     * @return
     */
    List<ImportStock> getStatisticsStocks(Integer userId);
}