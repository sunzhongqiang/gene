package com.mmk.gene.dao.impl;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import com.mmk.gene.dao.PageCriteria;
/**
 * 对Hibernate criteria 分页封装 ，对{@link PageCriteria}的具体实现
 * @author 孙中强
 *
 */
@Component
@Scope("prototype")
public class PageCriteriaImpl implements PageCriteria {
	
	private Criteria criteria;
	private Criteria criteriaCount;
	

	@PersistenceContext private EntityManager entityManager;
	
	@Override
	public PageCriteria from(@SuppressWarnings("rawtypes") Class clazz) {
		criteria = entityManager.unwrap(org.hibernate.Session.class).createCriteria(clazz);
		criteriaCount = entityManager.unwrap(org.hibernate.Session.class).createCriteria(clazz);
		return this;
	}
	
	@Override
	public PageCriteria from(@SuppressWarnings("rawtypes") Class clazz,String alias) {
		criteria = entityManager.unwrap(org.hibernate.Session.class).createCriteria(clazz,alias);
		criteriaCount = entityManager.unwrap(org.hibernate.Session.class).createCriteria(clazz,alias);
		return this;
	}
	

	@Override
	public PageCriteria add(Criterion criterion) {
		criteria.add(criterion);
		criteriaCount.add(criterion);
		return this;
	}	

	@Override
	public PageCriteria addOrder(org.hibernate.criterion.Order order) {
		criteria.addOrder(order);
		criteriaCount.addOrder(order);
		return this;
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <T> Page<T> page(Pageable pageable) {
		criteria.setFirstResult(pageable.getOffset());
		criteria.setMaxResults(pageable.getPageSize());
		
		if(pageable.getSort()!=null){
			Iterator<Order> iterator = pageable.getSort().iterator();
			while(iterator.hasNext()){
				Order order = iterator.next();
				if(order.isAscending()){
					criteria.addOrder(org.hibernate.criterion.Order.asc(order.getProperty()));
				}else{
					criteria.addOrder(org.hibernate.criterion.Order.desc(order.getProperty()));
				}
			}
		}
		List content = list();
		
		
		criteriaCount.setProjection(Projections.rowCount());
		Long total = (Long) criteriaCount.uniqueResult();
		
		
		return new PageImpl<T>(content, pageable, total);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public  List list() {
		return criteria.list();
	}

	@Override
	public PageCriteria createAlias(String path, String alias) {
		criteria.createAlias(path, alias);
		criteriaCount.createAlias(path, alias);
		return this;
	}

	@Override
	public PageCriteria createAlias(String path, String alias, JoinType join) {
		criteria.createAlias(path, alias,join);
		criteriaCount.createAlias(path, alias,join);
		return this;
	}

	@Override
	public PageCriteria createAlias(String path, String alias, JoinType join,
			Criterion criterion) {
		criteria.createAlias(path, alias,join,criterion);
		criteriaCount.createAlias(path, alias,join,criterion);
		return this;
	}

}
