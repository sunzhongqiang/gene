package com.mmk.gene.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.query.internal.QueryImpl;
import org.hibernate.transform.Transformers;

import com.mmk.gene.dao.BasePagingQueryDao;

/**
 * 接口 {@link BasePagingQueryDao} 的具体实现类
 * 
 * @author 孙中强
 *
 * @param <T>
 *            泛型：要对哪一个实体类进行分页查询
 */
public class BasePagingQueryDaoImpl<T> extends BaseQueryDaoImpl<T> implements BasePagingQueryDao<T> {

	public final static Long DEFAULT_START = 0l;
	public final static Long DEFAULT_LIMIT = 10l;

	/**
	 * 构造基本 的分页查询类
	 * 
	 * @param persistentClass
	 *            要查询的实体
	 */
	public BasePagingQueryDaoImpl(Class<T> persistentClass) {
		super(persistentClass);
	}

	/**
	 * 构造基本的分页查询类
	 * 
	 * @param persistentClass
	 *            查询实体
	 * @param entityManager
	 *            实体管理类
	 */
	public BasePagingQueryDaoImpl(Class<T> persistentClass, EntityManager entityManager) {
		super(persistentClass, entityManager);
	}

	@Override
	public List<T> query(Map<String, Object> params, Long start, Long limit) {
		StringBuilder ql = new StringBuilder("select model from  ");
		ql.append(this.persistentClass.getName());
		ql.append(" model ");
		if (params != null) {
			ql.append("where 1=1 ");
			for (String key : params.keySet()) {
				ql.append(" and model.");
				ql.append(key);
				ql.append(" = :");
				ql.append(key.replace(".", "_"));
			}
		}
		log.debug("ql:" + ql);
		TypedQuery<T> query = entityManager.createQuery(ql.toString(), persistentClass);
		if (params != null) {
			for (String key : params.keySet()) {
				String keyString = key.replace(".", "_");
				Object value = params.get(key);
				log.debug("jpql赋值[" + keyString + "：" + value + "]");
				if (value instanceof Date) {
					Date date = (Date) value;
					query.setParameter(keyString, date, TemporalType.TIMESTAMP);
				} else if (value instanceof Calendar) {
					Calendar date = (Calendar) value;
					query.setParameter(keyString, date, TemporalType.TIMESTAMP);
				} else {
					query.setParameter(keyString, value);
				}
			}
		}
		// 设置分页
		if (start == null) {
			start = DEFAULT_START;
		}
		if (limit == null) {
			limit = DEFAULT_LIMIT;
		}
		query.setFirstResult(start.intValue());
		query.setMaxResults(limit.intValue());
		return query.getResultList();
	}

