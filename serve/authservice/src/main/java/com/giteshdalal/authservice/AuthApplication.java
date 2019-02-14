package com.giteshdalal.authservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

/**
 * @author gitesh
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class AuthApplication {

	private final OAuth2Properties properties;

	public AuthApplication(OAuth2Properties properties) {
		this.properties = properties;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	public JsonSchemaGenerator schemaGenerator() {
		return new JsonSchemaGenerator(new ObjectMapper());
	}

	@Bean
	public SCryptPasswordEncoder passwordEncoder() {
		return new SCryptPasswordEncoder();
	}
}
