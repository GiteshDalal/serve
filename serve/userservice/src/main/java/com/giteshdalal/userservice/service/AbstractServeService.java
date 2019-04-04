package com.giteshdalal.userservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.giteshdalal.userservice.exception.BadRequestUserServiceException;
import com.giteshdalal.userservice.exception.NotFoundUserServiceException;
import com.giteshdalal.userservice.repository.BaseServeRepository;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @param <T>
 * 		- Generated Model Class
 * @param <QT>
 * 		- Generated QModel Class
 * @param <RT>
 * 		- Generated Respective Resource Class
 * @param <ID>
 * 		- Identifier Class
 * @param <REP>
 * 		- Implemented BaseServeRepository Class
 * @author Serve Engine
 */
public abstract class AbstractServeService<T, QT extends EntityPath<?>, RT, ID, REP extends BaseServeRepository<T, QT, ID>>
		implements BaseServeService<RT, ID> {

	private final Class<T> modelClass;
	private final Class<RT> resourceClass;

	@Autowired
	protected REP repo;

	@Autowired
	protected ModelMapper mapper;

	public AbstractServeService(Class<T> modelClass, Class<RT> resourceClass) {
		this.modelClass = modelClass;
		this.resourceClass = resourceClass;
	}

	@Override
	public Page<RT> findAll(Predicate predicate, Pageable pageable) {
		Page<T> page = repo.findAll(predicate, pageable);
		return page.map(this::mapToResource);
	}

	@Override
	public Optional<RT> findByUid(ID uid) {
		Optional<T> entity = repo.findById(uid);
		return entity.map(this::mapToResource);
	}

	@Override
	public RT save(RT resource) {
		T entity;
		try {
			entity = this.mapToModel(resource);
			entity = repo.save(entity);
		} catch (HibernateException e) {
			throw new BadRequestUserServiceException("Invalid input data object. Mandatory field might be missing.", e);
		}
		return this.mapToResource(entity);
	}

	@Override
	public RT update(ID uid, RT resource) {
		Optional<T> e = repo.findById(uid);
		if (e.isPresent()) {
			try {
				T entity = mapToModel(resource);
				return mapToResource(repo.save(entity));
			} catch (HibernateException ex) {
				throw new BadRequestUserServiceException("Invalid input data object.", ex);
			}
		} else {
			throw new NotFoundUserServiceException("Object with uid : '" + uid + "' not found!");
		}
	}

	@Override
	public void patch(ID uid, JsonNode updates) {
		Optional<T> e = repo.findById(uid);
		if (e.isPresent()) {
			try {
				T entity = e.get();
				mapper.map(updates, entity);
				repo.save(entity);
			} catch (HibernateException ex) {
				throw new BadRequestUserServiceException("Invalid input data object.", ex);
			}
		} else {
			throw new NotFoundUserServiceException("Object with uid : '" + uid + "' not found!");
		}
	}

	@Override
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

