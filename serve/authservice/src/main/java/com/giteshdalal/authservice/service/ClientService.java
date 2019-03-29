package com.giteshdalal.authservice.service;

import java.util.List;
import java.util.Optional;

import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.resource.ClientResource;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;

/**
 * @author gitesh
 */
public interface ClientService extends ClientDetailsService, ClientRegistrationService {

	ClientModel findClientByClientId(String clientId) throws NoSuchClientException;

	ClientModel register(ClientModel client) throws ClientRegistrationException;

	boolean clientExistsWithClientId(String clientId);

	boolean clientExistsWithEmail(String email);

	@Override
	void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException;

	@Override
	void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException;

	ClientModel update(ClientModel clientDetails) throws NoSuchClientException;

	@Override
	void updateClientSecret(String clientId, String secret) throws NoSuchClientException;

	@Override
	void removeClientDetails(String clientId) throws NoSuchClientException;

	@Override
	List<ClientDetails> listClientDetails();

	Optional<ClientResource> findClientById(Long uid);

	Page<ClientResource> findAllClients(Predicate predicate, Pageable pageable);

	ClientResource saveClient(ClientResource resource);

	ClientResource updateClient(Long uid, ClientResource resource);

	void deleteClientById(Long uid);
}
