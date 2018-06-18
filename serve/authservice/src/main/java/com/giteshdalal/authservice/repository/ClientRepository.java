package com.giteshdalal.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giteshdalal.authservice.model.ClientModel;

/**
 * @author gitesh
 *
 */
@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {

	Optional<ClientModel> findOptionalByClientId(String clientId);

	long countByClientId(String clientId);

	long countByEmail(String email);

	boolean existsByClientId(String clientId);

	boolean existsByEmail(String email);

}
