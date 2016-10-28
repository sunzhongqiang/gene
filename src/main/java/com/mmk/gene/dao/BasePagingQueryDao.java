package com.mmk.gene.dao;

import java.util.List;
import java.util.Map;
/**
 * 基础分页查询接口，继承基础查询接口，扩展了根据分页参数获得分页结果
 * @author 孙中强
 *
 * @param <T> 要查询的分页实体，可以不传，尽量写上，不是强执行返回
 */
public interface BasePagingQueryDao<T> extends BaseQueryDao<T> {
	
    
    /**
     * 带基本分页的快捷查询
     * @param params 查询参数
     * @param start 起始位置
     * @param limit 返回结果数
     * @return result  返回符合条件的结果
     */
    List<T> query(Map<String,Object> params,Long start,Long limit);
    
   
    /**
	 * 根据查询，获取结果列表
	 * @param ql 查询语句String
	 * @param params 查询语句绑定的参数
	 * @param start 起始位置
     * @param limit 返回结果数
	 * @return result 返回符合查询语句的结果
	 */
	List<T> queryByJpql(String ql,Map<String, Object> params,Long start,Long limit);
	
	/**
	 * 根据查询JPQL，带参数，获取数组列表
	 * @param ql  jpql的查询
	 * @param params 参数
	 * @param start 分页开始
	 *  @param limit 返回结果数
	 * @return 符合条件的数组列表
	 */
	List queryArrayByJpql(String ql,Map<String, Object> params,int start,int limit);
	
	/**
	 * 根据sql和绑定参数获取查询列表，params可以为null 
	 * @param ql 查询语句为sql
	 * @param params 参数化查询参数，为位置参数
	 * @param start 起始位置
     * @param limit 返回结果数
	 * @return result 返回符合查询语句的结果  
	 */
	List<T> queryBySql(String ql,Map<Integer, Object> params,Long start,Long limit);
	/**
	 * 根据JPQL进行投影查询 返回Map，为对应的字段和数据
	 * @param ql 查询语句为jpql
	 * @param params 绑定参数
	 * @param start 起始位置
     * @param limit 返回结果数
	 * @return result 返回符合查询语句的结果  
	 */
    
    List<Map<String,Object>> queryFieldsByJpql(String ql,Map<String, Object> params,Long start,Long limit);
    
    /**
	 * 根据Sql进行投影查询 返回Map，为对应的字段和数据
	 * @param sql 查询语句为 sql
	 * @param params 绑定参数
	 * @param start 起始位置
     * @param limit 返回结果数
	 * @return result 返回符合查询语句的结果  
	 */
    
    List<Map<String,Object>> queryFieldsBySql(String sql,Map<Integer, Object> params,Long start,Long limit);
    /**
     * 根据查询语句获得指定类型的查询分页结果
     * @param ql
     * @param resultType
     * @param start
     * @param limit
     * @return
     */
    List<T> query(String ql,Class<T> resultType,Map<String,Object> params,int start,int limit);
    

}
