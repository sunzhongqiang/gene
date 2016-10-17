package com.mmk.gene.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 封装Hibernate 的Criteria 方便快捷的使用  包含自动分页<br>
 * PageCriteria pageCriteria <br>
 * 用法：<br>
 * pageCriteria.from(Model.class).add(Restrictions.ilike("username", username, MatchMode.ANYWHERE)).add().... .page(pageable); <br> 分页返回
 * pageCriteria.from(Model.class).add(Restrictions.ilike("username", username, MatchMode.ANYWHERE)).add().... list()   <br> 列表返回
 *  
 * @author 孙中强 sunzhongqiang
 *
 */
public interface PageCriteria {
	
	/**
	 * 从那个实体进行检索数据
	 * 
	 * 必须先调用此方法方可使用后续的方法
	 * @param clazz 要查询的实体
	 * @return   连接查询
	 */
	
	PageCriteria from(Class clazz);
	
	/**
	 * 从那个实体进行检索数据
	 * 必须先调用此方法方可使用后续的方法,命名别名
	 * @param clazz 要查询的实体
	 * @param alias 给查询实体添加别名
	 * @return  连接查询
	 */
	public PageCriteria from(Class clazz,String alias);
	
	/**
	 * 查询中添加限制条件
	 * @param criterion  限制条件
	 * @return  连接查询
	 */
	PageCriteria add(Criterion criterion);
	
	/**
	 * 添加排序
	 * @param order 排序规则
	 * @return 链接查询
	 */
	PageCriteria addOrder(Order order);

	/**
	 * 按分页进行返回分页数据
	 * @param <T> 要返回的实体
	 * @param pageable 分页参数
	 * 
	 * @return 符合条件的分页
	 */

	<T> Page<T> page(Pageable pageable);
	
	/**
	 * 返回结果集
	 * @param <T> 要返回的实体
	 * @return 返回结果集
	 */
	<T> List <T> list();
	
	/**
	 * 为属性创建别名
	 * @param path 查询路径
	 * @param alias 别名
	 * @return 返回自己
	 */
	PageCriteria createAlias(String path, String alias );
	
	/**
	 * 为属性创建别名
	 * @param path 查询路径
	 * @param alias 别名
	 * @param join 链接类型
	 * @return 返回自己
	 */
	PageCriteria createAlias(String path,String alias, JoinType join);
	/**
	 * 为属性创建别名
	 * @param path 查询路径
	 * @param alias 别名
	 * @param join 连接类型
	 * @param criterion 查询
	 * @return 连接查询
	 */
	PageCriteria createAlias(String path,String alias, JoinType join,Criterion criterion);
	
	
}
