package com.giteshdalal.productservice.service;

import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.giteshdalal.productservice.repository.BaseServeRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;

public abstract class AbstractServeService<T, QT extends EntityPath<?>, RT, ID, REP extends BaseServeRepository<T, QT, ID>> {

	final Class<T> modelClass;
	final Class<RT> resourceClass;

	@Autowired
	protected REP repo;

	@Autowired
	protected ModelMapper mapper;

	public AbstractServeService(Class<T> modelClass, Class<RT> resourceClass) {
		this.modelClass = modelClass;
		this.resourceClass = resourceClass;
	}

	public Page<RT> findAll(Predicate predicate, Pageable pageable) {
		Page<T> page = repo.findAll(predicate, pageable);
		return page.map(model -> this.mapToResource(model));
	}

	public Optional<RT> findByUid(ID uid) {
		Optional<T> entity = repo.findById(uid);
		return entity.isPresent() ? Optional.ofNullable(this.mapToResource(entity.get())) : Optional.empty();
	}

	public RT save(RT resource) {
		T entity = this.mapToModel(resource);
		entity = repo.save(entity);
		return this.mapToResource(entity);
	}

	public void patch(ID uid, Map<String, Object> updates) {

	}

	public void deleteByUid(ID uid) {
		repo.deleteById(uid);
	}

	protected RT mapToResource(T model) {
		return mapper.map(model, resourceClass);
	}

	protected T mapToModel(RT resource) {
		return mapper.map(resource, modelClass);

	}
}
