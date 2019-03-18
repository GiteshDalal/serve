package com.giteshdalal.authservice.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author gitesh
 *
 * @param <T>
 * @param <QT>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseServeRepository<T, QT extends EntityPath<?>, ID>
		extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<QT> {

	@Override
	default void customize(QuerydslBindings bindings, QT model) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
