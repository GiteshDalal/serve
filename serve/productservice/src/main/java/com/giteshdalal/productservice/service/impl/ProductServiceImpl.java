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

	@Override
	public Page<ProductResource> findProducts(Predicate predicate, Pageable pageable) {
		Page<ProductModel> page = repo.findAll(predicate, pageable);
		ModelMapper modelMapper = new ModelMapper();
		return page.map(model -> modelMapper.map(model, ProductResource.class));
	}

}
