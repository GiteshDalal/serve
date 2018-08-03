package com.giteshdalal.productservice;

import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslPredicate;

import java.lang.annotation.Annotation;

public class ServeQuerydslPredicate implements QuerydslPredicate {

	private Class<?> root;

	public ServeQuerydslPredicate(Class<?> root) {
		this.root = root;
	}

	@Override
	public Class<?> root() {
		return root;
	}

	@Override
	public Class<? extends QuerydslBinderCustomizer> bindings() {
		return QuerydslBinderCustomizer.class;
	}

	@Override
	public Class<? extends Annotation> annotationType() {
		return getClass();
	}
}
