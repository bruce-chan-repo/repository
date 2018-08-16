package com.ytem.repository.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ytem.repository.bean.ImportStocksPack;
import com.ytem.repository.bean.Product;
import com.ytem.repository.bean.Stock;
import com.ytem.repository.common.Const;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.dao.ProductMapper;
import com.ytem.repository.dao.StockMapper;
import com.ytem.repository.service.StockService;

/**
 * 库存业务实现.
 * @author 陈康敬💪
 * @date 2018年5月28日上午9:04:10
 * @description
 */
@Service("stockService")
public class StockSerivceImpl implements StockService {
	private Logger logger = Logger.getLogger(StockSerivceImpl.class);
	
	@Autowired
	private StockMapper stockMapper;
	
	@Autowired
	private ProductMapper productMapper;

	
	@Override
	public PageInfoExt<Stock> getStocks(Stock condition, Integer pageNum, Integer pageSize) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有库存信息.|";
		logger.debug(opreation + ".|开始");
		
		// 开启分页
		PageHelper.startPage(pageNum, pageSize);
		
		// 获取分页数据.
		List<Stock> stocks = stockMapper.selectStocks(condition);
		PageInfoExt<Stock> pageInfo = new PageInfoExt<>(stocks);
		
		logger.debug(opreation + ".|结束");
		return pageInfo;
	}

	@Override
	public int addStock(Stock stock) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有库存信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = stockMapper.insertSelective(stock);
		
		logger.debug(opreation + ".|结束");
		return result;
	}

	@Override
	public Stock getStockByCondition(Stock condition) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|根据条件获取库存信息.|";
		logger.debug(opreation + ".|开始");
		
		Stock stock = stockMapper.selectByCondition(condition);
		
		logger.debug(opreation + ".|结束");
		return stock;
	}

	@Override
	public List<Stock> getStocks(Integer userId) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有的库存信息.|";
		logger.debug(opreation + ".|开始");
		
		List<Stock> selectStocks = stockMapper.getStocks(userId);
		
		logger.debug(opreation + ".|结束");
		return selectStocks;
	}

	@Override
	public int batchInsert(List<Stock> list) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|批量添加库存信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = stockMapper.batchInsert(list);
		
		logger.debug(opreation + ".|结束");
		return result;
	}

	@Override
	public List<Stock> getNeedPurchase() {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取需要采购的产品清单.|";
		logger.debug(opreation + ".|开始");
		
		List<Stock> stocks = stockMapper.selectNeedPurchase();
		
		logger.debug(opreation + ".|结束");
		return stocks;
	}

	@Override
	public int intoRepository(ImportStocksPack stocksPack, Integer userId) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取需要采购的产品清单.|";
		logger.debug(opreation + ".|开始");
		int result = 0;
		
		// 获取已经存在的商品信息
		Map<String, Product> existsProducts = getExistsProducts();
		
		// 获取已经存在的库存信息.
		Map<String, Stock> existsStocks = getExistsStocks();
		
		// 获取已经存在的库存信息.
		Map<String, Stock> existsStocksForParts = getExistsStocksForParts();
		
		// 最终添加的库存.
		List<Stock> finalImportStocks = new ArrayList<>();
		//List<Stock> finalUpdateStocks = new ArrayList<>();
		
		// 遍历所有对象
		List<Stock> stocks = stocksPack.getStocks();
		int size = stocks.size();
		for (int i = 0; i < size; i++) {
			Stock tempStock = stocks.get(i);
			
			// 判断当前产品是否存在.
			Product product = existsProducts.get(tempStock.getProductCode());
			if (product == null) {
				product = new Product();
				
				product.setProductCode(tempStock.getProductCode());
				product.setProductName(tempStock.getProductName());
				
				productMapper.insert(product);
				existsProducts.put(product.getProductCode(), product);
			}
			
			String sequence = tempStock.getSequence();
			if (StringUtils.isNotBlank(sequence) && !"-".equals(sequence)) {
				// 判断当前导入的库存是否存在.
				Stock stock = existsStocks.get(tempStock.getProductCode() + tempStock.getSequence());
				if (stock == null) {
					Stock addStock = new Stock();
					
					try {
						BeanUtils.copyProperties(addStock, tempStock);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					addStock.setProductId(product.getId());
					addStock.setUserId(userId);
					
					finalImportStocks.add(addStock);
					//stockMapper.addStock(addStock);
					
					// 追加到已经存在的map中.
					existsStocks.put(tempStock.getProductCode() + tempStock.getSequence(), addStock);
				}
			} else {
				// 配件.
				Stock stock = existsStocksForParts.get(tempStock.getProductCode());
				if (stock == null) {
					Stock addStock = new Stock();
					
					try {
						BeanUtils.copyProperties(addStock, tempStock);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
					addStock.setProductId(product.getId());
					addStock.setUserId(userId);
					
					//finalImportStocks.add(addStock);
					stockMapper.addStock(addStock);
					
					// 追加到已经存在的map中.
					existsStocksForParts.put(tempStock.getProductCode(), addStock);
				} else {
					// 修改
					Integer oldQuantity = stock.getQuantity();
					Integer newQuantity = oldQuantity + tempStock.getQuantity();
					
					Stock updateStock = new Stock();
					updateStock.setQuantity(newQuantity);
					updateStock.setId(stock.getId());
					
					// 实时更新内存中对象的库存信息.
					stock.setQuantity(newQuantity);
					
					stockMapper.updateByPrimaryKeySelective(updateStock);
				}
			}
		}
		// finalImportStocks不为空时,批量插入.
		if (CollectionUtils.isNotEmpty(finalImportStocks)) {
			result = stockMapper.batchInsert(finalImportStocks);
		}
		
		/*// 批量修改
		for (int i = 0; i < finalUpdateStocks.size(); i++) {
			stockMapper.updateByPrimaryKeySelective(finalUpdateStocks.get(i));
		}*/
		
		logger.debug(opreation + ".|结束");
		return result;
	}
	
	
	/**
	 * 获取已经存在的商品.
	 * @return
	 */
	private Map<String, Product> getExistsProducts() {
		Map<String, Product> map = new HashMap<>();
		
		List<Product> products = productMapper.selectProducts(null);
		int size = products.size();
		
		for (int i = 0; i < size; i++) {
			Product tempProduct = products.get(i);
			map.put(tempProduct.getProductCode(), tempProduct);
		}
		
		return map;
	}
	
	/**
	 * 获取已经存在的库存信息.
	 * @return
	 */
	private Map<String, Stock> getExistsStocks() {
		Map<String, Stock> map = new HashMap<>();
		
		List<Stock> stocks = stockMapper.getStocks(null);
		int size = stocks.size();
		
		for (int i = 0; i < size; i++) {
			Stock stock = stocks.get(i);
			map.put(stock.getProductCode() + stock.getSequence(), stock);
		}
		
		return map;
	}
	
	/**
	 * 获取已经存在的库存信息.
	 * @return
	 */
	private Map<String, Stock> getExistsStocksForParts() {
		Map<String, Stock> map = new HashMap<>();
		
		List<Stock> stocks = stockMapper.getStocks(null);
		int size = stocks.size();
		
		for (int i = 0; i < size; i++) {
			Stock stock = stocks.get(i);
			map.put(stock.getProduct().getProductCode(), stock);
		}
		
		return map;
	}

	@Override
	public int updateStock(Stock stock) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改库存信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = stockMapper.updateByPrimaryKeySelective(stock);
		
		logger.debug(opreation + ".|结束");
		return result;
	}

	@Override
	public Stock getStockCountByProductCode(String productCode, Integer userId, String productName) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改库存信息.|";
		logger.debug(opreation + ".|开始");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("productCode", productCode);
		paramMap.put("userId", userId);
		paramMap.put("productName", productName);
		
		Stock stock;
		// 如果没有库存编号则按商品名称查询
		if ("".equals(productCode) || "-".equals(productCode)) {
			stock = stockMapper.getStockCountByProductName(paramMap);
		} else {
			int stockNumber = 0;
			List<Integer> quantitys = stockMapper.getStockCountByProductCode(paramMap);
			if (quantitys != null && quantitys.size() > 1) {
				stockNumber = quantitys.size();
			} else if (quantitys != null && quantitys.size() == 1) {
				stockNumber = quantitys.get(0);
			}
			
			stock = new Stock();
			stock.setQuantity(stockNumber);
		}
		
		logger.debug(opreation + ".|结束");
		return stock;
	}

	@Override
	public int deleteStocks(String stockIds) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改库存信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = stockMapper.batchDelete(stockIds);
		
		logger.debug(opreation + ".|结束");
		return result;
	}

	@Override
	public int getProductStockCount(Integer productId) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改库存信息.|";
		logger.debug(opreation + ".|开始");
		
		int result = stockMapper.getProductStockCount(productId);
		
		logger.debug(opreation + ".|结束");
		return result;
	}
}
