package com.mmk.gene.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.mmk.gene.dao.SpringDataQueryDao;
/**
 * 对spring Data JPA 的分页封装实现，对接口  {@link SpringDataQueryDao}  的具体实现
 * @author 孙中强
 *
 * @param <T>要对那个实体类进行查询
 */
public class SpringDataQueryDaoImpl<T> extends BasePagingQueryDaoImpl<T> implements SpringDataQueryDao<T> {

	
	public SpringDataQueryDaoImpl(Class<T> persistentClass) {
		super(persistentClass);
	}
	
	public SpringDataQueryDaoImpl(Class<T> persistentClass,
			EntityManager entityManager) {
		super(persistentClass, entityManager);
	}

	@Override
	public Page<T> query(Map<String, Object> params, Pageable pageable) {
		StringBuilder ql = new StringBuilder("select model from  ");
		ql.append(this.persistentClass.getName());
		ql.append(" model ");
		if(params!=null){
			ql.append("where 1=1 ");
			for(String key : params.keySet()) {
				ql.append(" and model.");
				ql.append(key);
				ql.append(" = :");
				ql.append(key.replace(".", "_"));
			}
		}
		log.debug("ql:"+ql);
		TypedQuery<T> query = entityManager.createQuery(ql.toString(), persistentClass);
		if(params!=null){
			for(String key : params.keySet()) {
				String keyString = key.replace(".", "_");
				Object value = params.get(key);
				log.debug("jpql赋值[" + keyString + "："+ value + "]");
				if(value instanceof Date){
					Date date = (Date) value;
					query.setParameter(keyString, date ,TemporalType.TIMESTAMP);
				}else if(value instanceof Calendar){
					Calendar date = (Calendar) value;
					query.setParameter(keyString, date,TemporalType.TIMESTAMP);
				}else{
					query.setParameter(keyString, value);
				}
			}
		}
		query.setFirstResult(Long.valueOf(pageable.getOffset()).intValue());
		query.setMaxResults(pageable.getPageSize());
		
		List<T> content = query.getResultList();
		Long total = countByJpql(ql.toString(), params);
		Page<T> page = new PageImpl<T>(content,pageable,total);
		return page;
	}

	@Override
	public Page<T> queryByJpql(String ql, Map<String, Object> params,Pageable pageable) {
		Long total = countByJpql(ql, params);
		List<T> contents = queryByJpql(ql, params,Long.valueOf(pageable.getOffset()),Long.valueOf(pageable.getPageSize()));
		Page<T> page = new PageImpl<T>(contents,pageable,total);
		return page;
	}
	
	@Override
	public Page queryArrayByJpql(String ql, Map<String, Object> params, Pageable pageable) {
		Long total = countByJpql(ql, params);
		int offset = Long.valueOf(pageable.getOffset()).intValue();
		int pageSize = pageable.getPageSize();
		List contents = queryArrayByJpql(ql, params,offset,pageSize);
		Page page = new PageImpl(contents,pageable,total);
		return page;
	}

	@Override
	public Page<T> queryBySql(String ql, Map<Integer, Object> params,Pageable pageable) {
		Long total = countBySql(ql, params);
		List<T> contents = queryBySql(ql, params,Long.valueOf(pageable.getOffset()),Long.valueOf(pageable.getPageSize()));
		Page<T> page = new PageImpl<T>(contents,pageable,total);
		return page;
	}

	@Override
	public Page<Map<String, Object>> queryFieldsByJpql(String ql,Map<String, Object> params, Pageable pageable) {
		Long total = countByJpql(ql, params);
		List<Map<String, Object>> contents = queryFieldsByJpql(ql, params ,Long.valueOf(pageable.getOffset()),Long.valueOf(pageable.getPageSize()));
		Page<Map<String, Object>> page = new PageImpl<Map<String, Object>>(contents,pageable,total);
		return page;
	}

	@Override
	public Page<Map<String, Object>> queryFieldsBySql(String ql,Map<Integer, Object> params, Pageable pageable) {
		Long total = countBySql(ql, params);
		List<Map<String, Object>> contents = queryFieldsBySql(ql, params,Long.valueOf(pageable.getOffset()),Long.valueOf(pageable.getPageSize()));
		Page<Map<String, Object>> page = new PageImpl<Map<String, Object>>(contents,pageable,total);
		return page;
	}

	@Override
	public Page<T> query(String ql, Class<T> resultClass, Map<String, Object> params, Pageable pageable) {
		Long total = countByJpql(ql, params);
		List<T> result = query(ql,resultClass,params);
		Page<T> page = new PageImpl<T>(result, pageable, total);
		return page;
	}

	


}
