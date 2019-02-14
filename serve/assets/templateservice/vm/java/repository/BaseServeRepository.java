package ${service.group.toLowerCase()}.${service.name.toLowerCase()}service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;

/**
 * @author Serve Engine
 *
 * @param <T>
 * @param <QT>
 * @param <ID>
 */
public interface BaseServeRepository<T, QT extends EntityPath<?>, ID>
		extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<QT> {

	@Override
	default void customize(QuerydslBindings bindings, QT model) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
