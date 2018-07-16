package com.ytem.repository.bean;

import java.util.List;

public class Order {
    private Integer id;

    private String orderNo;				// 订单编号/合同编号
    
    private Integer userId;				// 订单所属客户方

    private String customName;			// 客户的客户姓名

    private String customPhone;			// 客户的客户电话

    private String shipmentsCompany;	// 发货公司

    private String shipmentsAddress;	// 发货地址

    private String remark;				// 备注

    private String createTime;			// 订单时间

    private String modifiedTime;
    
    /*  辅助属性   */
    private User user;					// 所属客户.
    
    private List<OrderItem> orderItems; // 订单明细.
    
    private String orderFile;			// 订单文件
    
    private String orderFileName;		// 订单文件源名称.
    
    private Integer tableNumber;		// 分表编号
    
    

    public Order(Integer id, String orderNo, Integer userId, String customName, String customPhone, String shipmentsCompany, String shipmentsAddress, String remark, String createTime, String modifiedTime) {
        this.id = id;
        this.orderNo = orderNo;
        this.userId = userId;
        this.customName = customName;
        this.customPhone = customPhone;
        this.shipmentsCompany = shipmentsCompany;
        this.shipmentsAddress = shipmentsAddress;
        this.remark = remark;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Order() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName == null ? null : customName.trim();
    }

    public String getCustomPhone() {
        return customPhone;
    }

    public void setCustomPhone(String customPhone) {
        this.customPhone = customPhone == null ? null : customPhone.trim();
    }

    public String getShipmentsCompany() {
        return shipmentsCompany;
    }

    public void setShipmentsCompany(String shipmentsCompany) {
        this.shipmentsCompany = shipmentsCompany == null ? null : shipmentsCompany.trim();
    }

    public String getShipmentsAddress() {
        return shipmentsAddress;
    }

    public void setShipmentsAddress(String shipmentsAddress) {
        this.shipmentsAddress = shipmentsAddress == null ? null : shipmentsAddress.trim();
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

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getOrderFile() {
		return orderFile;
	}

	public void setOrderFile(String orderFile) {
		this.orderFile = orderFile;
	}

	public Integer getTableNumber() {
		return tableNumber;
	}

	public void setTableNumber(Integer tableNumber) {
		this.tableNumber = tableNumber;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrderFileName() {
		return orderFileName;
	}

	public void setOrderFileName(String orderFileName) {
		this.orderFileName = orderFileName;
	}
}