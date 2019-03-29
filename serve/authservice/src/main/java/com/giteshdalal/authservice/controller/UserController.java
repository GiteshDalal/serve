package com.giteshdalal.authservice.controller;

import java.util.Locale;

import javax.security.auth.login.AccountException;

import com.giteshdalal.authservice.exceptions.NotFoundAuthServiceException;
import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.resource.UserResource;
import com.giteshdalal.authservice.service.UserService;
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
@RequestMapping(value = "/api/users", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<UserResource> getById(@PathVariable("uid") Long uid, Locale locale) {
		UserResource resource = userService.findUserById(uid)
				.orElseThrow(() -> new NotFoundAuthServiceException("User with uid : '" + uid + "' not found!"));
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<PagedResources<Resource<UserResource>>> getAllResources(Pageable pageable,
			@QuerydslPredicate(root = UserModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<UserResource> assembler,
			Locale locale) {
		Page<UserResource> resources = userService.findAllUsers(predicate, pageable);
		return new ResponseEntity<>(assembler.toResource(resources), HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<UserResource> add(@RequestBody UserResource resource, Locale locale) throws AccountException {
		UserResource newUser = userService.saveUser(resource);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<UserResource> overwrite(@PathVariable("uid") Long uid, @RequestBody UserResource resource, Locale locale) {
		UserResource updated = userService.updateUser(uid, resource);
		return new ResponseEntity<>(updated, HttpStatus.CREATED);
	}

	@DeleteMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:DELETE')")
	public HttpEntity<?> overwrite(@PathVariable("uid") Long uid, Locale locale) {
		userService.deleteUserById(uid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
