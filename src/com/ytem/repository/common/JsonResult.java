package com.ytem.repository.common;

import java.io.Serializable;

/**
 * JsonReultBean.
 * @author 陈康敬💪
 * @date 2018年5月19日下午3:41:03
 * @description
 */
public class JsonResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private int code;
	
	private int rows;
	
	private String msg;
	
	private T data;
	

	public JsonResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public JsonResult(int code, String msg, int rows) {
		this.code = code;
		this.msg = msg;
		this.rows = rows;
	}
	
	public JsonResult(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", msg=" + msg + ", rows=" + rows + "]";
	}
}
