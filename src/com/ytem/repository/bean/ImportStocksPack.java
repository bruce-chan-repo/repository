package com.ytem.repository.bean;

import java.util.List;

/**
 * 入库单数据包装
 * @author 陈康敬💪
 * @date 2018年6月23日上午10:21:18
 * @description
 */
public class ImportStocksPack {
	private List<ImportStock> importStocks;

	private List<Stock> stocks;
	
	
	public List<ImportStock> getImportStocks() {
		return importStocks;
	}

	public void setImportStocks(List<ImportStock> importStocks) {
		this.importStocks = importStocks;
	}

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
	}