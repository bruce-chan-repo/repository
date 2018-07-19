package com.ytem.repository.controller;

import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ytem.repository.bean.Product;
import com.ytem.repository.bean.Stock;
import com.ytem.repository.bean.User;
import com.ytem.repository.common.JsonResult;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.common.ResponseCode;
import com.ytem.repository.service.ProductService;
import com.ytem.repository.service.StockService;
import com.ytem.repository.service.UserService;
import com.ytem.repository.utils.ImportHanler;

/**
 * 库存控制层
 * @author 陈康敬💪
 * @date 2018年5月23日下午9:34:51
 * @description
 */
@Controller
@RequestMapping("stock")
public class StockController {
	private final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 跳转到库存表
	 * @return
	 */
	@RequestMapping("toStocks.do")
	public ModelAndView toStocks(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/stock/stocks");
		
		// 查询所有的客户.
		List<User> clients = userService.getAllClients();
		
		// 获取当前登录的用户.
		Subject subject = SecurityUtils.getSubject();
		String username = subject.getPrincipal().toString();
		
		// 获取用户信息
		User currUser = (User) request.getSession().getAttribute(username);
		
		mv.addObject("clients", clients);
		mv.addObject("currUser", currUser);
		
		return mv;
	}
	
	/**
	 * 获取库存的分页信息.
	 * @return
	 */
	@RequestMapping("stocks.do")
	public ModelAndView getStocks(Stock condition, @RequestParam(value = "curpage", defaultValue = "1") Integer pageNum, 
			@RequestParam(value = "limit", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/stock/stocksData");
			
		// 获取当前登录的用户.
		Subject subject = SecurityUtils.getSubject();
		String username = subject.getPrincipal().toString();
		
		// 获取用户信息
		User currUser = (User) request.getSession().getAttribute(username);
		
		// 如果是客户登录的话，需要查询自己的订单列表.
		if (currUser.getRole().getId() != 1) {
			condition.setUserId(currUser.getId());
		}
		
		PageInfoExt<Stock> pageInfo = stockService.getStocks(condition, pageNum, pageSize);
		
		mv.addObject("pageInfo", pageInfo);
		return mv;
	}
	
	/**
	 * 跳转到添加库存页面
	 * @return
	 */
	@RequestMapping("toAddStock.do")
	public ModelAndView toAddStock(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/stock/addStock");
		
		// 查询产品信息.
		List<Product> products = productService.getProducts();
		
		// 查询所有的客户.
		List<User> clients = userService.getAllClients();
		
		// 获取当前登录的用户.
		Subject subject = SecurityUtils.getSubject();
		String username = subject.getPrincipal().toString();
		
		// 获取用户信息
		User currUser = (User) request.getSession().getAttribute(username);
		
		mv.addObject("currUser", currUser);
		mv.addObject("products", products);
		mv.addObject("clients", clients);
		return mv;
	}
	
	/**
	 * 添加库存信息
	 * @param stock
	 * @return
	 */
	@RequestMapping("addStock.do")
	@ResponseBody
	public JsonResult addStock(Stock stock, HttpServletRequest request) {
		JsonResult result;
		
		try {
			// 获取当前登录的用户.
			Subject subject = SecurityUtils.getSubject();
			String username = subject.getPrincipal().toString();
			
			// 获取用户信息
			User currUser = (User) request.getSession().getAttribute(username);
			
			// 如果是客户登录的话，需要查询自己的订单列表.
			if (currUser.getRole().getId() != 1) {
				stock.setUserId(currUser.getId());
			}
			
			
			int row = stockService.addStock(stock);
			if (row > 0) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "添加产品成功");
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "添加产品失败");
			}
		} catch (Exception e) {
			logger.error("异常信息-添加库存信息失败");
			result = new JsonResult(ResponseCode.ERROR.getCode(), "添加产品失败");
		}
		
		return result;
	}
	
	/**
	 * 验证序列号是否在库存中出现
	 * @return
	 */
	@RequestMapping("checkSequenceIsUseable.do")
	@ResponseBody
	public JsonResult checkSequenceIsUseable(Stock condition) {
		JsonResult result;
		
		try {
			// 空序列号不做验证，直接返回
			if (condition != null && StringUtils.isBlank(condition.getSequence())) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "序列号可用");	
				return result;
			}
			
			Stock stock = stockService.getStockByCondition(condition);
			if (stock == null) {
				result = new JsonResult(ResponseCode.SUCCESS.getCode(), "序列号可用");	
			} else {
				result = new JsonResult(ResponseCode.ERROR.getCode(), "序列号不可用");
			}
		} catch (Exception e) {
			logger.error("异常信息-添加库存信息失败");
			result = new JsonResult(ResponseCode.ERROR.getCode(), "验证库存中是否存在相同的序列号失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到导入库存信息页面.
	 * @return
	 */
	@RequestMapping("toImportStock.do")
	public ModelAndView toImportStock() {
		ModelAndView mv = new ModelAndView("/stock/importStock");
		return mv;
	}
	
	/**
	 * 导入库存Excel.
	 * @param filePath
	 * @return
	 */
	@RequestMapping("importStocks.do")
	@ResponseBody
	public JsonResult importStocks(String filePath) {
		JsonResult result;
		
		// 参数校验.
		if (StringUtils.isBlank(filePath)) {
			result = new JsonResult(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		try {
			ImportHanler importHanler = new ImportHanler();
			importHanler.importStocks(filePath, productService, stockService);
			
			result = new JsonResult(ResponseCode.ERROR.getCode(), "导入成功");
		} catch (Exception e) {
			logger.error("异常信息-导入库存表失败", e);
			result = new JsonResult(ResponseCode.ERROR.getCode(), "导入失败");
		}
		
		return result;
	}
	
	/**
	 * 获取需要购买的清单.
	 * @param request
	 * @return
	 */
	@RequestMapping("getNeedPurchase.do")
	@ResponseBody
	public ModelAndView getNeedPurchase(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("stock/showNeedPurchase");
		
		List<Stock> needPurchase = stockService.getNeedPurchase();
		
		mv.addObject("needPurchase", needPurchase);
		return mv;
	}
	
	/**
	 * 跳转到备注页面.
	 * @param stockId
	 * @return
	 */
	@RequestMapping(value = "toRemark.do", method = RequestMethod.GET)
	public ModelAndView toRemark(Stock stock) {
		ModelAndView mv = new ModelAndView("stock/editRemark");
		
		// 查询当前库存信息.
		Stock stockVo = stockService.getStockByCondition(stock);
		
		mv.addObject("stock", stockVo);
		return mv;
	}
	
	/**
	 * 备注库存产品.
	 * @param stock
	 * @return
	 */
	@RequestMapping("remark.do")
	@ResponseBody
	public JsonResult<Stock> editRemark(Stock stock) {
		JsonResult result;
		
		// 参数校验.
		if (stock == null) {
			result = new JsonResult<>(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		try {
			// 修改库存备注信息.
			int row = stockService.updateStock(stock);
			if (row > 0) {
				result = new JsonResult<>(ResponseCode.SUCCESS.getCode(), "修改备注成功");
			} else {
				result = new JsonResult<>(ResponseCode.ERROR.getCode(), "修改备注失败");
			}
		} catch (Exception e) {
			logger.error("异常信息-备注库存信息异常", e);
			result = new JsonResult<>(ResponseCode.ERROR.getCode(), "修改备注失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到编辑页面.
	 * @param stock
	 * @return
	 */
	@RequestMapping("toEditStock.do")
	public ModelAndView toEditStock(Stock stock) {
		ModelAndView mv = new ModelAndView("/stock/addStock");
		
		// 查询库存信息.
		Stock stockVo = stockService.getStockByCondition(stock);
		
		mv.addObject("stock", stockVo);
		return mv;
	}
	
	/**
	 * 修改库存信息.
	 * @param stock
	 * @return
	 */
	@RequestMapping("editStock.do")
	@ResponseBody
	public JsonResult<Stock> editStock(Stock stock) {
		JsonResult result;
		
		// 参数校验.
		if (stock == null) {
			result = new JsonResult<>(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		try {
			// 修改库存备注信息.
			int row = stockService.updateStock(stock);
			if (row > 0) {
				result = new JsonResult<>(ResponseCode.SUCCESS.getCode(), "修改库存信息成功");
			} else {
				result = new JsonResult<>(ResponseCode.ERROR.getCode(), "修改库存信息失败");
			}
		} catch (Exception e) {
			logger.error("异常信息-修改库存信息异常", e);
			result = new JsonResult<>(ResponseCode.ERROR.getCode(), "修改库存信息失败");
		}
		
		return result;
	}
	
	/**
	 * 删除库存信息.
	 * @param stockIds
	 * @return
	 */
	@RequestMapping("deleteStocks.do")
	@ResponseBody
	public JsonResult<Stock> deleteStocks(String stockIds) {
		JsonResult result;
		
		// 参数校验.
		if (StringUtils.isBlank(stockIds)) {
			result = new JsonResult<>(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		try {
			// 执行删除操作.
			int row = stockService.deleteStocks(stockIds);
			
			if (row > 0) {
				result = new JsonResult<>(ResponseCode.SUCCESS.getCode(), "删除库存信息成功");
			} else {
				result = new JsonResult<>(ResponseCode.ERROR.getCode(), "缺少参数");
			}
		} catch (Exception e) {
			logger.error("异常信息-删除库存信息异常", e);
			result = new JsonResult<>(ResponseCode.ERROR.getCode(), "删除库存信息失败");
		}
		
		return result;
	}
	
	
	/**
	 * 导出库存信息.
	 * @param request
	 * @param response
	 */
	@RequestMapping("export")
	public void exportStock(HttpServletRequest request, HttpServletResponse response) {
		response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/ms-excel");
        response.setHeader("Content-type", "application/x-msexcel");
        
        try {
        	ServletOutputStream sos = response.getOutputStream();
        	
            // 设置文件名称，因为header中不能包含中文，所以需要转换使用URLEncoder转换.
            String fileName = URLEncoder.encode("库存信息", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            
            // 获取数据
            List<Stock> stocks = stockService.getStocks();
            
            // 创建工作簿
            HSSFWorkbook wb = new HSSFWorkbook();
            
            // 向excel中构建数据.
            buildExcel(wb, stocks);
            
            wb.write(sos);
            sos.flush();
            sos.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常信息-导出库存信息异常", e);
		}
	}
	
	/**
	 * 构建导出的excel
	 * @param workbook
	 */
	private void buildExcel(HSSFWorkbook workbook, List<Stock> stocks) {
		// 创建sheet
		HSSFSheet sheet = workbook.createSheet("商品库存.");
		
		// 构建表头
		String[] coloums = {"型号,prodcut.productName", "产品代码,product.productCode", "序列号,sequence", "批次号,batchNumber", 
							"数量,quantity", "入库日期,createTime"};
		
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < coloums.length; i++) {
			String value = coloums[i].split(",")[0];
			
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(value);
		}
		
		// 构建列
		int size = stocks == null ? 0 : stocks.size();
		for (int i = 0; i < size; i++) {
			HSSFRow tempRow = sheet.createRow(i + 1);
			Stock tempStock = stocks.get(i);
			
			for (int j = 0; j < coloums.length; j++) {
				String property = coloums[j].split(",")[1];
				String value = "";
				try {
					value = BeanUtils.getProperty(tempStock, property);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					e.printStackTrace();
				}
				
				HSSFCell cell = tempRow.createCell(i);
				cell.setCellValue(value);
			}
		}
	}
}
