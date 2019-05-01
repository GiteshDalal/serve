package com.giteshdalal.authservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.giteshdalal.authservice.exceptions.BadRequestAuthServiceException;
import com.giteshdalal.authservice.exceptions.NotFoundAuthServiceException;
import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.query.ModelSpecification;
import com.giteshdalal.authservice.query.SearchCriteria;
import com.giteshdalal.authservice.repository.ClientRepository;
import com.giteshdalal.authservice.resource.ClientResource;
import com.giteshdalal.authservice.service.ClientService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.hql.internal.ast.QuerySyntaxException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

/**
 * @author gitesh
 */
@Service("clientService")
public class ClientServiceImpl implements ClientService {

	@Autowired
	protected ModelMapper mapper;

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Optional<ClientModel> client = clientRepo.findOptionalByClientId(clientId);
		if (client.isPresent()) {
			return client.get();
		}
		throw new ClientRegistrationException(String.format("Client with id [%s] not found", clientId));
	}

	@Override
	public ClientModel findClientByClientId(String clientId) throws NoSuchClientException {
		Optional<ClientModel> client = clientRepo.findOptionalByClientId(clientId);
		if (client.isPresent()) {
			return client.get();
		}
		throw new NoSuchClientException(String.format("Client with id [%s] not found", clientId));
	}

	@Override
	public ClientModel register(ClientModel client) throws ClientRegistrationException {
		if (StringUtils.isBlank(client.getClientId()) || StringUtils.isBlank(client.getEmail())) {
			throw new ClientRegistrationException("Client id and Email can not be left blank");
		} else if (this.clientExistsWithClientId(client.getClientId())) {
			throw new ClientRegistrationException(String.format("Client id [%s] is already taken", client.getClientId()));
		} else if (this.clientExistsWithEmail(client.getEmail())) {
			throw new ClientRegistrationException(
					String.format("Email [%s] is already associated with another client account.", client.getEmail()));
		} else {
			client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
			return clientRepo.save(client);
		}
	}

	@Override
	public boolean clientExistsWithClientId(String clientId) {
		return clientRepo.existsByClientId(clientId);
	}

	@Override
	public boolean clientExistsWithEmail(String email) {
		return clientRepo.existsByEmail(email);
	}

	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		if (clientDetails instanceof ClientModel) {
			this.register((ClientModel) clientDetails);
			return;
		}
		throw new IllegalArgumentException("Unexpected type of ClientDetails object received");
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		if (clientDetails instanceof ClientModel) {
			this.update((ClientModel) clientDetails);
			return;
		}
		throw new IllegalArgumentException("Unexpected type of ClientDetails object received");
	}

	@Override
	public ClientModel update(ClientModel entity) throws NoSuchClientException {
		Objects.requireNonNull(entity, "ClientDetails object must not be null");

		Optional<ClientModel> client = clientRepo.findById(entity.getUid());
		if (client.isPresent()) {
			entity.setClientSecret(client.get().getClientSecret());
			return clientRepo.save(entity);
		}
		throw new NotFoundAuthServiceException("Client with uid : '" + entity.getUid() + "' not found!");
	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		Optional<ClientModel> client = clientRepo.findOptionalByClientId(clientId);
		if (client.isPresent()) {
			ClientModel clientModel = client.get();
			clientModel.setClientSecret(passwordEncoder.encode(secret));
			clientRepo.save(clientModel);
			return;
		}
		throw new NoSuchClientException(String.format("Client with id [%s] not found", clientId));
	}

	@Override
	public void removeClientDetails(String clientId) throws NoSuchClientException {
		ClientModel clientModel = this.findClientByClientId(clientId);
		clientRepo.delete(clientModel);
	}

	@Override
	public List<ClientDetails> listClientDetails() {
		return new ArrayList<>(clientRepo.findAll());
	}

	@Override
	public Optional findClientById(Long uid) {
		Optional<ClientModel> entity = clientRepo.findById(uid);
		return entity.isPresent() ? Optional.ofNullable(mapClient(entity.get())) : Optional.empty();
	}

	@Override
	public Page<ClientResource> findAll(List<SearchCriteria> params, Pageable pageable) {
		Page<ClientModel> page;
		if (CollectionUtils.isNotEmpty(params)) {
			Specification<ClientModel> spec = Specification.where(new ModelSpecification<>(params.get(0)));
			for (int i = 1; i < params.size(); i++) {
				spec = spec.and(new ModelSpecification<>(params.get(i)));
			}
			try {
				page = clientRepo.findAll(spec, pageable);
			} catch (QuerySyntaxException e) {
				throw new BadRequestAuthServiceException(
						"Invalid operation. Query operation not supported for this field type.", e);
			}
		} else {
			page = clientRepo.findAll(pageable);
		}
		return page.map(this::mapClient);
	}

	@Override
	public ClientResource saveClient(ClientResource resource) {
		resource.setUid(null);
		ClientModel entity = mapper.map(resource, ClientModel.class);
		return mapClient(register(entity));
	}

	@Override
	public ClientResource updateClient(Long uid, ClientResource resource) {
		ClientModel entity = mapper.map(resource, ClientModel.class);
		entity.setUid(uid);
		return mapClient(update(entity));
	}

	@Override
	public void deleteClientById(Long uid) {
		Optional<ClientModel> client = clientRepo.findById(uid);
		if (client.isPresent()) {
			clientRepo.delete(client.get());
			return;
		}
		throw new NotFoundAuthServiceException("Client with uid : '" + uid + "' not found!");
	}

	private ClientResource mapClient(ClientModel clientModel) {
		return mapper.map(clientModel, ClientResource.class);
	}

}
