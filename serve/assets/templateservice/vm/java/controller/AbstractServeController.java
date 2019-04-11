package ${service.group.toLowerCase()}.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Locale;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ${service.group.toLowerCase()}.exception.BadRequest${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException;
import ${service.group.toLowerCase()}.exception.NotFound${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException;
import ${service.group.toLowerCase()}.service.BaseServeService;
import com.querydsl.core.types.Predicate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Serve Engine
 *
 * @param <RT> - Generated Resource Type Class
 * @param <ID> - Identifier Type Class
 * @param <S> - Implemented BaseServeService Class
 */
public abstract class AbstractServeController<RT extends ResourceSupport, ID, S extends BaseServeService<RT, ID>> {

	@Autowired
	protected S service;

	protected HttpEntity<RT> getById(ID uid, Locale locale) {
		RT resource = service.findByUid(uid)
				.orElseThrow(() -> new NotFound${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException("Object with uid : '" + uid + "' not found!"));
		this.supportHateoas(resource);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	public abstract HttpEntity<PagedResources<Resource<RT>>> getAllResources(Pageable pageable, @QuerydslPredicate Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<RT> assembler, Locale locale);

	protected HttpEntity<PagedResources<Resource<RT>>> getAll(Pageable pageable, Predicate predicate,
			MultiValueMap<String, String> parameters, PagedResourcesAssembler<RT> assembler, Locale locale) {
		Page<RT> resources = service.findAll(predicate, pageable);
		this.supportHateoas(resources.getContent());
		return new ResponseEntity<>(assembler.toResource(resources), HttpStatus.OK);
	}

	protected HttpEntity<RT> add(RT resource, Locale locale) {
		RT newResource = service.save(resource);
		this.supportHateoas(newResource);
		return new ResponseEntity<>(newResource, HttpStatus.CREATED);
	}

	protected HttpEntity<RT> overwrite(ID uid, RT resource, Locale locale) {
		RT updated = service.update(uid, resource);
		this.supportHateoas(updated);
		return new ResponseEntity<>(updated, HttpStatus.CREATED);
	}

	protected HttpEntity<?> patch(ID uid, HttpEntity<String> httpEntity, Locale locale) {
		JsonNode jsonNode;
		try {
			jsonNode = new ObjectMapper().readTree(httpEntity.getBody());
		} catch (IOException e) {
			throw new BadRequest${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}ServiceException("Error translating request body: " + httpEntity.getBody(), e);
		}
		service.patch(uid, jsonNode);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	protected HttpEntity<?> delete(ID uid, Locale locale) {
		service.deleteByUid(uid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	protected abstract void supportHateoas(RT resource);

	protected void supportHateoas(Collection<RT> resources) {
		if (CollectionUtils.isNotEmpty(resources)) {
			resources.forEach(this::supportHateoas);
		}
	}
}