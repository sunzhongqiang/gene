package com.mmk.gene.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * JPA API 查询封装，尚未实现完成
 * @author 孙中强
 * @since 2016-1-3 1.0
 */
public class JpaQueryImpl {
	 
	@PersistenceContext
	private EntityManager em;
	private CriteriaQuery<Object> query;
	private Root root;
	private CriteriaBuilder builder;
	private List<Predicate> predicateList = new ArrayList<Predicate>();
	
	public JpaQueryImpl(){
		builder = em.getCriteriaBuilder();
		query = builder.createQuery();
	}
	
	public JpaQueryImpl from(Class clz){
		root = query.from(clz);
		return this;
	}
	
	public JpaQueryImpl fetch(String field,JoinType type){
		root.fetch(field, type);
		return this;
	}
	
	public JpaQueryImpl like(String filed,String value){
		Predicate p = builder.like(root.get(filed), value);
		predicateList.add(p);
		return this;
	}
	
	public JpaQueryImpl llike(String filed,String value){
		Predicate p = builder.like(root.get(filed), "%"+value);
		predicateList.add(p);
		return this;
	}
	
	public JpaQueryImpl rlike(String filed,String value){
		Predicate p = builder.like(root.get(filed), value+"%");
		predicateList.add(p);
		return this;
	}
	
	public JpaQueryImpl between(String field,Comparable<Object> object,Comparable<Object> object2){
		Predicate p = builder.between(root.get(field), object, object2);
		predicateList.add(p);
		return this;
	}
	
	public JpaQueryImpl between(String field,Date object,Date object2){
		Predicate p = builder.between(root.get(field), object, object2);
		predicateList.add(p);
		return this;
	}
	
	public JpaQueryImpl in(String field,List list){
		Predicate p = builder.in(root.get(field).in(list));
		predicateList.add(p);
		return this;
	}
	/**
	 * 大于
	 * @param field 字段
	 * @param value 要大于的值
	 * @return 返回自己本身
	 */
	public JpaQueryImpl gt(String field,Number value){
		Predicate p =builder.gt(root.get(field),value);
		predicateList.add(p);
		return this;
	}
	
	/**
	 * 大于等于
	 * @param field 比较的字段
	 * @param value 数值
	 * @return 查询本身
	 * 
	 */
	public JpaQueryImpl ge(String field,Number value){
		Predicate p =builder.ge(root.get(field),value);
		predicateList.add(p);
		return this;
	}
	
	/**
	 * 小于
	 * @param field 比较的字段
	 * @param value 数值
	 * @return 查询本身
	 */
	public JpaQueryImpl lt(String field,Number value){
		Predicate p =builder.lt(root.get(field),value);
		predicateList.add(p);
		return this;
	}
	
	/**
	 * 小于登录
	 * @param field 比较的字段
	 * @param value 数值
	 * @return 查询本身
	 */
	public JpaQueryImpl le(String field,Number value){
		Predicate p =builder.le(root.get(field),value);
		predicateList.add(p);
		return this;
	}
	
	
	
	/**
	 * 返回查询的list 结果
	 * @return 查询结果
	 */
	public List list(){
		query.select(root);
		perdicate();
		return em.createQuery(query).getResultList();
	}
	
	/**
	 * 返回查询的分页结果
	 * @param pageable 分页参数
	 * @param <T>  分页返回的泛型对象
	 * @return 分页结果
	 */
	public <T> Page<T> page(Pageable pageable){
		query.select(root);
		perdicate();
		List list = em.createQuery(query).setFirstResult(pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();
		Expression<Long> count = em.getCriteriaBuilder().count(root);
		Long total = (Long) em.createQuery(query.select(count)).getSingleResult();
		return new PageImpl(list,pageable,total);
	}
	/**
	 * 添加where语句的谓词，添加限制条件
	 */
	private void perdicate(){
		query.where(predicateList.toArray(new Predicate[predicateList.size()]));
	} 
   

}
