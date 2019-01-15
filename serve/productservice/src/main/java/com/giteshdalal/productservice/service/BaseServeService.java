package com.giteshdalal.productservice.service;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author gitesh
 *
 * @param <RT> - Generated Resource Type
 * @param <ID> - Identifier Type
 */
public interface BaseServeService<RT, ID> {

	Page<RT> findAll(Predicate predicate, Pageable pageable);

	Optional<RT> findByUid(ID uid);

	RT save(RT resource);
	
	void patch(ID uid, JsonNode updates);

	void deleteByUid(ID uid);

}
