package com.giteshdalal.productservice.service.impl;

import org.springframework.stereotype.Service;

import com.giteshdalal.productservice.model.generated.ProductModel;
import com.giteshdalal.productservice.model.generated.QProductModel;
import com.giteshdalal.productservice.repository.ServeProductRepository;
import com.giteshdalal.productservice.resource.generated.ProductResource;
import com.giteshdalal.productservice.service.AbstractServeService;
import com.giteshdalal.productservice.service.ProductService;

/**
 * @author gitesh
 *
 */
@Service
public class ProductServiceImpl
		extends AbstractServeService<ProductModel, QProductModel, ProductResource, Long, ServeProductRepository>
		implements ProductService {

	public ProductServiceImpl() {
		super(ProductModel.class, ProductResource.class);
	}
}
