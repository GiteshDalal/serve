package com.giteshdalal.userservice.controller;

import com.giteshdalal.userservice.resource.generated.RoleResource;
import com.giteshdalal.userservice.service.RoleService;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gitesh
 */
@RestController
@RequestMapping("/roles")
public class RoleController extends AbstractServeController<RoleResource, Long, RoleService> {

	@Override
	protected void supportHateoas(RoleResource resource) {
		resource.add(ControllerLinkBuilder.linkTo(getClass()).slash(resource.getUid()).withSelfRel());
	}

}
