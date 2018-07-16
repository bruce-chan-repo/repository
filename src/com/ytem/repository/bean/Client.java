package com.ytem.repository.bean;

public class Client {
    private Integer id;

    private String name;

    private String phone;

    private String remark;

    private String createTime;

    private String modifiedTime;
    

    public Client(Integer id, String name, String phone, String remark, String createTime, String modifiedTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.remark = remark;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Client() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
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
}