package com.ytem.repository.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.ytem.repository.bean.Product;
import com.ytem.repository.common.Const;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.dao.ProductMapper;
import com.ytem.repository.service.ProductService;

/**
 * 商品的业务实现类
 * @author 陈康敬💪
 * @date 2018年5月17日上午9:20:42
 * @description
 */
@Service("productService")
public class ProductServiceImpl implements ProductService{
	private final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductMapper productMapper;
	
	@Override
	public PageInfoExt<Product> getProducts(Product condition, Integer pageNum, Integer pageSize) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有产品信息.|";
		logger.debug(opreation + ".|开始");
		
		PageHelper.startPage(pageNum, pageSize);
		List<Product> productList = productMapper.selectProducts(condition);
		
		PageInfoExt<Product> pageInfo = new PageInfoExt<>(productList);
		
		logger.debug(opreation + ".|结束");
		return pageInfo;
	}
	
	@Override
	public int addProduct(Product product) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|添加产品信息.|";
		logger.debug(opreation + ".|开始");
	
		int row = productMapper.insert(product);
		
		logger.debug(opreation + ".|结束");
		return row;
	}
	
	@Override
	public int editProduct(Product product) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|修改产品信息.|";
		logger.debug(opreation + ".|开始");
	
		int row = productMapper.updateByPrimaryKeySelective(product);
		
		logger.debug(opreation + ".|结束");
		return row;
	}
	
	@Override
	public int deleteProducts(String productIds) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|删除产品信息.|";
		logger.debug(opreation + ".|开始");
	
		int row = productMapper.batchDelete(productIds);
		
		logger.debug(opreation + ".|结束");
		return row;
	}
	
	@Override
	public Product getProductById(Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|根据产品编号获取产品信息.|";
		logger.debug(opreation + ".|开始");
	
		Product product = productMapper.selectByPrimaryKey(id);
		
		logger.debug(opreation + ".|结束");
		return product;
	}
	
	@Override
	public Product getProductByCode(String code, Integer id) {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|根据产品编号获取产品信息.|";
		logger.debug(opreation + ".|开始");
	
		Product product = productMapper.selectByCode(code, id);
		
		logger.debug(opreation + ".|结束");
		return product;
	}
	
	@Override
	public List<Product> getProducts() {
		String opreation = Const.LOGGER_PREFIX_DEBUG + "THREADID = " + Thread.currentThread().getId() + ".|获取所有产品信息.|";
		logger.debug(opreation + ".|开始");
		
		List<Product> productList = productMapper.selectProducts(null);
		
		logger.debug(opreation + ".|结束");
		return productList;
	}
}
