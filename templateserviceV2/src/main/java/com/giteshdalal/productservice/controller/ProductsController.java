package com.giteshdalal.productservice.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giteshdalal.productservice.model.generated.ProductModel;
import com.giteshdalal.productservice.resource.generated.ProductResource;
import com.giteshdalal.productservice.service.ProductService;
import com.querydsl.core.types.Predicate;

/**
 * @author gitesh
 *
 */
@RestController
@RequestMapping("/")
public class ProductsController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public HttpEntity<PagedResources<Resource<ProductResource>>> getProducts(Pageable pageable,
			@QuerydslPredicate(root = ProductModel.class) Predicate predicate,
			@RequestParam MultiValueMap<String, String> parameters, 
			PagedResourcesAssembler<ProductResource> assembler, Locale locale) {
		Page<ProductResource> products = productService.findProducts(predicate, pageable);
		return new ResponseEntity<>(assembler.toResource(products), HttpStatus.OK);
	}

	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public HttpEntity<ProductResource> addProduct(@RequestBody ProductResource product, Locale locale) {
		System.out.println(product);
		ProductResource newProduct = productService.addProduct(product);
		return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
	}
}