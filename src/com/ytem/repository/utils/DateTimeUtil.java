package com.ytem.repository.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * 时间工具类
 * @author 陈康敬💪
 * @date 2018年6月3日下午5:45:44
 * @description
 */
public class DateTimeUtil {
	private static DateTime dateTime = DateTime.now();
	
	/**
	 * 标准时间格式
	 */
	private static final String STANDARD_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	// 私有化构造
	private DateTimeUtil() {

	}
	
	/**
	 * 获取当前的年
	 * @return
	 */
	public static int getYear() {
		return dateTime.getYear();
	}
	
	/**
	 * 获取月份
	 * @return
	 */
	public static int getMonth() {
		return dateTime.getMonthOfYear();
	}
	
	/**
	 * 获取天
	 * @return
	 */
	public static int getDay() {
		return dateTime.getDayOfMonth();
	}
	
	/**
	 * 时间转换字符串
	 * @param date
	 * @param pattern
	 */
	public static String date2String(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 时间转换字符串
	 * @param date
	 * @param pattern
	 */
	public static String date2String(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_PATTERN);
		return sdf.format(date);
	}
	
	/**
	 * 字符串转时间
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date string2Date(String date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 字符串转时间
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date string2Date(String date) {
		return string2Date(date, STANDARD_PATTERN);
	}
}
