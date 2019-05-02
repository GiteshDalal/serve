package com.giteshdalal.userservice.controller;

import com.giteshdalal.userservice.resource.generated.UserResource;
import com.giteshdalal.userservice.service.UserService;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Serve Engine
 */
@RestController
@RequestMapping("/users")
public class UserController extends AbstractServeController<UserResource, Long, UserService> {

	@Override
	protected void supportHateoas(UserResource resource) {
		resource.add(ControllerLinkBuilder.linkTo(getClass()).slash(resource.getUid()).withSelfRel());
	}

}
