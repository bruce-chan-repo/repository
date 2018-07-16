package com.ytem.repository.service;

import java.util.List;

import com.ytem.repository.bean.Product;
import com.ytem.repository.common.PageInfoExt;

/**
 * 商品的业务接口
 * @author 陈康敬💪
 * @date 2018年5月17日上午9:20:01
 * @description
 */
public interface ProductService {
	/**
	 * 获取商品列表
	 * @param condition 条件
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示的数量.
	 * @return
	 */
	PageInfoExt<Product> getProducts(Product condition, Integer pageNum, Integer pageSize);
	
	/**
	 * 添加商品信息
	 * @param product
	 * @return
	 */
	int addProduct(Product product);
	
	/**
	 * 修改商品信息
	 * @param product
	 * @return
	 */
	int editProduct(Product product);
	
	/**
	 * 批量删除商品信息.
	 * @param productIds
	 * @return
	 */
	int deleteProducts(String productIds);
	
	/**
	 * 根据商品编号获取商品信息.
	 * @param id
	 * @return
	 */
	Product getProductById(Integer id);
	
	/**
	 * 根据产品编号获取产品信息.
	 * @param code
	 * @return
	 */
	Product getProductByCode(String code, Integer id);
	
	/**
	 * 获取所有产品信息.
	 * @return
	 */
	List<Product> getProducts();
}
