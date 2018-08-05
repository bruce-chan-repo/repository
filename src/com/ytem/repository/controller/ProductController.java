package com.ytem.repository.controller;

import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ytem.repository.bean.Product;
import com.ytem.repository.common.JsonResult;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.common.ResponseCode;
import com.ytem.repository.service.ProductService;
import com.ytem.repository.service.StockService;

/**
 * 产品控制层.
 * @author 陈康敬💪
 * @date 2018年5月23日上午12:33:41
 * @description
 */
@Controller
@RequestMapping("product")
public class ProductController {
	private final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private StockService stockService;
	
	
	/**
	 * 跳转到商品列表页
	 * @return
	 */
	@RequestMapping("toProducts.do")
	public ModelAndView toProducts() {
		return new ModelAndView("product/products");
	}
	
	/**
	 * 获取商品列表
	 * @return
	 */
	@RequestMapping("products.do")
	public ModelAndView selectUsers(Product condition, @RequestParam(value = "curpage", defaultValue = "1") Integer pageNum, 
				@RequestParam(value = "limit", defaultValue = "10") Integer pageSize) {
		ModelAndView mv = new ModelAndView("product/productsData");
		
		// 查询商品信息
		PageInfoExt<Product> pageInfo = productService.getProducts(condition, pageNum, pageSize);
		
		mv.addObject("pageInfo", pageInfo);
		return mv;
	}
	
	/**
	 * 跳转到添加商品页面
	 * @return
	 */
	@RequestMapping("toAddProduct.do")
	public ModelAndView toAddProduct() {
		ModelAndView mv = new ModelAndView("product/addProduct");
		return mv;
	}
	
	/**
	 * 添加商品信息
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "addProduct.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addProduct(Product product) {
		JsonResult result;
		
		try {
			// 执行添加操作.
			int row = productService.addProduct(product);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "添加产品成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "添加产品失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加产品信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "添加产品失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到编辑页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "toEditProduct.do")
	public ModelAndView toEditProduct(Integer id) {
		ModelAndView mv = new ModelAndView("product/addProduct");
		
		// 获取商品信息.
		Product product = productService.getProductById(id);
		
		mv.addObject("product", product);
		return mv;
	}
	
	/**
	 * 修改商品信息
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "editProduct.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult editProduct(Product product) {
		JsonResult result;
		
		try {
			// 执行修改操作.
			int row = productService.editProduct(product);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "修改产品成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "修改产品失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加产品信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "修改产品失败");
		}
		
		return result;
	}
	
	/**
	 * 删除商品信息
	 * @param product
	 * @return
	 */
	@RequestMapping(value = "deleteProducts.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deleteProducts(String productIds) {
		JsonResult result;
		
		try {
			// 校验参数
			if (StringUtils.isBlank(productIds)) {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
				return result;
			}
			
			// 先校验该商品是否有引用的库存
			StringTokenizer tokenizer = new StringTokenizer(productIds, ",");
			while (tokenizer.hasMoreElements()) {
				String productId = (String) tokenizer.nextElement();
				int count = stockService.getProductStockCount(Integer.parseInt(productId));
				
				if (count > 0) {
					result = new JsonResult(ResponseCode.ERROR.getCode(), "删除的产品中还存在库存，不可删除");
					return result;
				}
			}
			
			// 执行删除操作.
			int row = productService.deleteProducts(productIds);
			
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "删除产品成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "删除产品失败");
			}
		} catch (Exception e) {
			logger.error("系统异常-添加产品信息", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "删除产品失败");
		}
		
		return result;
	}
	
	/**
	 * 验证产品编码是否合法， 编辑时排除当前产品.
	 * @param code
	 * @param id
	 * @return
	 */
	@RequestMapping("checkCodeIsUsable.do")
	@ResponseBody
	public JsonResult checkCodeIsUsable(String code, Integer id) {
		JsonResult result;
		
		// 校验参数是否合法
		if (StringUtils.isBlank(code)) {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		// 如果产品编码是 -,直接返回可用
		if ("-".equals(code)) {
			result = new JsonResult(ResponseCode.SUCCESS.getCode(), "产品编号可用");
			return result;
		}
		
		// 根据产品编号查看该code是否有引用的产品.
		Product product = productService.getProductByCode(code, id);
		if (product == null) {
			result = new JsonResult(ResponseCode.SUCCESS.getCode(), "产品编号可用");
		} else {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "产品编号不可用");
		}
		
		return result;
	}
	
}
