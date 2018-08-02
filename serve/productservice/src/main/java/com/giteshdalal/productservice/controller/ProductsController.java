package com.giteshdalal.productservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.giteshdalal.productservice.resource.generated.ProductResource;
import com.giteshdalal.productservice.service.ProductService;

/**
 * @author gitesh
 *
 */
@RestController
@RequestMapping("/")
@PreAuthorize("hasRole('USER')")
public class ProductsController extends AbstractServeController<ProductResource, Long, ProductService> {

}
