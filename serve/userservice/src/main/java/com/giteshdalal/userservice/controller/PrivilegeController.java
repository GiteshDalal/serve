package com.giteshdalal.userservice.controller;

import java.util.Locale;

import com.giteshdalal.userservice.model.generated.PrivilegeModel;
import com.giteshdalal.userservice.resource.generated.PrivilegeResource;
import com.giteshdalal.userservice.service.PrivilegeService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gitesh
 */
@RestController
@RequestMapping("/api/privileges")
public class PrivilegeController extends AbstractServeController<PrivilegeResource, Long, PrivilegeService> {

	@GetMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<PrivilegeResource> getById(@PathVariable("uid") Long uid, Locale locale) {
		return super.getById(uid, locale);
	}

	@Override
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<PagedResources<Resource<PrivilegeResource>>> getAllResources(Pageable pageable,
			@QuerydslPredicate(root = PrivilegeModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<PrivilegeResource> assembler,
			Locale locale) {
		return super.getAll(pageable, predicate, parameters, assembler, locale);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<PrivilegeResource> add(@RequestBody PrivilegeResource resource, Locale locale) {
		return super.add(resource, locale);
	}

	@PutMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<PrivilegeResource> overwrite(@PathVariable("uid") Long uid, @RequestBody PrivilegeResource resource,
			Locale locale) {
		resource.setUid(uid);
		return super.overwrite(uid, resource, locale);
	}

	@PatchMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<?> patch(@PathVariable("uid") Long uid, HttpEntity<String> httpEntity, Locale locale) {
		return super.patch(uid, httpEntity, locale);
	}

	@DeleteMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:DELETE')")
	public HttpEntity<?> delete(@PathVariable("uid") Long uid, Locale locale) {
		return super.delete(uid, locale);
	}

	@Override
	protected void supportHateoas(PrivilegeResource resource) {
		resource.add(ControllerLinkBuilder.linkTo(getClass()).slash(resource.getUid()).withSelfRel());
	}

}
