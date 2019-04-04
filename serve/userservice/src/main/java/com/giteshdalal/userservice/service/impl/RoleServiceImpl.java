package com.giteshdalal.userservice.service.impl;

import com.giteshdalal.userservice.model.generated.QRoleModel;
import com.giteshdalal.userservice.model.generated.RoleModel;
import com.giteshdalal.userservice.repository.generated.RoleRepository;
import com.giteshdalal.userservice.resource.generated.RoleResource;
import com.giteshdalal.userservice.service.AbstractServeService;
import com.giteshdalal.userservice.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author gitesh
 */
@Service
public class RoleServiceImpl extends AbstractServeService<RoleModel, QRoleModel, RoleResource, Long, RoleRepository>
		implements RoleService {

	public RoleServiceImpl() {
		super(RoleModel.class, RoleResource.class);
	}
}
