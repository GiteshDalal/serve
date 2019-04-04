package com.giteshdalal.userservice.service.impl;

import com.giteshdalal.userservice.model.generated.QPrivilegeModel;
import com.giteshdalal.userservice.model.generated.PrivilegeModel;
import com.giteshdalal.userservice.repository.generated.PrivilegeRepository;
import com.giteshdalal.userservice.resource.generated.PrivilegeResource;
import com.giteshdalal.userservice.service.AbstractServeService;
import com.giteshdalal.userservice.service.PrivilegeService;
import org.springframework.stereotype.Service;

/**
 * @author gitesh
 */
@Service
public class PrivilegeServiceImpl extends AbstractServeService<PrivilegeModel, QPrivilegeModel, PrivilegeResource, Long, PrivilegeRepository>
		implements PrivilegeService {

	public PrivilegeServiceImpl() {
		super(PrivilegeModel.class, PrivilegeResource.class);
	}
}
