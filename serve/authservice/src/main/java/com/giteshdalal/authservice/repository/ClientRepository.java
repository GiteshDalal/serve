package com.giteshdalal.authservice.repository;

import java.util.Optional;

import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.model.QClientModel;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface ClientRepository extends BaseServeRepository<ClientModel, QClientModel, Long> {

	Optional<ClientModel> findOptionalByClientId(String clientId);

	long countByClientId(String clientId);

	long countByEmail(String email);

	boolean existsByClientId(String clientId);

	boolean existsByEmail(String email);

	@Override
	default void customize(QuerydslBindings bindings, QClientModel model) {
		bindings.bind(model.clientId, model.email).first(StringExpression::containsIgnoreCase);
		bindings.excluding(model.clientSecret);
	}

}
