package com.mmk.gene.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.internal.QueryImpl;
import org.hibernate.transform.Transformers;

import com.mmk.gene.dao.BaseQueryDao;

/**
 * 接口{@link BaseQueryDao}的具体实现类
 * @author 孙中强
 *
 * @param <T> 要对哪一个实体类进行查询
 */
public class BaseQueryDaoImpl<T> implements BaseQueryDao<T> {
	
	protected final Log log = LogFactory.getLog(getClass());
	@PersistenceContext
	protected EntityManager entityManager;
	protected Class<T> persistentClass;
	
	
	/**
	 * 实例化一个实体类的查询DAO
	 * @param persistentClass 要查询的实体类
	 */
	public BaseQueryDaoImpl(final Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	

	 /**
     * 使用指定的实体和实体管理器构建一个实体查询类
     * @param persistentClass 要查询的实体类
     * @param entityManager 实体管理器
     */
	public BaseQueryDaoImpl(final Class<T> persistentClass, EntityManager entityManager) {
		super();
        this.persistentClass = persistentClass;
        this.entityManager = entityManager;
	}
	
	
	
	@Override
	public T getOne(String jpql) {
		TypedQuery<T> query =  entityManager.createQuery(jpql,this.persistentClass);
		query.setMaxResults(1);
		List<T> result = query.getResultList();
		return result.isEmpty() ? null :result.get(0);
	}

	@Override
	public T getOne(String jpql, Map<String, Object> params) {
		TypedQuery<T> query =  entityManager.createQuery(jpql,this.persistentClass);
		query.setMaxResults(1);
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
		List<T> result = query.getResultList();
		return result.isEmpty() ? null :result.get(0);
	}

	@Override
	public List<T> query(Map<String, Object> params) {
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
		return query.getResultList();
	}

	@Override
	public List<T> queryByJpql(String ql, Map<String, Object> params) {
		TypedQuery<T> query =   entityManager.createQuery(ql, this.persistentClass);
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
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryBySql(String ql, Map<Integer, Object> params) {
		Query query =   entityManager.createNativeQuery(ql, persistentClass);
		if(params!=null){
			for(Integer key : params.keySet()) {
				Object value = params.get(key);
				log.debug("sql赋值["+key+"："+params.get(key)+"]");
				if(value instanceof Date){
					Date date = (Date) value;
					query.setParameter(key, date ,TemporalType.TIMESTAMP);
				}else if(value instanceof Calendar){
					Calendar date = (Calendar) value;
					query.setParameter(key, date,TemporalType.TIMESTAMP);
				}else{
					query.setParameter(key, value);
				}
			}
		}
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryFieldsByJpql(String ql,Map<String, Object> params) {
		Query query = entityManager.createQuery(ql);
		query.unwrap(QueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		for (String key : params.keySet()) {
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
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryFieldsBySql(String sql,Map<Integer, Object> params) {
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
		
		for (Integer key : params.keySet()) {
			Object value = params.get(key);
			log.debug("sql赋值["+key+"："+params.get(key)+"]");
			if(value instanceof Date){
				Date date = (Date) value;
				query.setParameter(key, date ,TemporalType.TIMESTAMP);
			}else if(value instanceof Calendar){
				Calendar date = (Calendar) value;
				query.setParameter(key, date,TemporalType.TIMESTAMP);
			}else{
				query.setParameter(key, value);
			}
		}
		return query.getResultList();
	}

	@Override
	public Long countByJpql(String ql, Map<String, Object> params) {
		String countQl = countQueryStringFromJpql(ql);
		return count(countQl, params);
	}

	@Override
	public Long countBySql(String sql, Map<Integer, Object> params) {
		String countSql = countQueryStringFromSql(sql);
		return nativeCount(countSql, params);
	}

	@Override
	public Long count(String countJpql, Map<String, Object> params) {
		log.debug("countSql:"+countJpql);
		try {
			TypedQuery<Long> query = entityManager.createQuery(countJpql,Long.class);
			//为sql赋值
			if(params!=null){
				for (String key : params.keySet()) {
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
			Long total = query.getSingleResult();
			log.debug("获取总数："+total);
			return total;
		} catch (Exception e) {
			log.debug("通过jpql获取总数的时候出错了", e);
			e.printStackTrace();
		}
		
		return null;
	}
	

	@Override
	public Long nativeCount(String countSql, Map<Integer, Object> params) {
		log.debug("countSql:"+countSql);
		try {
			Query query = entityManager.createNativeQuery(countSql.toString());
			//为sql赋值
			if(params!=null){
				for (Integer key : params.keySet()) {
					Object value = params.get(key);
					log.debug("sql赋值["+key+"："+params.get(key)+"]");
					if(value instanceof Date){
						Date date = (Date) value;
						query.setParameter(key, date ,TemporalType.TIMESTAMP);
					}else if(value instanceof Calendar){
						Calendar date = (Calendar) value;
						query.setParameter(key, date,TemporalType.TIMESTAMP);
					}else{
						query.setParameter(key, value);
					}
				}
			}
			String total = query.getSingleResult().toString();
			log.debug("获取总数："+total);
			return Long.valueOf(total);
		} catch (Exception e) {
			log.debug("通过sql获取总数的时候出错了", e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@Override
	public String countQueryStringFromSql(String sql) {
		StringBuilder countSql = new StringBuilder("select count(*) from (");
		countSql.append(sql);
		countSql.append(" ) as  result_list ");
		return countSql.toString();
	}

	@Override
	public String countQueryStringFromJpql(String jpql) {
		log.debug("jpql:"+jpql);
		if(StringUtils.isNotBlank(jpql)){
			log.debug("预处理jqpl");
			jpql = StringUtils.trimToEmpty(jpql);
			jpql = jpql.replaceAll("(?i)fetch", "");
			log.debug("去掉fetch:"+jpql);
			jpql = jpql.replaceFirst("(?i) order(\\s+)by(\\s+).*", "");  //去掉orderby
			log.debug("去掉order by的jqpl:"+jpql);
			Pattern pattern = Pattern.compile("select(\\s+)(.+)(\\s+)from(\\s+)([\\w\\.]+)(\\s+)(\\w+)(.*)",Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(jpql);
			if(matcher.matches()){
				String modelClass = matcher.group(5);
				log.debug("group 5:"+modelClass);
				log.debug("group 6:"+matcher.group(6));
				String mainModel = matcher.group(7);
				log.debug("group 7:"+matcher.group(7));
				jpql = "select count(distinct "+mainModel+") from "+modelClass +" "+mainModel+matcher.group(8);
				log.debug("组装最后的jpql:"+jpql);
			}else{
				throw new RuntimeException("无法将jpql匹配成结果集查询数的jpql："+jpql);
			}
			return jpql;
		}
		return "";
	}



	@Override
	public List<T> query(String qlString, Class<T> resultClass,Map<String,Object> params) {
		TypedQuery<T> query = entityManager.createQuery(qlString, resultClass);
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
		return query.getResultList();
	}



	@Override
	public T findBy(String field, Object value) {
		StringBuilder sb = new StringBuilder();
		sb.append("select model from ");
		sb.append( this.persistentClass.getName() );
		sb.append(" model where model.");
		sb.append(field).append(" = :").append(field);
		TypedQuery<T> query = entityManager.createQuery(sb.toString(),persistentClass);
		query.setParameter(field, value);
		query.setMaxResults(1);
		List<T> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}



	@Override
	public List<T> findAllBy(String field, Object value) {
		StringBuilder sb = new StringBuilder();
		sb.append("select model from ");
		sb.append( this.persistentClass.getName() );
		sb.append(" model where model.");
		sb.append(field).append(" = :").append(field);
		TypedQuery<T> query = entityManager.createQuery(sb.toString(),persistentClass);
		query.setParameter(field, value);
		return query.getResultList();
	}



	@Override
	public T findBy(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("select model from ");
		sb.append( this.persistentClass.getName() );
		sb.append(" model where 1=1 ");
		for (String key : params.keySet()) {
			sb.append("model.").append(key).append(" = :").append(key);
		}
		
		TypedQuery<T> query = entityManager.createQuery(sb.toString(),persistentClass);
		
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
		
		query.setMaxResults(1);
		List<T> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}



	@Override
	public List<T> findAllBy(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("select model from ");
		sb.append( this.persistentClass.getName() );
		sb.append(" model where 1=1 ");
		for (String key : params.keySet()) {
			sb.append("model.").append(key).append(" = :").append(key);
		}
		
		TypedQuery<T> query = entityManager.createQuery(sb.toString(),persistentClass);
		
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
		return query.getResultList();
	}



	@Override
	public int executeUpdate(String updateSql) {
		return entityManager.createNativeQuery(updateSql).executeUpdate();
	}



	@Override
	public int executeUpdate(String updateJpql, Map<String, Object> params) {
		Query query = entityManager.createQuery(updateJpql);
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
		return query.executeUpdate();

	}



	@Override
	public int executeNativeUpdate(String updateJpql, Map<Integer, Object> params) {
		Query query = entityManager.createNativeQuery(updateJpql);
		for (Integer key : params.keySet()) {
			Object value = params.get(key);
			log.debug("jpql赋值[" + key + "："+ value + "]");
			if(value instanceof Date){
				Date date = (Date) value;
				query.setParameter(key, date ,TemporalType.TIMESTAMP);
			}else if(value instanceof Calendar){
				Calendar date = (Calendar) value;
				query.setParameter(key, date,TemporalType.TIMESTAMP);
			}else{
				query.setParameter(key, value);
			}
		}
		return query.executeUpdate();
	}



	@Override
	public List queryArrayByJpql(String ql, Map<String, Object> params) {
		Query query =   entityManager.createQuery(ql);
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
		return query.getResultList();
	}


}
