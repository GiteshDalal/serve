package com.giteshdalal.productservice.service.impl;

import com.giteshdalal.productservice.model.generated.ProductModel;
import com.giteshdalal.productservice.model.generated.QProductModel;
import com.giteshdalal.productservice.repository.generated.ProductRepository;
import com.giteshdalal.productservice.resource.generated.ProductResource;
import com.giteshdalal.productservice.service.AbstractServeService;
import com.giteshdalal.productservice.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author gitesh
 */
@Service
public class ProductServiceImpl extends AbstractServeService<ProductModel, QProductModel, ProductResource, Long, ProductRepository>
		implements ProductService {

	public ProductServiceImpl() {
		super(ProductModel.class, ProductResource.class);
	}
}
