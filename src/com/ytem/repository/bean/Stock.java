package com.ytem.repository.bean;

/**
 * 库存信息表
 * @author 陈康敬💪
 * @date 2018年5月28日上午9:16:09
 * @description
 */
public class Stock {
    private Integer id;

    private Integer productId;
    
    private Integer userId;

    private String sequence;

    private Byte status;

    private String remark;
    
    private Boolean isSold;
    
    private String batchNumber;
    
    private String contractNumber;
    
    private String billNumber;
    
    private Integer quantity;
    
    private String importDate;
    
    private String arrivalDate;

    private String createTime;

    private String modifiedTime;
    
    private Product product;
    
    private User user;
    
    /*  辅助属性  */
    private String productName;		// 产品名称.
    
    private String productCode;		// 产品代码
    
    private Integer rest;			// 剩余库存数量.
    

    public Stock(Integer id, Integer productId, String sequence, Byte status, String remark, Boolean isSold, String createTime, String modifiedTime) {
        this.id = id;
        this.productId = productId;
        this.sequence = sequence;
        this.status = status;
        this.remark = remark;
        this.isSold = isSold;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Stock(Integer id, Integer productId, String sequence, Byte status, String remark, Boolean isSold,
			String batchNumber, String contractNumber, String billNumber, Integer quantity, String createTime, String modifiedTime) {
		super();
		this.id = id;
		this.productId = productId;
		this.sequence = sequence;
		this.status = status;
		this.remark = remark;
		this.isSold = isSold;
		this.batchNumber = batchNumber;
		this.contractNumber = contractNumber;
		this.billNumber = billNumber;
		this.quantity = quantity;
		this.createTime = createTime;
		this.modifiedTime = modifiedTime;
	}
    
    public Stock(Integer id, Integer productId, Integer userId, String sequence, Byte status, String remark, Boolean isSold,
			String batchNumber, String contractNumber, String billNumber, Integer quantity, String importDate, String arrivalDate,
			String createTime, String modifiedTime) {
		super();
		this.id = id;
		this.productId = productId;
		this.userId = userId;
		this.sequence = sequence;
		this.status = status;
		this.remark = remark;
		this.isSold = isSold;
		this.batchNumber = batchNumber;
		this.contractNumber = contractNumber;
		this.billNumber = billNumber;
		this.quantity = quantity;
		this.importDate = importDate;
		this.arrivalDate = arrivalDate;
		this.createTime = createTime;
		this.modifiedTime = modifiedTime;
	}

	public Stock() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence == null ? null : sequence.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

	public Boolean getIsSold() {
		return isSold;
	}

	public void setIsSold(Boolean isSold) {
		this.isSold = isSold;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public Integer getRest() {
		return rest;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setRest(Integer rest) {
		this.rest = rest;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}