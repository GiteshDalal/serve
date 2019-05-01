package com.giteshdalal.authservice.repository;

import java.util.Optional;

import com.giteshdalal.authservice.model.UserModel;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface UserRepository extends BaseServeRepository<UserModel, Long> {

	Optional<UserModel> findOptionalByUsername(String username);

	Optional<UserModel> findOptionalByResetToken(String resetToken);

	boolean existsByUsername(String username);

	boolean existsByResetToken(String resetToken);

	boolean existsByEmail(String email);
}
