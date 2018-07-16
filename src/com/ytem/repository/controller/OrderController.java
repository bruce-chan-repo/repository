package com.ytem.repository.controller;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytem.repository.bean.ImportStock;
import com.ytem.repository.bean.ImportStocksPack;
import com.ytem.repository.bean.Order;
import com.ytem.repository.bean.OrderItem;
import com.ytem.repository.bean.User;
import com.ytem.repository.common.JsonResult;
import com.ytem.repository.common.PageInfoExt;
import com.ytem.repository.common.ResponseCode;
import com.ytem.repository.service.OrderService;
import com.ytem.repository.service.StockService;
import com.ytem.repository.service.UserService;
import com.ytem.repository.utils.ImportHanler;

/**
 * 订单控制层
 * @author 陈康敬💪
 * @date 2018年5月23日下午9:34:51
 * @description
 */
@Controller
@RequestMapping("order")
public class OrderController {
	private final Logger logger = Logger.getLogger(UserController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private StockService stockService;
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 跳转到商品销售页面.
	 * @return
	 */
	@RequestMapping("toSell.do")
	public ModelAndView toSell() {
		ModelAndView mv = new ModelAndView("order/sell");
		
		// 查询所有客户信息.
		List<User> clients = userService.getAllClients();
		
		mv.addObject("clients", clients);
		return mv;
	}
	
	@RequestMapping("toRead.do")
	public ModelAndView toRead(String method, Integer userId) {
		ModelAndView mv = new ModelAndView("order/readOrder");
		mv.addObject("method", method);
		mv.addObject("userId", userId);
		return mv;
	}
	
	/**
	 * 跳转到商品订单列表
	 * @return
	 */
	@RequestMapping("toOrders.do")
	public ModelAndView toStocks(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/order/orders");
		
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
	 * 获取商品订单的分页信息.
	 * @return
	 */
	@RequestMapping("orders.do")
	public ModelAndView getStocks(Order condition, @RequestParam(value = "curpage", defaultValue = "1") Integer pageNum, 
			@RequestParam(value = "limit", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/order/ordersData");
			
		// 获取当前登录的用户.
		Subject subject = SecurityUtils.getSubject();
		String username = subject.getPrincipal().toString();
		
		// 获取用户信息
		User currUser = (User) request.getSession().getAttribute(username);
		
		// 如果是客户登录的话，需要查询自己的订单列表.
		if (currUser.getRole().getId() != 1) {
			condition.setUserId(currUser.getId());
		}
		
		// 获取订单列表.
		PageInfoExt<Order> pageInfo = orderService.getOrders(condition, pageNum, pageSize);
		
		mv.addObject("pageInfo", pageInfo);
		return mv;
	}
	
	/**
	 * 查看订单详情
	 * @return
	 */
	@RequestMapping("detail.do")
	public ModelAndView searchDeatil(Integer id) {
		ModelAndView mv = new ModelAndView("/order/detail");
		
		Order order = orderService.getOrderById(id);
		
		mv.addObject("order", order);
		return mv;
	}
	
	/**
	 * 商品入库操作.
	 * @param stockPack
	 * @return
	 */
	@RequestMapping("intoRepository.do")
	@ResponseBody
	public JsonResult intoRepository(String stockPackJson) {
		JsonResult<Order> result;
		
		// 参数校验.
		if (StringUtils.isEmpty(stockPackJson)) {
			result = new JsonResult<Order>(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ImportStocksPack stockPack = objectMapper.readValue(stockPackJson, ImportStocksPack.class);
			
			// Maybe haven't insert data. So here don't judge.
			stockService.intoRepository(stockPack, stockPack.getStocks().get(0).getUserId());
			
			result = new JsonResult<Order>(ResponseCode.SUCCESS.getCode(), "导入成功");
		} catch (Exception e) {
			logger.error("异常信息-商品入库失败", e);
			result = new JsonResult<Order>(ResponseCode.ERROR.getCode(), "导入失败");
		}
		
		return result;
	}
	
	/**
	 * 合并订单信息
	 * @param order
	 */
	@RequestMapping("mergeOrder.do")
	public void mergeOrder(Order order, HttpServletRequest request, HttpServletResponse response) {
		response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/ms-excel");
        response.setHeader("Content-type", "application/x-msexcel");
        
        try {
        	ServletOutputStream sos = response.getOutputStream();
        	
        	// 获取源文件名称.
        	String orderFile = order.getOrderFile();
        	String excelFileName = order.getOrderFileName();
        	
            // 设置文件名称，因为header中不能包含中文，所以需要转换使用URLEncoder转换.
            String fileName = URLEncoder.encode(excelFileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            
            File excelFile = new File(orderFile);
    		FileInputStream is = new FileInputStream(excelFile);
    		
    		Workbook wb = WorkbookFactory.create(is);
    		Sheet sheet = wb.getSheetAt(0);
    		// 填充数据
    		List<OrderItem> orderItems = order.getOrderItems();
    		int size = orderItems.size();
    		for (int i = 0; i < size; i++) {
    			OrderItem item = orderItems.get(i);
    			
    			Row row = sheet.getRow(i + 2);
    			Cell cell = row.getCell(3);
    			cell.setCellValue(item.getProductSequence());
    		}
    		// 填充备注
    		int maxRow = sheet.getLastRowNum();
    		
    		Row newRow = sheet.createRow(maxRow + 1);
    		
    		Cell newCell = newRow.createCell(2);
    		newCell.setCellValue(order.getRemark());
    		newRow.createCell(3);

    		// 合并单元格
    		sheet.addMergedRegion(new CellRangeAddress(maxRow, maxRow, 2, 3));
    		
    		// 处理订单信息
    		orderService.saveOrder(order);
    		
    		// TODO 删除数量为0的订单信息
    		
            
            wb.write(sos);
            sos.flush();
            sos.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("异常信息—合并订单失败", e);
        }
	}
	
	/**
	 * 读取订单信息.
	 * @param filePath
	 * @return
	 */
	@RequestMapping("readOrder.do")
	@ResponseBody
	public JsonResult<Order> importStocks(String filePath, Integer userId) {
		JsonResult<Order> result;
		
		// 参数校验.
		if (StringUtils.isBlank(filePath)) {
			result = new JsonResult<Order>(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		try {
			ImportHanler importHanler = new ImportHanler();
			Order readOrder = importHanler.readOrder(filePath, stockService, userId);
			readOrder.setOrderFile(filePath);
			
			result = new JsonResult<Order>(ResponseCode.SUCCESS.getCode(), "导入成功", readOrder);
		} catch (Exception e) {
			logger.error("异常信息-导入库存表失败", e);
			result = new JsonResult<Order>(ResponseCode.ERROR.getCode(), "导入失败");
		}
		
		return result;
	}
	
	/**
	 * 上传入库单.
	 * @param filePath
	 * @return
	 */
	@RequestMapping("readIntoRepository.do")
	@ResponseBody
	public JsonResult<ImportStocksPack> importIntoRepository(String filePath) {
		JsonResult<ImportStocksPack> result;
		
		// 参数校验.
		if (StringUtils.isBlank(filePath)) {
			result = new JsonResult<ImportStocksPack>(ResponseCode.ERROR.getCode(), "缺少参数");
			return result;
		}
		
		try {
			ImportHanler importHanler = new ImportHanler();
			List<ImportStock> stockList = importHanler.readIntoOrder(filePath);
			
			ImportStocksPack pack = new ImportStocksPack();
			pack.setImportStocks(stockList);
			
			result = new JsonResult<ImportStocksPack>(ResponseCode.SUCCESS.getCode(), "导入成功", pack);
		} catch (Exception e) {
			logger.error("异常信息-导入库存表失败", e);
			result = new JsonResult<ImportStocksPack>(ResponseCode.ERROR.getCode(), "导入失败");
		}
		
		return result;
	}
	
	/**
	 * 跳转到添加入库
	 * @return
	 */
	@RequestMapping("toIntoRepository.do")
	@ResponseBody
	public ModelAndView toIntoRepository() {
		ModelAndView mv = new ModelAndView("order/intoRepository");
		
		// 查询所有客户信息.
		List<User> clients = userService.getAllClients();
		
		mv.addObject("clients", clients);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {  
	    // 设置List的最大长度  
	    binder.setAutoGrowCollectionLimit(10000);  
	}  
}
