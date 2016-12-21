package com.mmk.gene.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Spring Data 的使用sql或者jpql的基础分页查询接口
 * @author 孙中强 sunzhongqiang
 *
 * 
 */
public interface SpringDataQueryDao<T> extends BasePagingQueryDao<T> {
    
    /**
     * 根据参数查询数据库，以分页的形势返回
     * @param params 查询阐述
     * @param pageable 分页参数 
     * @return 分页结果
     */
	Page<T> query(Map<String, Object> params,Pageable pageable);
    
	/**
	 * 根据查询语言和绑定的参数分页返回数据
	 * @param ql 查询JPQL语句，
	 * @param params 查询参数
	 * @param pageable 分页参数
	 * @return 分页结果 
	 */
	Page<T> queryByJpql(String ql,Map<String, Object> params,Pageable  pageable);
	/**
	 * 根据查询语言和绑定的参数分页返回数据
	 * @param ql 查询JPQL语句，
	 * @param params 查询参数
	 * @param pageable 分页参数
	 * @return 数组分页结果
	 */
	Page queryArrayByJpql(String ql,Map<String, Object> params,Pageable  pageable);
	
	/**
	 * 使用本地查询语言SQL进行查询
	 * @param ql  查询sql语句，
	 * @param params   参数只能使用位置绑定
	 * @param pageable Spring 分页参数
	 * @return 分页结果
	 */
	Page<T> queryBySql(String ql,Map<Integer, Object> params, Pageable pageable);
	
	
	
	/**
	 * 投影查询，分页返回根据 JPQL 进行查询
	 * @param ql 查询语句，投影查询，select from 之间要填写的投影字段
	 * @param params  key：value 形式的参数
	 * @param pageable Spring 分页参数
	 * @return 分页结果
	 */
	Page<Map<String ,Object>> queryFieldsByJpql(String ql,Map<String, Object> params, Pageable pageable);
	
	/**
	 *  投影查询，分页返回 只能使用位置参数，语句为sql语句
	 * @param ql sql查询语句，投影查询，select from 之间要填写的投影字段 
	 * @param params 位置参数 
	 * @param pageable Spring 分页参数
	 * @return 分页结果
	 */
	Page<Map<String ,Object>> queryFieldsBySql(String ql,Map<Integer, Object> params, Pageable pageable);
	
	/**
	 * 分页返回
	 * @param ql
	 * @param resultClass
	 * @param params
	 * @param pageable
	 * @return
	 */
	Page<T> query(String ql ,Class<T> resultClass,Map<String,Object> params,Pageable pageable);

}
