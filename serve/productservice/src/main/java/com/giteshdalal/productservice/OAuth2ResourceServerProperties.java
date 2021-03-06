package com.giteshdalal.productservice;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "serve.oauth2")
public class OAuth2ResourceServerProperties {

	/**
	 * Id to identify this resource server
	 */
	@NotEmpty
	private String resourceId;

	/**
	 * OAuth2 Server Service Id
	 */
	@NotEmpty
	private String authServerServiceId;

	/**
	 * Client Id to communicate with OAuth2 Server
	 */
	@NotEmpty
	private String clientId;

	/**
	 * Client Id secret to communicate with OAuth2 Server
	 */
	@NotEmpty
	private String clientSecret;
}
