package com.giteshdalal.productservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.giteshdalal.productservice.resource.generated.ProductResource;
import com.querydsl.core.types.Predicate;

/**
 * @author gitesh
 *
 */
public interface ProductService {

	Page<ProductResource> findProducts(Predicate predicate, Pageable pageable);

}
