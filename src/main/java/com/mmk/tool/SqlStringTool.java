package com.mmk.tool;

import org.apache.commons.lang3.StringUtils;
/**
 * 
 * @author Sun
 *
 */
public class SqlStringTool {
	
	/**
	 * 对sql进行处理：全模糊查询，
	 * @param queryString
	 * @return %查询字符%
	 */
	public static String anyMatch(String queryString){
		if(StringUtils.isNoneBlank(queryString)){
			return "%".concat(queryString.trim().concat("%"));
		}
		return "%%";
	}
	
	/**
	 * 对sql进行处理：左模糊查询
	 * @param queryString
	 * @return %查询字符
	 */
	public static String leftMatch(String queryString){
		if(StringUtils.isNoneBlank(queryString)){
			return "%".concat(queryString.trim());
		}
		return "%";
	}
	
	/**
	 * 对sql进行处理：右模糊查询
	 * @param queryString
	 * @return  查询字符%
	 */
	public static String rightMatch(String queryString){
		if(StringUtils.isNoneBlank(queryString)){
			return queryString.trim().concat("%");
		}
		return "%";
	}
}
