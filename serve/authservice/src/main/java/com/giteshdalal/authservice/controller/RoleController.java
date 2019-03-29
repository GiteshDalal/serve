package com.giteshdalal.authservice.controller;

import java.util.Locale;

import com.giteshdalal.authservice.exceptions.NotFoundAuthServiceException;
import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.resource.RoleResource;
import com.giteshdalal.authservice.service.AuthorityService;
import com.querydsl.core.types.Predicate;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author gitesh
 */
@Controller
@RequestMapping(value = "/api/roles", produces = { MediaType.APPLICATION_JSON_VALUE })
public class RoleController {

	@Autowired
	private AuthorityService authorityService;

	@GetMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<RoleResource> getById(@PathVariable("uid") Long uid, Locale locale) {
		RoleResource resource = authorityService.findRoleById(uid)
				.orElseThrow(() -> new NotFoundAuthServiceException("Role with id : '" + uid + "' not found!"));
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<PagedResources<Resource<RoleResource>>> getAllResources(Pageable pageable,
			@QuerydslPredicate(root = RoleModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<RoleResource> assembler,
			Locale locale) {
		Page<RoleResource> resources = authorityService.findAllRoles(predicate, pageable);
		return new ResponseEntity<>(assembler.toResource(resources), HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<RoleResource> add(@RequestBody RoleResource resource, Locale locale) {
		RoleResource newRole = authorityService.saveRole(resource);
		return new ResponseEntity<>(newRole, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<RoleResource> overwrite(@PathVariable("uid") Long uid, @RequestBody RoleResource resource, Locale locale) {
		RoleResource updated = authorityService.updateRole(uid, resource);
		return new ResponseEntity<>(updated, HttpStatus.CREATED);
	}

	@DeleteMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:DELETE')")
	public HttpEntity<?> overwrite(@PathVariable("uid") Long uid, Locale locale) {
		authorityService.deleteRoleById(uid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
