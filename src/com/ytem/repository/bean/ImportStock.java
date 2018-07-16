package com.ytem.repository.bean;

/**
 * 库存表导入辅助表
 * @author 陈康敬💪
 * @date 2018年6月6日下午1:20:32
 * @description
 */
public class ImportStock {
	private String productName;				// 产品型号

    private String productCode;				// 产品代码
    
    private String sequence;				// 序列号

    private String remark;					// 备注
    
    private String batchNumber;				// 批次号
    
    private String contractNumber;			// 合同号
    
    private String billNumber;				// 提单号
    
    private String importDate;				// 进口日期
    
    private String arrivalDate;				// 到货日期
    
    private String createTime;				// 入库日期.
    
    

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getImportDate() {
		return importDate;
	}

	public void setImportDate(String importDate) {
		this.importDate = importDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
}
