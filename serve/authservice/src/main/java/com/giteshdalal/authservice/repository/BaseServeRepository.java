package com.giteshdalal.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @param <T>
 * 		- Generated Model Class
 * @param <ID>
 * 		- Identifier Type
 * @author gitesh
 */
@NoRepositoryBean
public interface BaseServeRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
