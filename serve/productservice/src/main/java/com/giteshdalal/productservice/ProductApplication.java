package com.giteshdalal.productservice;

import com.giteshdalal.productservice.controller.AbstractServeController;
import com.giteshdalal.productservice.controller.ProductsController;
import com.giteshdalal.productservice.model.generated.ProductModel;
import org.modelmapper.ModelMapper;
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
		registerControllerForModel(ProductModel.class, ProductsController.class);
		SpringApplication.run(ProductApplication.class, args);
	}

	private static void registerControllerForModel(Class<?> modelClass, Class<? extends AbstractServeController> controllerClass) {
		ServeQuerydslPredicate querydslPredicate = new ServeQuerydslPredicate(modelClass);
		alterAnnotationValue(controllerClass, QuerydslPredicate.class, querydslPredicate);
	}

	private static void alterAnnotationValue(Class<?> targetClass, Class<? extends Annotation> targetAnnotation, Annotation targetValue) {
		try {
			Field annotations = Class.class.getDeclaredField(ANNOTATIONS);
			annotations.setAccessible(true);

			Map<Class<? extends Annotation>, Annotation> map = (Map<Class<? extends Annotation>, Annotation>) annotations.get(targetClass);
			map.put(targetAnnotation, targetValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Bean
	public ModelMapper modelMapperInstance() {
		return new ModelMapper();
	}
}
