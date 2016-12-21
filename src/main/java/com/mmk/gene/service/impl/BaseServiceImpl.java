package com.mmk.gene.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.mmk.gene.service.BaseService;
/**
 * 基本服务类的具体实现{@link BaseService}
 * @author 孙中强
 *
 * @param <T> 对哪个实体类进行封装管理
 * @param <ID> 实体类所使用的主键类型
 */
public class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T,ID> {
	
	private JpaRepository<T,ID> repository;
	
	public BaseServiceImpl(JpaRepository<T,ID> repository){
		this.repository = repository;
	}

	@Override
	public boolean exists(ID id) {
		return repository.exists(id);
	}

	@Override
	public T get(ID id) {
		return repository.getOne(id);
	}

	@Override
	public T find(ID id) {
		return repository.findOne(id);
	}

	@Override
	public T save(T entity) {
		return repository.save(entity);
	}

	@Transactional
	@Override
	public <S extends T> List<S> save(Iterable<S> entities) {
		return repository.save(entities);
	}

	@Override
	public void deleteInBatch(Iterable<T> entities) {
		repository.deleteInBatch(entities);
		
	}

	@Override
	public void deleteAllInBatch() {
		repository.deleteAllInBatch();
	}

	@Override
	public Iterable<T> findAll() {
		return repository.findAll();
	}

	@Override
	public Iterable<T> findAll(Iterable<ID> ids) {
		return repository.findAll(ids);
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public void delete(T entity) {
		repository.delete(entity);
		
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		repository.delete(entities);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
