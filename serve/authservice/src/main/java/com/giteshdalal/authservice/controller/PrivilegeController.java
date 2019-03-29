package com.giteshdalal.authservice.controller;

import java.util.Locale;

import com.giteshdalal.authservice.exceptions.NotFoundAuthServiceException;
import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.resource.PrivilegeResource;
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
@RequestMapping(value = "/api/privileges", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PrivilegeController {

	@Autowired
	private AuthorityService authorityService;

	@GetMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<PrivilegeResource> getById(@PathVariable("uid") Long uid, Locale locale) {
		PrivilegeResource resource = authorityService.findPrivilegeById(uid)
				.orElseThrow(() -> new NotFoundAuthServiceException("Privilege with id : '" + uid + "' not found!"));
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<PagedResources<Resource<PrivilegeResource>>> getAllResources(Pageable pageable,
			@QuerydslPredicate(root = PrivilegeModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<PrivilegeResource> assembler,
			Locale locale) {
		Page<PrivilegeResource> resources = authorityService.findAllPrivileges(predicate, pageable);
		return new ResponseEntity<>(assembler.toResource(resources), HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<PrivilegeResource> add(@RequestBody PrivilegeResource resource, Locale locale) {
		PrivilegeResource newPrivilege = authorityService.savePrivilege(resource);
		return new ResponseEntity<>(newPrivilege, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<PrivilegeResource> overwrite(@PathVariable("uid") Long uid, @RequestBody PrivilegeResource resource,
			Locale locale) {
		PrivilegeResource updated = authorityService.updatePrivilege(uid, resource);
		return new ResponseEntity<>(updated, HttpStatus.CREATED);
	}

	@DeleteMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:DELETE')")
	public HttpEntity<?> overwrite(@PathVariable("uid") Long uid, Locale locale) {
		authorityService.deletePrivilegeByUid(uid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
