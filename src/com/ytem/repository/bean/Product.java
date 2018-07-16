package com.ytem.repository.bean;

/**
 * 商品类
 * @author 陈康敬💪
 * @date 2018年5月22日下午11:39:09
 * @description
 */
public class Product {
    private Integer id;

    private String productName;

    private String productCode;

    private String createTime;

    private String modifiedTime;

    private String detail;
    

    public Product(Integer id, String productName, String productCode, String createTime, String modifiedTime) {
        this.id = id;
        this.productName = productName;
        this.productCode = productCode;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Product(Integer id, String productName, String productCode, String createTime, String modifiedTime, String detail) {
        this.id = id;
        this.productName = productName;
        this.productCode = productCode;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
        this.detail = detail;
    }

    public Product() {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productCode=" + productCode + ", createTime=" + createTime + ", modifiedTime=" + modifiedTime + ", detail=" + detail + "]";
	}
}