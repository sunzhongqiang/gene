package com.mmk.gene.dao;

import java.util.List;
import java.util.Map;
/**
 * 基础查询类，提供便捷的sql或者jpql快速查询，封装比较重复的 查询<br>
 * 针对那个实体进行查询，如果传入，一些方法会返回该类，尽量写 
 * @author 孙中强 sunzhongqiang
 *
 * 
 */

public interface BaseQueryDao<T> {
	/**
     * 根据JPQL仅仅获取一个实体
     * @param jpql  单个查询用的jpql
     * @return  符合条件的唯一实体，或者符合条件的第一个实体
     */
    T getOne(String jpql);
    
    /**
     * 根据JPQL和给定的参数仅仅获取一个实体，如果存在多个符合条件的会只返回第一个
     * @param jpql 查的jpql
     * @param params jpql绑定参数
     * @return  符合条件的唯一实体，或者符合条件的第一个实体
     */
    T getOne(String jpql,Map<String,Object> params);
    
    /**
     * 根据参数进行查询符合条件的列表<br>
     * 参数会以and形式组装在where以后，使用字段名作为参数条件
     * @param params 字段以及期望字段中拥有的值
     * @return results 为list 符合条件的列表
     */
    List<T> query(Map<String,Object> params);
    
    /**
	 * 根据查询JPQL，带参数，获取结果列表
	 * @param ql  查询的JPQL
	 * @param params jpql 的参数
	 * @return 符合条件的列表
	 */
	List<T> queryByJpql(String ql,Map<String, Object> params);
	
	/**
	 * 根据查询JPQL，带参数，获取数组列表
	 * @param ql  jpql的查询
	 * @param params 参数
	 * @return 符合条件的数组列表
	 */
	List<List<Object>> queryArrayByJpql(String ql,Map<String, Object> params);
	
	/**
	 * 根据sql和参数获取查询列表
	 * @param ql 要查询的sql语句
	 * @param params 绑定参数，为Integer : Object
	 * @return 符合条件的列表
	 */
	List<T> queryBySql(String ql,Map<Integer, Object> params);
	/**
	 * 投影查询 以Map形式组装的对象列表，进行返回
	 * @param ql 要查询的jpql语句
	 * @param params 查询参数
	 * @return 符合条件的列表
	 */
    
    List<Map<String,Object>> queryFieldsByJpql(String ql,Map<String, Object> params);
    
    /**
	 * 投影查询 参数为位置参数
	 * @param sql 要查询的sql语句
	 * @param params 绑定的参数
	 * @return 符合条件的列表
	 */
    
    List<Map<String,Object>> queryFieldsBySql(String sql,Map<Integer, Object> params);
    
    /**
	 * 根据jpqlql查询语句，获得该查询语句的结果集数量
	 * @param ql 为JPQL
	 * @param params 查询参数
	 * @return 总记录数
	 */
	Long countByJpql(String ql,Map<String,Object> params);
	
	/**
	 * 根据sql查询语句，获得该查询语句的结果集数量，该方法为查询结果集的语句，会自动拼接select count 语句
	 * @param sql 为本地sql语句
	 * @param params 查询参数
	 * @return 查询语句能查到总记录数 
	 */
	Long countBySql(String sql,Map<Integer,Object> params);
	
	
	/**
	 * 查询数量的jpql，获取唯一的个数，计算总记录数的jpql count语句，保证     该语句是count语句，并返回一个统计数值
	 * @param countJpql 统计JPQL
	 * @param params 查询参数
	 * @return 查询语句能查到总记录数 
	 */
	Long count(String countJpql,Map<String,Object> params);
	
	/**
	 * 查询数量的sql，获取唯一的个数 ，保证     该语句是count语句，并返回一个统计数值
	 * @param countSql 统计的sql语句
	 * @param params 查询参数
	 * @return 查询语句能查到总记录数 
	 */
	Long nativeCount(String countSql,Map<Integer,Object> params);

	/**
	 * 根据查询结果集的sql，拼接出查询结果数的sql
	 * @param sql 查询结果集sql的语句
	 * @return  统计结果数的sql语句
	 */
	String countQueryStringFromSql(String sql);

	/**
	 * 根据查询结果的jpql，拼接出查询结果数的jpql
	 * @param jpql 查询结果集的jpql语句
	 * @return 查询结果数的jpql的语句
	 */
	String countQueryStringFromJpql(String jpql);

	/**
	 * 根据查询语句获得指定类型的查询结果
	 * @param qlString
	 * @param resultClass
	 * @return
	 */
	List<T> query(String qlString, Class<T> resultClass,Map<String,Object> params);
	
	/**
	 * 根据字段和字段值，获得符合条件的唯一值，或者是第一个值
	 * @param filed 要查询的字段
	 * @param value 要查询字段对应的值
	 * @return 返回唯一值，或者符合条件的第一个值，否则返回null
	 */
	T findBy(String filed,Object value);
	/**
	 * 根据字段和字段值，获得符合条件的所有
	 * @param filed 要查询的字段
	 * @param value 要查询字段对应的值
	 * @return 返回符合条件的所有记录，或者是一个空的集合
	 */
	List<T> findAllBy(String field,Object value);
	
	/**
	 * 根据字段和字段值，获得符合条件的唯一值，或者是第一个值
	 * @param params key-value 组
	 * @return 返回唯一值，或者符合条件的第一个值，否则返回null
	 */
	T findBy(Map<String,Object> params);
	/**
	 * 根据字段和字段值，获得符合条件的所有
	 * @param params key-value 组
	 * @return 返回符合条件的所有记录，或者是一个空的集合
	 */
	List<T> findAllBy(Map<String,Object> params);
	
	/**
	 * 批量执行删除或者修改
	 * @param updateSql
	 * @return 影响的条数
	 */
	int executeUpdate(String updateSql);
	
	int executeUpdate(String updateJpql,Map<String,Object> params);
	
	int executeNativeUpdate(String updateJpql,Map<Integer,Object> params);

}
