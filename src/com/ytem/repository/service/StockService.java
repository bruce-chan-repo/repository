package com.ytem.repository.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ytem.repository.bean.ImportStocksPack;
import com.ytem.repository.bean.Stock;
import com.ytem.repository.common.PageInfoExt;

/**
 * 库存接口
 * @author 陈康敬💪
 * @date 2018年5月28日上午9:03:17
 * @description
 */
public interface StockService {
	/**
	 * 查询库存信息.
	 * @param condition 查询条件
	 * @param pageNum 当前页
	 * @param pageSize 总页数
	 * @return
	 */
	PageInfoExt<Stock> getStocks(Stock condition, Integer pageNum, Integer pageSize);
	
	/**
	 * 添加库存信息.
	 * @param stock
	 * @return
	 */
	int addStock(Stock stock);
	
	/**
	 * 根据条件获取库存信息.
	 * @param stock
	 * @return
	 */
	Stock getStockByCondition(Stock condition);
	
	/**
	 * 获取所有的库存信息.
	 * @return
	 */
	List<Stock> getStocks(Integer userId);
	
	/**
	 * 批量添加
	 * @param list
	 * @return
	 */
    int batchInsert(List<Stock> list);
    
    /**
     * 获取需要采购的产品
     * @return
     */
    List<Stock> getNeedPurchase();
    
    /**
     * 入库操作.
     * @param stocksPack
     * @return
     */
    int intoRepository(ImportStocksPack stocksPack, Integer userId);
    
    /**
     * 修改库存信息.
     * @param stock
     * @return
     */
    int updateStock(Stock stock);
    
    /**
     * 删除库存信息.
     * @param stockIds
     * @return
     */
    int deleteStocks(String stockIds);
    
    /**
     * 获取某个产品的库存数量.
     * @param productCode
     * @return
     */
    Stock getStockCountByProductCode(String productCode, Integer userId, String productName);
    
    /**
     * 根据产品编号获取产品数量.
     * @param productId
     * @return
     */
    int getProductStockCount(Integer productId);
    
    /**
     * 获取库存统计信息.
     * @param userId
     * @return
     */
    List<Stock> getStatisticsStocks(Integer userId);
    
    /**
     * 根据分页获取库存统计信息
     * @param condition
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageInfo<Stock> getStatisticsByPage(Stock condition, Integer pageNo, Integer pageSize);
}
