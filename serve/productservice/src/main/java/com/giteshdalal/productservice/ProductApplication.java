package com.giteshdalal.productservice;

import com.giteshdalal.productservice.controller.AbstractServeController;
import com.giteshdalal.productservice.controller.ProductsController;
import com.giteshdalal.productservice.model.generated.ProductModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.jackson.JsonNodeValueReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author gitesh
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableSpringDataWebSupport
public class ProductApplication {

	private static final String ANNOTATIONS = "annotations";

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapperInstance() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().addValueReader(new JsonNodeValueReader());
		return modelMapper;
	}
}
