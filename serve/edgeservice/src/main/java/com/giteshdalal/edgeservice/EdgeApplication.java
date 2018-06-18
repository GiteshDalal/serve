package com.giteshdalal.edgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.giteshdalal.edgeservice.filter.DefaultFilter;

@EnableZuulProxy
@SpringBootApplication
public class EdgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeApplication.class, args);
	}

	@Bean
	public DefaultFilter defaultFilter() {
		return new DefaultFilter();
	}
}
