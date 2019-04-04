package com.giteshdalal.userservice.service.impl;

import com.giteshdalal.userservice.model.generated.ClientModel;
import com.giteshdalal.userservice.model.generated.QClientModel;
import com.giteshdalal.userservice.repository.generated.ClientRepository;
import com.giteshdalal.userservice.resource.generated.ClientResource;
import com.giteshdalal.userservice.service.AbstractServeService;
import com.giteshdalal.userservice.service.ClientService;
import org.springframework.stereotype.Service;

/**
 * @author gitesh
 */
@Service
public class ClientServiceImpl extends AbstractServeService<ClientModel, QClientModel, ClientResource, Long, ClientRepository>
		implements ClientService {

	public ClientServiceImpl() {
		super(ClientModel.class, ClientResource.class);
	}
}
