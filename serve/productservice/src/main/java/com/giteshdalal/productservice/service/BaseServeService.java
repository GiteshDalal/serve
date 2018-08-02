package com.giteshdalal.productservice.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Predicate;

/**
 * @author gitesh
 *
 * @param <RT>
 * @param <ID>
 */
public interface BaseServeService<RT, ID> {

	Page<RT> findAll(Predicate predicate, Pageable pageable);

	Optional<RT> findByUid(ID uid);

	RT save(RT resource);
	
	void patch(ID uid, Map<String, Object> updates);

	void deleteByUid(ID uid);

}
