package com.giteshdalal.authservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.repository.ClientRepository;

@Service("clientServiceImpl")
public class ClientServiceImpl implements ClientDetailsService, ClientRegistrationService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired(required = true)
	private PasswordEncoder passwordEncoder;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Optional<ClientModel> client = clientRepo.findOptionalByClientId(clientId);
		if (client.isPresent()) {
			return client.get();
		} else {
			throw new ClientRegistrationException(String.format("Client with id [%s] not found", clientId));
		}
	}

	public ClientModel findClientByClientId(String clientId) throws NoSuchClientException {
		Optional<ClientModel> client = clientRepo.findOptionalByClientId(clientId);
		if (client.isPresent()) {
			return client.get();
		} else {
			throw new NoSuchClientException(String.format("Client with id [%s] not found", clientId));
		}
	}

	public ClientModel register(ClientModel client) throws ClientRegistrationException {
		if (StringUtils.isBlank(client.getClientId()) || StringUtils.isBlank(client.getEmail())) {
			throw new ClientRegistrationException("Client id and Email can not be left blank");
		} else if (this.clientExistsWithClientId(client.getClientId())) {
			throw new ClientRegistrationException(
					String.format("Client id [%s] is already taken", client.getClientId()));
		} else if (this.clientExistsWithEmail(client.getEmail())) {
			throw new ClientRegistrationException(
					String.format("Email [%s] is already associated with another client account.", client.getEmail()));
		} else {
			client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
			return clientRepo.save(client);
		}
	}

	private boolean clientExistsWithClientId(String clientId) {
		return clientRepo.existsByClientId(clientId);
	}

	private boolean clientExistsWithEmail(String email) {
		return clientRepo.existsByEmail(email);
	}

	@Override
	public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
		if (clientDetails instanceof ClientModel) {
			this.register((ClientModel) clientDetails);
		} else {
			throw new IllegalArgumentException("Unexpected type of ClientDetails object received");
		}
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
		if (clientDetails instanceof ClientModel) {
			this.update((ClientModel) clientDetails);
		} else {
			throw new IllegalArgumentException("Unexpected type of ClientDetails object received");
		}
	}

	public void update(ClientModel clientDetails) throws NoSuchClientException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
		Optional<ClientModel> client = clientRepo.findOptionalByClientId(clientId);
		if (client.isPresent()) {
			ClientModel clientModel = client.get();
			clientModel.setClientSecret(passwordEncoder.encode(secret));
			clientRepo.save(clientModel);
		} else {
			throw new NoSuchClientException(String.format("Client with id [%s] not found", clientId));
		}

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

}
