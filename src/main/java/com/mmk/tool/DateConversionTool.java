package com.mmk.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;

public class DateConversionTool {

	/**
	 * 将日期字符串转换成时间戳
	 * @param time 日期字符串
	 * @return 对应的时间戳
	 */
	public static long conver(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long times = 0;
		try {
			times = sdf.parse(time).getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}
	/**
	 * 将日期字符串转换成时间戳
	 * @param time 日期字符串
	 * @return 对应的时间戳
	 */
	public static long conver2start(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long times = 0;
		try {
			times = sdf.parse(time).getTime()/1000-8*60*60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}
	
	/**
	 * 将日期字符串转换成数据库使用的时间戳
	 * @param time 日期字符串
	 * @return 对应的时间戳
	 */
	public static long conver2Long4DB(String time) {
		String[] pattern = {"yyyy-MM-dd","yyyyMMdd","yyyy/MM/dd","yyyy-MM-dd HH:mm:ss","yyyyMMdd HH:mm:ss",
				"yyyy/MM/dd HH:mm:ss","yyyyMM","yyyy"};
		long times = 0;
		try {
			times = DateUtils.parseDate(time, pattern).getTime()/1000-8*60*60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}
	
	/**
	 * 将时间转换为秒数
	 * @param date 要转换的时间
	 * @return 返回long类型的时间戳
	 */
	public static long conver2end(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long times = 0;
		try {
			Date date = sdf.parse(time);
			
			date = DateUtils.setHours(date, 23);
			date = DateUtils.setMinutes(date, 59);
			date = DateUtils.setSeconds(date, 59);
			times = date.getTime()/1000 - 8*60*60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}
	
	
	/**
	 * 根据时间获取当天的凌晨的秒数
	 * @param date 要转换的时间
	 * @return 返回long类型的时间戳
	 */
	public static long date2long(Date date){
		
		return date.getTime()/1000;
	}
	/**
	 * 根据时间获取当天的凌晨的秒数
	 * @param date 要转换的时间
	 * @return 返回long类型的时间戳
	 */
	public static long date2long2(Date date){
		date = DateUtils.setHours(date, 0);
		date = DateUtils.setMinutes(date, 0);
		date = DateUtils.setSeconds(date, 0);
		return date.getTime()/1000;
	}
	/**
	 * 将日期字符串根据给定的格式转换成日期类型
	 * @param date 日期字符串
	 * @param format 日期格式
	 * @return 对应的日期
	 */
	public static Date string2date(String date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date dt = sdf.parse(date);
			return dt;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将日期字符串根据yyyy-MM-dd格式转换成日期类型
	 * @param date 日期字符串
	 *
	 * @return 对应的日期
	 */
	public static Date string2date(String date){
		try {
			String[] pattern = {"yyyy-MM-dd","yyyyMMdd","yyyy/MM/dd","yyyy-MM-dd HH:mm:ss","yyyyMMdd HH:mm:ss",
					"yyyy/MM/dd HH:mm:ss","yyyyMM","yyyy"};
			Date result = DateUtils.parseDate(date, pattern);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 将日期字符串根据yyyy-MM-dd格式转换成日期类型
	 * @param date 日期字符串
	 *
	 * @return 对应的日期
	 */
	public static Date string2Startdate(String date){
		try {
			String[] pattern = {"yyyy-MM-dd","yyyyMMdd","yyyy/MM/dd","yyyy-MM-dd HH:mm:ss","yyyyMMdd HH:mm:ss",
					"yyyy/MM/dd HH:mm:ss","yyyyMM","yyyy"};
			Date result = DateUtils.parseDate(date+" 00:00:00", pattern);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}	
	
	/**
	 * 将日期字符串根据yyyy-MM-dd格式转换成日期类型
	 * @param date 日期字符串
	 *
	 * @return 对应的日期
	 */
	public static Date string2Enddate(String date){
		try {
			String[] pattern = {"yyyy-MM-dd","yyyyMMdd","yyyy/MM/dd","yyyy-MM-dd HH:mm:ss","yyyyMMdd HH:mm:ss",
					"yyyy/MM/dd HH:mm:ss","yyyyMM","yyyy"};
			Date result = DateUtils.parseDate(date+" 23:59:59", pattern);
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static Integer getDays(int year,int month){
		DateTime dateTime = new DateTime(year, month, 1, 0, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(dateTime.toDate());
		c.getMaximum(Calendar.DAY_OF_MONTH);
		return 0;
	}
	
	public static String date2string(Date date , String pattern){
		return new DateTime(date).toString(pattern);
	}
	
	/**
	 * 将日期返回yyyy-MM-dd HH:mm:ss格式
	 * @param date
	 * @return
	 */
	public static String formatDateToString(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	public static String long2string(Long time, String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		DateTime dateTime = new DateTime(time);
		return sdf.format(dateTime.toDate());
	}
	
	/**
	 * 将日期字符串转换成时间戳
	 * @param time 日期字符串
	 * @return 对应的时间戳
	 */
	public static long conver16(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long times = 0;
		try {
			times = sdf.parse(time).getTime()/1000;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}
	
	/**
	 * 将日期字符串根据传过来的格式转换成long类型
	 * @param date  要转换的字符时间
	 * @return 返回long类型的时间戳
	 */
	public static Long string2long(String date){
		try {
			String[] pattern = {"yyyy-MM-dd","yyyyMMdd","yyyy/MM/dd","yyyy-MM-dd HH:mm:ss","yyyyMMdd HH:mm:ss",
					"yyyy/MM/dd HH:mm:ss","yyyyMM","yyyy"};
			Long result = DateUtils.parseDate(date, pattern).getTime()/1000;
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
