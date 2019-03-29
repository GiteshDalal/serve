package com.giteshdalal.authservice.controller;

import java.util.Locale;
import javax.security.auth.login.AccountException;

import com.giteshdalal.authservice.exceptions.NotFoundAuthServiceException;
import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.resource.ClientResource;
import com.giteshdalal.authservice.service.ClientService;
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
@RequestMapping(value = "/api/clients", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<ClientResource> getById(@PathVariable("uid") Long uid, Locale locale) {
		ClientResource resource = clientService.findClientById(uid)
				.orElseThrow(() -> new NotFoundAuthServiceException("Client with uid : '" + uid + "' not found!"));
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:READ')")
	public HttpEntity<PagedResources<Resource<ClientResource>>> getAllResources(Pageable pageable,
			@QuerydslPredicate(root = ClientModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<ClientResource> assembler,
			Locale locale) {
		Page<ClientResource> resources = clientService.findAllClients(predicate, pageable);
		return new ResponseEntity<>(assembler.toResource(resources), HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<ClientResource> add(@RequestBody ClientResource resource, Locale locale) throws AccountException {
		ClientResource newClient = clientService.saveClient(resource);
		return new ResponseEntity<>(newClient, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{uid}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:WRITE')")
	public HttpEntity<ClientResource> overwrite(@PathVariable("uid") Long uid, @RequestBody ClientResource resource, Locale locale) {
		ClientResource updated = clientService.updateClient(uid, resource);
		return new ResponseEntity<>(updated, HttpStatus.CREATED);
	}

	@DeleteMapping("/{uid}")
	@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE:DELETE')")
	public HttpEntity<?> overwrite(@PathVariable("uid") Long uid, Locale locale) {
		clientService.deleteClientById(uid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
