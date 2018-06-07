package com.giteshdalal.productservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.giteshdalal.productservice.resource.generated.ProductResource;

public interface ProductService {

	Page<ProductResource> findProducts(Pageable pageable);

}
