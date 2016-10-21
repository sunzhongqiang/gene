package com.mmk.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author Sun
 *
 */
public class SqlStringTool {
	
	/**
	 * 判断是否字母数字组合长度2-20
	 * @param name
	 * @return
	 */
	public static boolean isCommonName(String name) {
		Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]{2,20}$");//以字母开头，
//		Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	public static String anyMatch(String queryString){
		if(StringUtils.isNoneBlank(queryString)){
			return "%".concat(queryString.trim().concat("%"));
		}
		return "%%";
	}
	
	public static String leftMatch(String queryString){
		if(StringUtils.isNoneBlank(queryString)){
			return "%".concat(queryString.trim());
		}
		return "%";
	}
	public static String rightMatch(String queryString){
		if(StringUtils.isNoneBlank(queryString)){
			return queryString.trim().concat("%");
		}
		return "%";
	}
}
