package com.giteshdalal.productservice.controller;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.giteshdalal.productservice.exception.NotFoundProductServiceException;
import com.giteshdalal.productservice.service.BaseServeService;
import com.querydsl.core.types.Predicate;

/**
 * @author gitesh
 *
 * @param <RT> - Generated Resource Type
 * @param <ID> - Identifier Type
 * @param <S> - Service Implementation of BaseServeService
 */
public abstract class AbstractServeController<RT, ID, S extends BaseServeService<RT, ID>> {

	@Autowired
	protected S service;

	@GetMapping("/{uid}")
	public HttpEntity<RT> getById(@PathVariable("uid") ID uid, Locale locale) {
		RT resource = service.findByUid(uid)
				.orElseThrow(() -> new NotFoundProductServiceException("Object with uid : '" + uid + "' not found!"));
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	/**
	 * Needs to be implemented to set @QuerydslPredicate root to be respective generated Model class
	 *
	 * Example
	 *
	 * @Override
	 * @GetMapping
	 * public HttpEntity<PagedResources<Resource<ResourceType>>> getAllResources(Pageable pageable,
	 * 		@QuerydslPredicate(root = <ModelType>.class) Predicate predicate,
	 * 		@RequestParam MultiValueMap<String ,  String> parameters, PagedResourcesAssembler<ResourceType> assembler,
	 * 		Locale locale) {
	 * 		return this.getAll(pageable, predicate, parameters, assembler, locale);
	 * }
	 *
	 * @param pageable - to allow pagination
	 * @param predicate @QuerydslPredicate(root = <ModelType>.class) - to enable querydsl
	 * @param parameters - parameters used to search
	 * @param assembler - assembler needed for querydsl
	 * @param locale
	 * @return
	 */
	public abstract HttpEntity<PagedResources<Resource<RT>>>  getAllResources(Pageable pageable, @QuerydslPredicate Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<RT> assembler,
			Locale locale);

	protected HttpEntity<PagedResources<Resource<RT>>> getAll(Pageable pageable, @QuerydslPredicate Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<RT> assembler,
			Locale locale) {
		Page<RT> resources = service.findAll(predicate, pageable);
		return new ResponseEntity<>(assembler.toResource(resources), HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<RT> add(@RequestBody RT resource, Locale locale) {
		RT newProduct = service.save(resource);
		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
	}

	@PatchMapping("/{uid}")
	public HttpEntity<?> patch(@PathVariable("uid") ID uid, @RequestBody Map<String, Object> updates, Locale locale) {
		service.patch(uid, updates);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{uid}")
	public HttpEntity<?> overwrite(@PathVariable("uid") ID uid, Locale locale) {
		service.deleteByUid(uid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
