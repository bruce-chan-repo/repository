package com.ytem.repository.common;

import com.ytem.repository.utils.PropertiesUtil;

/**
 * 常量类
 * @author ChenKj
 * @date 2018年5月3日下午12:54:57
 * @description
 */
public class Const {
	// 日志前缀
	private final static String LOGGER_PREFIX="TSB_ISCHOOL_SNS_SERVER"; 
	
	public final static String LOGGER_PREFIX_ERROR="ERROR_PG_"+LOGGER_PREFIX+".|";
	
	public final static String LOGGER_PREFIX_DEBUG="DEBUG_PG_"+LOGGER_PREFIX+".|";
	
	public final static String LOGGER_PREFIX_INFO="INFO_PG_"+LOGGER_PREFIX+".|";
	
	public final static String LOGGER_PREFIX_WARN="WARN_PG_"+LOGGER_PREFIX+".|";
	
	// 加密算法和加密次数
	public final static String HASH_ALGORITHMNAME = "MD5";
	
	public final static int HASH_ITERATIONS = 1024;
	
	// 上传路径.
	public static final String UPLOAD_FILE_PATH = PropertiesUtil.getProperty("upload_file_path");
	
	public static final String SUFFIX_TMPFILE_SAVEPATH = PropertiesUtil.getProperty("suffix_tmpfile_savepath");
	
	public static final String MANAGERS = PropertiesUtil.getProperty("managers", "1");
}
