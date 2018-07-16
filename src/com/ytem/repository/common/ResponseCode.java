package com.ytem.repository.common;

/**
 * 响应码类.
 * @author 陈康敬💪
 * @date 2018年5月19日下午3:44:44
 * @description
 */
public enum ResponseCode {
	/**
	 * 响应成功
	 */
	SUCCESS(1, "SUCCESS"),	
	/**
	 * 响应失败
	 */
	ERROR(2, "ERROR");	
	
	
	private final int code;
	
	private final String desc;
	
	ResponseCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}	
