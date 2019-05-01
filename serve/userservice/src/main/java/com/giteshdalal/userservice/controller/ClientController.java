package com.giteshdalal.userservice.controller;

import java.util.Locale;

import com.giteshdalal.userservice.resource.generated.ClientResource;
import com.giteshdalal.userservice.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/clients")
public class ClientController extends AbstractServeController<ClientResource, Long, ClientService> {

	@GetMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<ClientResource> getById(@PathVariable("uid") Long uid, Locale locale) {
		return super.getById(uid, locale);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<Page<ClientResource>> getAll(Pageable pageable, @RequestParam(value = "q", required = false) String query,
			Locale locale) {
		return super.getAll(pageable, query, locale);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<ClientResource> add(@RequestBody ClientResource resource, Locale locale) {
		return super.add(resource, locale);
	}

	@PutMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<ClientResource> overwrite(@PathVariable("uid") Long uid, @RequestBody ClientResource resource, Locale locale) {
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
	protected void supportHateoas(ClientResource resource) {
		resource.add(ControllerLinkBuilder.linkTo(getClass()).slash(resource.getUid()).withSelfRel());
	}

}
