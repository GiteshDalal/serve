package com.giteshdalal.userservice.service.impl;

import java.util.Locale;
import java.util.Map;

import com.giteshdalal.userservice.model.generated.UserModel;
import com.giteshdalal.userservice.proxy.EmailServiceProxy;
import com.giteshdalal.userservice.repository.generated.UserRepository;
import com.giteshdalal.userservice.resource.generated.UserResource;
import com.giteshdalal.userservice.service.AbstractServeService;
import com.giteshdalal.userservice.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gitesh
 */
@Service
public class UserServiceImpl extends AbstractServeService<UserModel, UserResource, Long, UserRepository> implements UserService {

	@Autowired
	private EmailServiceProxy emailServiceProxy;

	public UserServiceImpl() {
		super(UserModel.class, UserResource.class);
	}

	@Override
	public UserResource update(Long uid, UserResource resource, Locale locale) {
		UserResource saved = super.update(uid, resource, locale);

		Map<String, String> emailResourceMap = new HashedMap();
		emailResourceMap.put("email", saved.getEmail());
		emailResourceMap.put("subject", "Account updated");
		emailResourceMap.put("title", "Account no." + uid);
		emailResourceMap.put("message", "Your account has been updated successfully");
		emailServiceProxy.triggerEmail("simple-message", emailResourceMap, locale);

		return saved;
	}
}
