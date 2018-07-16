package com.ytem.repository.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 菜单实体类.
 * @author 陈康敬💪
 * @date 2018年5月23日下午10:11:15
 * @description
 */
public class Menu {
    private Integer id;

    private String menuName;

    private Integer parentId;

    private Integer sortId;

    private String url;

    private String menuIcon;

    private String createTime;

    private String modifiedTime;
    
    private Menu parent;			// 父节点
    
    private List<Menu> children;	// 子节点

    
    public Menu(Integer id, String menuName, Integer parentId, Integer sortId, String url, String menuIcon, String createTime, String modifiedTime) {
        this.id = id;
        this.menuName = menuName;
        this.parentId = parentId;
        this.sortId = sortId;
        this.url = url;
        this.menuIcon = menuIcon;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }

    public Menu() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
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

    @JsonIgnore
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
}