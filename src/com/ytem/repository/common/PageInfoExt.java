package com.ytem.repository.common;

import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * 
 * @author 陈康敬💪
 * @date 2018年5月13日下午6:06:20
 * @description
 */
public class PageInfoExt<T> extends PageInfo<T> {
	private static final long serialVersionUID = 1L;
	
	private int code;

	
	public PageInfoExt() {
		super();
	}

	public PageInfoExt(List<T> arg0, int arg1) {
		super(arg0, arg1);
	}

	public PageInfoExt(List<T> list) {
		super(list);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
