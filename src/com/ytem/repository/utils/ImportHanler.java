package com.ytem.repository.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import com.ytem.repository.bean.ImportStock;
import com.ytem.repository.bean.Order;
import com.ytem.repository.bean.OrderItem;
import com.ytem.repository.bean.Product;
import com.ytem.repository.bean.Stock;
import com.ytem.repository.service.ProductService;
import com.ytem.repository.service.StockService;

/**
 * 导入Excel处理类
 * @author 陈康敬💪
 * @date 2018年6月5日下午6:04:10
 * @description
 */
@Component
public class ImportHanler {
	private final String OLD_EXCEL_EXT = "xls";
	
	private final String NEW_EXCEL_EXT = "xlsx";
	
	private FormulaEvaluator evaluator;
	
	/**
	 * 导入库存.
	 * @param filePath Excel文件.
	 * @throws IOException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@Deprecated
	public void importStocks(String filePath, ProductService productService, StockService stockService) throws IOException, IllegalAccessException, InvocationTargetException {
		File excelFile = new File(filePath);
		FileInputStream is = new FileInputStream(excelFile);
		
		Workbook wb = new HSSFWorkbook(is);
		HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(0);
		
		List<ImportStock> importStocks = new ArrayList<>();
		
		int lastRowNum = sheet.getLastRowNum();
		// 遍历解析出的所有行.
		for (int i = 3; i <= lastRowNum; i++) {
			HSSFRow row = sheet.getRow(i);
			
			ImportStock tempStock = buidImportStock(row);
			importStocks.add(tempStock);
		}
		
		Map<String, Product> existsProducts = getExistsProducts(productService);
		Map<String, Stock> existsStocks = getExistsStocks(stockService);
		List<Stock> finalImportStocks = new ArrayList<>();
		
		// 遍历所有对象
		int size = importStocks.size();
		for (int i = 0; i < size; i++) {
			ImportStock importStock = importStocks.get(i);
			
			// 判断当前产品是否存在.
			Product product = existsProducts.get(importStock.getProductCode());
			if (product == null) {
				product = new Product();
				
				product.setProductCode(importStock.getProductCode());
				product.setProductName(importStock.getProductName());
				
				productService.addProduct(product);
				existsProducts.put(product.getProductCode(), product);
			}
			
			// 判断当前导入的库存是否存在.
			Stock stock = existsStocks.get(importStock.getSequence());
			if (stock == null) {
				Stock addStock = new Stock();
				BeanUtils.copyProperties(addStock, importStock);
				
				addStock.setProductId(product.getId());
				
				finalImportStocks.add(addStock);
			}
		}
		
		stockService.batchInsert(finalImportStocks);
	}
	
	/**
	 * 构建导入bean
	 * @param row
	 * @return
	 */
	private ImportStock buidImportStock(Row row) {
		ImportStock result = new ImportStock();
		
		Cell cell = row.getCell(1);
		String productName = getCellValueByCell(cell);
		result.setProductName(productName);
		
		cell = row.getCell(2);
		String productCode = getCellValueByCell(cell);
		result.setProductCode(productCode);
		
		cell = row.getCell(3);
		String sequence = getCellValueByCell(cell);
		result.setSequence(sequence);
		
		cell = row.getCell(4);
		String batchNumber = getCellValueByCell(cell);
		result.setBatchNumber(batchNumber);
		
		cell = row.getCell(5);
		String contractNumber = getCellValueByCell(cell);
		result.setContractNumber(contractNumber);
		
		cell = row.getCell(6);
		String billNumber = getCellValueByCell(cell); 
		result.setBillNumber(billNumber);
		
		cell = row.getCell(7);
		String importDate = getCellValueByCell(cell);
		result.setImportDate(importDate);
		
		cell = row.getCell(8);
		String arrivalDate = getCellValueByCell(cell);
		result.setArrivalDate(arrivalDate);
		
		cell = row.getCell(9);
		String remark = getCellValueByCell(cell);
		result.setRemark(remark);
		
		return result;
	}
	
