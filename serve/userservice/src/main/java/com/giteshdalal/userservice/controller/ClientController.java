package com.giteshdalal.userservice.controller;

import com.giteshdalal.userservice.resource.generated.ClientResource;
import com.giteshdalal.userservice.service.ClientService;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gitesh
 */
@RestController
@RequestMapping("/clients")
public class ClientController extends AbstractServeController<ClientResource, Long, ClientService> {

	@Override
	protected void supportHateoas(ClientResource resource) {
		resource.add(ControllerLinkBuilder.linkTo(getClass()).slash(resource.getUid()).withSelfRel());
	}

}
