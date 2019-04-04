package com.giteshdalal.userservice.service.impl;

import com.giteshdalal.userservice.model.generated.QUserModel;
import com.giteshdalal.userservice.model.generated.UserModel;
import com.giteshdalal.userservice.repository.generated.UserRepository;
import com.giteshdalal.userservice.resource.generated.UserResource;
import com.giteshdalal.userservice.service.AbstractServeService;
import com.giteshdalal.userservice.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author gitesh
 */
@Service
public class UserServiceImpl extends AbstractServeService<UserModel, QUserModel, UserResource, Long, UserRepository>
		implements UserService {

	public UserServiceImpl() {
		super(UserModel.class, UserResource.class);
	}
}
