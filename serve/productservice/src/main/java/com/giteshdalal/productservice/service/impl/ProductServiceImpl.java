package com.giteshdalal.productservice.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giteshdalal.productservice.model.generated.ProductModel;
import com.giteshdalal.productservice.repository.generated.ProductRepository;
import com.giteshdalal.productservice.resource.generated.ProductResource;
import com.giteshdalal.productservice.service.ProductService;
import com.querydsl.core.types.Predicate;

/**
 * @author gitesh
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Page<ProductResource> findProducts(Predicate predicate, Pageable pageable) {
		Page<ProductModel> page = repo.findAll(predicate, pageable);
		return page.map(model -> mapper.map(model, ProductResource.class));
	}

	@Override
	public ProductResource addProduct(ProductResource product) {
		ProductModel entity = mapper.map(product, ProductModel.class);
		return mapper.map(repo.save(entity), ProductResource.class);
	}

}
