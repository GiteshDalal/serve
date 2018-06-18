package com.giteshdalal.authservice;

import java.util.Arrays;
import java.util.HashSet;

import javax.security.auth.login.AccountException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.service.ClientServiceImpl;
import com.giteshdalal.authservice.service.UserServiceImpl;

/**
 * @author gitesh
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {
	private final Log logger = LogFactory.getLog(getClass());

	@Value("${security.oauth2.validity.access_token:6000}")
	private int accessTokenValiditySeconds;

	@Value("${security.oauth2.validity.refresh_token:18000}")
	private int refreshTokenValiditySeconds;

	@Value("${security.oauth2.resource_id:oauth_id}")
	private String resourceId;

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	public SCryptPasswordEncoder passwordEncoder() {
		return new SCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner init(UserServiceImpl userService, ClientServiceImpl clientService) {
		return (evt) -> Arrays.asList("user,admin,john,robert,ana".split(",")).forEach(username -> {
			UserModel acct = new UserModel();
			acct.setUsername(username);
			if (username.equals("admin")) {
				acct.grantAuthority("ROLE_ADMIN");
			}
			acct.setPassword("password");
			acct.setFirstName(username);
			acct.setLastName("LastName");
			acct.grantAuthority("ROLE_USER");
			acct.setEmail(String.format("[%s]@giteshdalal.com", username));
			try {
				userService.register(acct);
			} catch (AccountException e) {
				e.printStackTrace();
			}

			ClientModel client = new ClientModel();
			client.setClientId("client_" + username);
			client.setResourceIds(new HashSet<>(Arrays.asList(resourceId)));
			client.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
			client.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
			client.grantAuthority("ROLE_TRUSTED_CLIENT");
			client.setScopes(new HashSet<>(Arrays.asList("oauth2")));
			client.setClientSecret("client_password");
			client.setAuthorizedGrantTypes(
					new HashSet<>(Arrays.asList("authorization_code", "client_credentials", "refresh_token")));
			client.setRegisteredRedirectUri(new HashSet<>(Arrays.asList("http://localhost:1234/api")));
			if (client.getClientId().equals("client_admin")) {
				client.getAuthorizedGrantTypes().add("password");
			}
			client.setSeceretRequired(true);
			client.setEmail(String.format("client.[%s]@giteshdalal.com", username));
			try {
				clientService.register(client);
			} catch (ClientRegistrationException e) {
				e.printStackTrace();
			}
			logger.info(String.format("User Credentials: %s/%s", acct.getUsername(), "password"));
			logger.info(String.format("Client Credentials: %s/%s", client.getClientId(), "client_password"));
		});
	}

}
