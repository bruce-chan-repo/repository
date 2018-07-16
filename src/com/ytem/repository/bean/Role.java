package com.ytem.repository.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 角色实体.
 * @author 陈康敬💪
 * @String 2018年5月23日上午10:03:03
 * @description
 */
public class Role {
    private Integer id;

    private String roleName;

    private String description;

    private Boolean available;

    private String createTime;

    private String modifiedTime;
    
    @JsonIgnore
    private List<Menu> menuList;
    

    public Role(Integer id, String roleName, String description, Boolean available, String createTime, String modifiedTime) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
        this.available = available;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Role() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
}