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

	@NotEmpty
	private String resourceId;

	@NotEmpty
	private String authServiceId;

	@NotEmpty
	private String clientId;

	@NotEmpty
	private String clientSecret;
}
