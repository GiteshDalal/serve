package com.giteshdalal.userservice.controller;

import com.giteshdalal.userservice.resource.generated.PrivilegeResource;
import com.giteshdalal.userservice.service.PrivilegeService;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gitesh
 */
@RestController
@RequestMapping("/privileges")
public class PrivilegeController extends AbstractServeController<PrivilegeResource, Long, PrivilegeService> {

	@Override
	protected void supportHateoas(PrivilegeResource resource) {
		resource.add(ControllerLinkBuilder.linkTo(getClass()).slash(resource.getUid()).withSelfRel());
	}

}
