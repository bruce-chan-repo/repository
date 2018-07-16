package com.ytem.repository.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 属性文件读取.
 * @author 陈康敬💪
 * @date 2018年4月30日下午1:44:24
 * @description
 */
public class PropertiesUtil {
	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);
	
	private static Properties props;
	
	static {
		String conf = "applicationResource.properties";
		props = new Properties();
		try {
			props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(conf), "UTF-8"));
		} catch (IOException e) {
			logger.error("属性文件读取失败", e);
		}
	}
	
	public static String getProperty(String key) {
		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			value = StringUtils.EMPTY;
		}
		
		return value;
	}
	
	public static String getProperty(String key, String defaultValue) {
		String value = props.getProperty(key);
		if (StringUtils.isBlank(value)) {
			value = defaultValue;
		}
		
		return value;
	}
}
