package ${service.group.toLowerCase()}.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

import ${service.group.toLowerCase()}.exception.NotFound${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException;
import ${service.group.toLowerCase()}.repository.BaseServeRepository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Serve Engine
 *
 * @param <T> - Generated Model Class
 * @param <QT> - Generated QModel Class
 * @param <RT> - Generated Respective Resource Class
 * @param <ID> - Identifier Class
 * @param <REP> - Implemented BaseServeRepository Class
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
		T entity = this.mapToModel(resource);
		entity = repo.save(entity);
		return this.mapToResource(entity);
	}

	@Override
	public RT update(ID uid, RT resource) {
		Optional<T> e = repo.findById(uid);
		if (e.isPresent()) {
			T entity = mapToModel(resource);
			return mapToResource(repo.save(entity));
		} else {
			throw new NotFound${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException("Object with uid : '" + uid + "' not found!");
		}
	}

	@Override
	public void patch(ID uid, JsonNode updates) {
		Optional<T> e = repo.findById(uid);
		if (e.isPresent()) {
			T entity = e.get();
			mapper.map(updates, entity);
			repo.save(entity);
		} else {
			throw new NotFound${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException("Object with uid : '" + uid + "' not found!");
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

