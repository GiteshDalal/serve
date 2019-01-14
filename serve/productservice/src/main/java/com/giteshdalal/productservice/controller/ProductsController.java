package com.giteshdalal.productservice.controller;

import java.util.Locale;

import com.giteshdalal.productservice.model.generated.ProductModel;
import com.giteshdalal.productservice.resource.generated.ProductResource;
import com.giteshdalal.productservice.service.ProductService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gitesh
 */
@RestController
@RequestMapping("/")
@PreAuthorize("hasRole('USER')")
public class ProductsController extends AbstractServeController<ProductResource, Long, ProductService> {

	@Override
	@GetMapping
	public HttpEntity<PagedResources<Resource<ProductResource>>> getAllResources(Pageable pageable,
			@QuerydslPredicate(root = ProductModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, PagedResourcesAssembler<ProductResource> assembler,
			Locale locale) {
		return this.getAll(pageable, predicate, parameters, assembler, locale);
	}

}
