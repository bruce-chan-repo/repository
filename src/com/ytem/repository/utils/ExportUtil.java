package com.ytem.repository.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

public class ExportUtil {

	/**
	 * 构建Excel的列头（通用）
	 * @param wb 工作簿
	 * @param colHead 需要构建的行
	 * @param columns 列头数组
	 */
	public static void buildColumnHeader(HSSFWorkbook wb, HSSFRow colHead, String[] columns) {
		// buildColHeader row
		colHead.setHeight((short)500);
		HSSFCellStyle colHeadStyle = wb.createCellStyle();
		
		// colHeader font Style
		Font colFont = wb.createFont();
		colFont.setFontHeightInPoints((short)12);
		colFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		colFont.setFontName("微软雅黑");
		colHeadStyle.setFont(colFont);
		colHeadStyle.setAlignment(CellStyle.ALIGN_CENTER);
		colHeadStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		//ExportUtil.setBorder(colHeadStyle);
		
		// generate column
		for (int i = 0; i < columns.length; i++) {
			ExportUtil.createCell(wb, colHead, i, columns[i].split(",")[0], colHeadStyle);
		}
	}
	
	/**
	 * 获取valueStyle
	 * @param wb
	 * @return
	 */
	public static CellStyle getValueStyle(HSSFWorkbook wb) {
		// valueStyle
		CellStyle valueStyle = wb.createCellStyle();
		Font valueFont = wb.createFont();
		valueFont.setFontHeightInPoints((short)11);
		valueFont.setFontName("微软雅黑");
		valueStyle.setFont(valueFont);
		valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	//居中单元格
		valueStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		return valueStyle;
	}
	
	/**
	 * 设置单元格的边框
	 * @param cellStyle
	 */
	public static void setBorder(HSSFCellStyle cellStyle){
		cellStyle.setBorderBottom(cellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(cellStyle.BORDER_THIN);
		cellStyle.setBorderRight(cellStyle.BORDER_THIN);
		cellStyle.setBorderTop(cellStyle.BORDER_THIN);
	}
	
	/**
	 * 创建一个单元格，如果指定样式按指定的样式创建
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 * @param cellstyle
	 */
	public static void createCell(HSSFWorkbook wb,HSSFRow row,int col,String val,HSSFCellStyle cellstyle){
        HSSFCell cell = row.createCell(col);
        cell.setCellValue(val);
        //如果参数包含样式，设置样式
        if(null != cellstyle){
        	cell.setCellStyle(cellstyle);
        }
    }
}
