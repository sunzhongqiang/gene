package com.mmk.gene.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 基础服务类，写了通用的方法，继承之后不需要每个类再写一遍
 * @author 孙中强 sunzhongqiang
 *
 * @param <T> 实体类
 * @param <ID> 主键
 */
public interface BaseService<T, ID> {

	/**
	 * 检查是否存在主键为id的实体
	 * 
	 * @param id
	 *            不能为空
	 * @return 如果有返回true，其他为false
	 * @throws IllegalArgumentException
	 *             if {@code id} is {@literal null}
	 */
	boolean exists(ID id);

	/**
	 * 更加主键获得一个引用，并没有从数据库中获取数据
	 * 
	 * @param id
	 *            主键
	 * @return 主键对应的对象引用
	 */
	T get(ID id);

	/**
	 * 从数据库中根据id获取一个实体
	 * 
	 * @param id
	 *            主键
	 * @return 主键对应的实体对象
	 */
	T find(ID id);

	/**
	 * 保存或者更新一个实体
	 * 
	 * @param entity   要保存的对象
	 * @return 持久化后的对象
	 */

	T save(T entity);
	
	/**
	 * 保存或者更新一组实体
	 * 
	 * @param entities  要保存的对象
	 * @param <S>  泛型对象 
	 * @return 保存后的结果
	 */
	<S extends T> List<S> save(Iterable<S> entities);

	/**
	 * 批量删除一组实体
	 * 
	 * @param entities
	 *            要删除的实体
	 */
	void deleteInBatch(Iterable<T> entities);

	/**
	 * 批量删除所有
	 */
	void deleteAllInBatch();

	/**
	 * 返回所有实体
	 * 
	 * @return 所有实体
	 */
	Iterable<T> findAll();

	/**
	 * 根据给定的主键返回所有
	 * 
	 * @param ids
	 *            主键s
	 * @return 符合条件的一组结果集
	 */
	Iterable<T> findAll(Iterable<ID> ids);

	/**
	 * 统计总数
	 * 
	 * @return 现在实体中的所有数量
	 */
	long count();

	/**
	 * 删除给定的实体
	 * 
	 * @param entity 要删除的实体
	 * @throws IllegalArgumentException
	 *             in case the given entity is {@literal null}.
	 */
	void delete(T entity);

	/**
	 * 批量删除实体
	 * 
	 * @param entities 要删除的实体
	 * @throws IllegalArgumentException
	 *             in case the given {@link Iterable} is {@literal null}.
	 */
	void delete(Iterable<? extends T> entities);

	/**
	 * 清空所有表
	 */
	void deleteAll();

	/**
	 * 分页返回所有的实体
	 * 
	 * @param pageable 分页参数
	 * @return a page of entities 分页结果
	 */
	Page<T> findAll(Pageable pageable);
	

}
