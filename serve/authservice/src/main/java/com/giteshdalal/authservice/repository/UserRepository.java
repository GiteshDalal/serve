package com.giteshdalal.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giteshdalal.authservice.model.UserModel;

/**
 * @author gitesh
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

	Optional<UserModel> findOptionalByUsername(String username);

	long countByUsername(String username);

	long countByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
