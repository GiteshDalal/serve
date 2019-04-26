package com.giteshdalal.authservice.repository;

import java.util.Optional;

import com.giteshdalal.authservice.model.QUserModel;
import com.giteshdalal.authservice.model.UserModel;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface UserRepository extends BaseServeRepository<UserModel, QUserModel, Long> {

	Optional<UserModel> findOptionalByUsername(String username);

	Optional<UserModel> findOptionalByResetToken(String resetToken);

	long countByUsername(String username);

	long countByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	@Override
	default void customize(QuerydslBindings bindings, QUserModel model) {
		bindings.bind(model.firstName, model.lastName, model.username, model.email).first(StringExpression::containsIgnoreCase);
		bindings.excluding(model.password);
	}

}
