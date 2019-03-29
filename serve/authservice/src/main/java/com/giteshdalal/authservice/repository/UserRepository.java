package com.giteshdalal.authservice.repository;

import java.util.Optional;

import com.giteshdalal.authservice.model.QUserModel;
import com.giteshdalal.authservice.model.UserModel;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface UserRepository extends BaseServeRepository<UserModel, QUserModel, Long> {

	Optional<UserModel> findOptionalByUsername(String username);

	long countByUsername(String username);

	long countByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