	@Override
	public List<T> queryByJpql(String ql, Map<String, Object> params, Long start, Long limit) {
		log.debug("ql:" + ql);
		try {
			TypedQuery<T> query = entityManager.createQuery(ql, persistentClass);
			if (params != null) {
				// 为sql赋值
				for (String key : params.keySet()) {
					String keyString = key.replace(".", "_");
					Object value = params.get(key);
					log.debug("jpql赋值[" + keyString + "：" + value + "]");
					if (value instanceof Date) {
						Date date = (Date) value;
						query.setParameter(keyString, date, TemporalType.TIMESTAMP);
					} else if (value instanceof Calendar) {
						Calendar date = (Calendar) value;
						query.setParameter(keyString, date, TemporalType.TIMESTAMP);
					} else {
						query.setParameter(keyString, value);
					}
				}
			}
			// 设置分页
			if (start == null) {
				start = DEFAULT_START;
			}
			if (limit == null) {
				limit = DEFAULT_LIMIT;
			}
			query.setFirstResult(start.intValue());
			query.setMaxResults(limit.intValue());
			return query.getResultList();
		} catch (Exception e) {
			log.debug("通过jpql获取总分页结果的时候出错了", e);
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> queryBySql(String ql, Map<Integer, Object> params, Long start, Long limit) {
		Query query = entityManager.createNativeQuery(ql, persistentClass);
		if (params != null) {
			for (Integer key : params.keySet()) {
				Object value = params.get(key);
				log.debug("sql赋值[" + key + "：" + value + "]");
				if (value instanceof Date) {
					Date date = (Date) value;
					query.setParameter(key, date, TemporalType.TIMESTAMP);
				} else if (value instanceof Calendar) {
					Calendar date = (Calendar) value;
					query.setParameter(key, date, TemporalType.TIMESTAMP);
				} else {
					query.setParameter(key, value);
				}
			}
		}
		// 设置分页
		if (start == null) {
			start = DEFAULT_START;
		}
		if (limit == null) {
			limit = DEFAULT_LIMIT;
		}
		query.setFirstResult(start.intValue());
		query.setMaxResults(limit.intValue());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryFieldsByJpql(String ql, Map<String, Object> params, Long start, Long limit) {
		Query query = entityManager.createQuery(ql);
		query.unwrap(QueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if(params != null) {
			for (String key : params.keySet()) {
				String keyString = key.replace(".", "_");
				Object value = params.get(key);
				log.debug("jpql赋值[" + keyString + "：" + value + "]");
				if (value instanceof Date) {
					Date date = (Date) value;
					query.setParameter(keyString, date, TemporalType.TIMESTAMP);
				} else if (value instanceof Calendar) {
					Calendar date = (Calendar) value;
					query.setParameter(keyString, date, TemporalType.TIMESTAMP);
				} else {
					query.setParameter(keyString, value);
				}
			}
		}

		// 设置分页
		if (start == null) {
			start = DEFAULT_START;
		}
		if (limit == null) {
			limit = DEFAULT_LIMIT;
		}
		query.setFirstResult(start.intValue());
		query.setMaxResults(limit.intValue());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryFieldsBySql(String sql, Map<Integer, Object> params, Long start, Long limit) {
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (params != null) {
			for (Integer key : params.keySet()) {
				Object value = params.get(key);
				log.debug("jpql赋值[" + key + "：" + value + "]");
				if (value instanceof Date) {
					Date date = (Date) value;
					query.setParameter(key, date, TemporalType.TIMESTAMP);
				} else if (value instanceof Calendar) {
					Calendar date = (Calendar) value;
					query.setParameter(key, date, TemporalType.TIMESTAMP);
				} else {
					query.setParameter(key, value);
				}
			}
		}

		// 设置分页
		if (start == null) {
			start = DEFAULT_START;
		}
		if (limit == null) {
			limit = DEFAULT_LIMIT;
		}
		query.setFirstResult(start.intValue());
		query.setMaxResults(limit.intValue());
		return query.getResultList();
	}

	@Override
	public List<T> query(String ql, Class<T> resultType, Map<String, Object> params, int start, int limit) {
		TypedQuery<T> query = entityManager.createQuery(ql, resultType);
		if (params != null) {
			for (String key : params.keySet()) {
				String keyString = key.replace(".", "_");
				Object value = params.get(key);
				log.debug("jpql赋值[" + keyString + "：" + value + "]");
				if (value instanceof Date) {
					Date date = (Date) value;
					query.setParameter(keyString, date, TemporalType.TIMESTAMP);
				} else if (value instanceof Calendar) {
					Calendar date = (Calendar) value;
					query.setParameter(keyString, date, TemporalType.TIMESTAMP);
				} else {
					query.setParameter(keyString, value);
				}
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List queryArrayByJpql(String ql, Map<String, Object> params, int start, int limit) {
		Query query = entityManager.createQuery(ql);
		if (params != null) {
			for (String key : params.keySet()) {
				String keyString = key.replace(".", "_");
				Object value = params.get(key);
				log.debug("jpql赋值[" + keyString + "：" + value + "]");
				if (value instanceof Date) {
					Date date = (Date) value;
					query.setParameter(keyString, date, TemporalType.TIMESTAMP);
				} else if (value instanceof Calendar) {
					Calendar date = (Calendar) value;
					query.setParameter(keyString, date, TemporalType.TIMESTAMP);
				} else {
					query.setParameter(keyString, value);
				}
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public List queryArrayBySql(String ql, Map<Integer, Object> params, int start, int limit) {
		Query query = entityManager.createNativeQuery(ql);
		if (params != null) {
			for (Integer key : params.keySet()) {
				Object value = params.get(key);
				log.debug("jpql赋值[" + key + "：" + value + "]");
				if (value instanceof Date) {
					Date date = (Date) value;
					query.setParameter(key, date, TemporalType.TIMESTAMP);
				} else if (value instanceof Calendar) {
					Calendar date = (Calendar) value;
					query.setParameter(key, date, TemporalType.TIMESTAMP);
				} else {
					query.setParameter(key, value);
				}
			}
		}
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}
