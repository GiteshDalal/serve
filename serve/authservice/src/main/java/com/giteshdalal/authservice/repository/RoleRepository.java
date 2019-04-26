package com.giteshdalal.authservice.repository;

import java.util.Optional;

import com.giteshdalal.authservice.model.QRoleModel;
import com.giteshdalal.authservice.model.RoleModel;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface RoleRepository extends BaseServeRepository<RoleModel, QRoleModel, Long> {

	Optional<RoleModel> findOptionalByName(String name);

	@Override
	default void customize(QuerydslBindings bindings, QRoleModel model) {
		bindings.bind(model.name).first(StringExpression::containsIgnoreCase);
	}
}
