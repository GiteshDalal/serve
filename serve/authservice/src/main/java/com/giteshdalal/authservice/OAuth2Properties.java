package com.giteshdalal.authservice;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@Validated
@ConfigurationProperties(prefix = "serve.oauth2")
public class OAuth2Properties {

	@Valid
	@Getter
	private final Keystore keystore = new Keystore();

	@Valid
	@Getter
	private final TokenValidity validity = new TokenValidity();

	/**
	 * Comma, seperated list of resourceids which can be accessed using the
	 * generated token. NOTE: Only used to generate testing user accounts.
	 */
	@Getter
	@Setter
	@NotEmpty
	private Set<String> resourceIds;

	@Data
	public static class Keystore {

		/**
		 * Name of key. Can't be empty
		 */
		@NotEmpty
		private String key;

		/**
		 * Name of keystore file. Can't be empty
		 */
		@NotEmpty
		private String file;

		/**
		 * password for keystore key. Can't be empty
		 */
		@NotEmpty
		private String password;

	}

	@Data
	public static class TokenValidity {

		/**
		 * Access Token Validity in Seconds. Must be greater then 60 seconds
		 */
		@NotNull
		@Min(60)
		private int accessToken;

		/**
		 * Refresh Token Validity in Seconds. Must be greater then 180 seconds
		 */
		@NotNull
		@Min(180)
		private int refreshToken;

	}
}
