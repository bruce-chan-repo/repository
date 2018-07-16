package com.ytem.repository.bean;

/**
 * 用户实体对象.
 * @author 陈康敬💪
 * @date 2018年6月20日下午10:53:40
 * @description
 */
public class User {
    private Integer id;

    private String username;
    
    private String realName;

    private String password;

    private String userIcon;

    private String question;

    private String answer;

    private Integer roleId;

    private Boolean isDeleted;

    private String createTime;

    private String modifiedTime;
    
    private Role role;				// 一方实体类
    

	public User() {
		super();
	}

	public User(Integer id, String username, String realName,String password, String userIcon, String question, String answer,
			Integer roleId, Boolean isDeleted, String createTime, String modifiedTime) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.realName = realName;
		this.userIcon = userIcon;
		this.question = question;
		this.answer = answer;
		this.roleId = roleId;
		this.isDeleted = isDeleted;
		this.createTime = createTime;
		this.modifiedTime = modifiedTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", userIcon=" + userIcon
				+ ", question=" + question + ", answer=" + answer + ", roleId=" + roleId + ", isDeleted=" + isDeleted
				+ ", createTime=" + createTime + ", modifiedTime=" + modifiedTime + ", role=" + role + "]";
	}
}