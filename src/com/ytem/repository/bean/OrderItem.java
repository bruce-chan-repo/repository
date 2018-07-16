package com.ytem.repository.bean;

public class OrderItem {
    private Integer id;
    
    private Integer orderNo;		

    private String productName;

    private String productCode;

    private String productSequence;

    private Integer quantity;

    private String createTime;

    private String modifiedTime;
    
    /*  辅助属性   */
    private Integer tableNumber;
    
    private Integer restNumber;			
    

    public OrderItem(Integer id, Integer orderNo, String productName, String productCode, String productSequence, Integer quantity, String createTime, String modifiedTime) {
        this.id = id;
        this.orderNo = orderNo;
        this.productName = productName;
        this.productCode = productCode;
        this.productSequence = productSequence;
        this.quantity = quantity;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public OrderItem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getProductSequence() {
        return productSequence;
    }

    public void setProductSequence(String productSequence) {
        this.productSequence = productSequence == null ? null : productSequence.trim();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Integer getRestNumber() {
		return restNumber;
	}

	public void setRestNumber(Integer restNumber) {
		this.restNumber = restNumber;
	}
}