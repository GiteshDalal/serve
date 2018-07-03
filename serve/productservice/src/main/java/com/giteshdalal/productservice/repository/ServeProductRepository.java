package com.giteshdalal.productservice.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.giteshdalal.productservice.model.generated.ProductModel;
import com.giteshdalal.productservice.repository.generated.ProductRepository;

/**
 * @author gitesh
 *
 */
@Repository
public interface ServeProductRepository extends ProductRepository {

	List<ProductModel> findOptionalByCode(String code);
}