	/**
	 * 获取已经存在的商品.
	 * @return
	 */
	private Map<String, Product> getExistsProducts(ProductService productService) {
		Map<String, Product> map = new HashMap<>();
		
		List<Product> products = productService.getProducts();
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
	private Map<String, Stock> getExistsStocks(StockService stockService) {
		Map<String, Stock> map = new HashMap<>();
		
		List<Stock> stocks = stockService.getStocks();
		int size = stocks.size();
		
		for (int i = 0; i < size; i++) {
			Stock stock = stocks.get(i);
			map.put(stock.getSequence(), stock);
		}
		
		return map;
	}
	
	/**
	 * 将不同的表格类型转换为字符串
	 * @param cell
	 * @return
	 */
	private String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        int cellType=cell.getCellType();
        if(cellType==Cell.CELL_TYPE_FORMULA){ 
            cellType = evaluator.evaluate(cell).getCellType();
        }
         
        switch (cellType) {
        case Cell.CELL_TYPE_STRING:
            cellValue= cell.getStringCellValue().trim();
            cellValue=StringUtils.isEmpty(cellValue) ? "" : cellValue; 
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            cellValue = String.valueOf(cell.getBooleanCellValue()); 
            break; 
        case Cell.CELL_TYPE_NUMERIC: 
             if (HSSFDateUtil.isCellDateFormatted(cell)) { 
                 cellValue =  DateTimeUtil.date2String(cell.getDateCellValue(), "yyyy-MM-dd");
             } else { 
                 cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue()); 
             } 
            break;
        default: 
            cellValue = "";
            break;
        }
        return cellValue;
    }
	
	/***
	 * 读取入库订单.
	 * @param filePath
	 * @return
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 */
	public List<ImportStock> readIntoOrder(String filePath) throws IOException, EncryptedDocumentException, InvalidFormatException {
		List<ImportStock> finalList = new ArrayList<>();
		
		// 读取Excel
		File excelFile = new File(filePath);
		FileInputStream is = new FileInputStream(excelFile);
		
		Workbook wb = WorkbookFactory.create(is);
		Sheet sheet =  wb.getSheetAt(0);
		
		int lastRowNum = sheet.getLastRowNum();
		// 遍历解析出的所有行.
		for (int i = 3; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			
			// 空行判断
			if (row == null) {
				continue;
			}
			
			ImportStock tempStock = buidImportStock(row);
			
			finalList.add(tempStock);
		}
		
		return finalList;
	}
	
	/**
	 * 读取发货订单
	 * @param filePath
	 * @return 
	 * @throws IOException 
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 */
	public Order readOrder(String filePath, StockService stockService, Integer userId) throws IOException, 
									EncryptedDocumentException, InvalidFormatException {
		Order resolverOrder = new Order();
		
		File excelFile = new File(filePath);
		FileInputStream is = new FileInputStream(excelFile);
		
		Workbook wb = WorkbookFactory.create(is);
		
		Sheet sheet = wb.getSheetAt(0);
		
		List<OrderItem> orderItems = new ArrayList<>();
		Map<String, Integer> stockMap = new HashMap<>();
		int lastRowNum = sheet.getLastRowNum();
		for (int i = 2; i < lastRowNum; i++) {
			Row row = sheet.getRow(i);
			
			// 解析到空行直接跳出.
			if (row == null){
				break;
			}
			if (row.getCell(0) == null && row.getCell(1) == null) {
				break;
			}
			if (StringUtils.isBlank(row.getCell(0).toString()) && StringUtils.isBlank(row.getCell(1).toString())) {
				break;
			}
			
			// 解析订单明细信息.
			String productCode = getCellValueByCell(row.getCell(2));
			String productName = getCellValueByCell(row.getCell(1));
			
			OrderItem temp = new OrderItem();
			temp.setProductName(productName);
			temp.setProductCode(productCode);
			temp.setQuantity(Integer.parseInt(getCellValueByCell(row.getCell(4))));
			
			String stockMapKey = productName.replace(" ", "") + productCode;
			int count = 0;
			if (stockMap.containsKey(stockMapKey)) {
				int stockMapValue = stockMap.get(stockMapKey);
				if (stockMapValue > 0) {
					count = stockMapValue - 1;
					stockMap.put(stockMapKey, count);
				}
			} else {
				// 获取该产品库存数量
				Stock stock = stockService.getStockCountByProductCode(productCode, userId, productName);
				
				if (stock != null && stock.getQuantity() != null) {
					count = stock.getQuantity();
				}
				
				// 记录每次查询的库存数量.
				stockMap.put(productName.replace(" ", "") + productCode, count);
			}
			
			temp.setRestNumber(count);	
			orderItems.add(temp);
		}
		
		// 获取订单信息.
		Row row = sheet.getRow(2);
		String orderNo = getCellValueByCell(row.getCell(5));
		String date = getCellValueByCell(row.getCell(6));
		String custom = getCellValueByCell(row.getCell(7));
		String shipments = getCellValueByCell(row.getCell(8));
		
		// 拆分用户信息.
		String[] customInfos = custom.split("\n");
		String[] shipmentInfos = shipments.split("\n");
		
		resolverOrder.setOrderNo(orderNo);
		resolverOrder.setCreateTime(date);
		
		if (customInfos != null && customInfos.length == 1) {
			resolverOrder.setCustomName(customInfos[0]);
		} else if (customInfos != null && customInfos.length == 2) {
			resolverOrder.setCustomName(customInfos[0]);
			resolverOrder.setCustomPhone(customInfos[1]);
		}
		
		if (shipmentInfos != null && shipmentInfos.length == 1) {
			resolverOrder.setShipmentsAddress(shipmentInfos[0]);
		} else if (shipmentInfos != null && shipmentInfos.length == 2) {
			resolverOrder.setShipmentsCompany(shipmentInfos[0]);
			resolverOrder.setShipmentsAddress(shipmentInfos[1]);
		}
		
		resolverOrder.setOrderItems(orderItems);
		
		return resolverOrder;
	}
	
}
