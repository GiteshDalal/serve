package com.giteshdalal.authservice.repository;

import java.util.Optional;

import com.giteshdalal.authservice.model.ClientModel;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface ClientRepository extends BaseServeRepository<ClientModel, Long> {

	Optional<ClientModel> findOptionalByClientId(String clientId);

	boolean existsByClientId(String clientId);

	boolean existsByEmail(String email);
}
