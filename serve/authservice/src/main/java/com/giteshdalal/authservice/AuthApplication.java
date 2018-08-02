package com.giteshdalal.authservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.service.AuthorityService;
import com.giteshdalal.authservice.service.impl.ClientServiceImpl;
import com.giteshdalal.authservice.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import javax.security.auth.login.AccountException;
import java.util.Arrays;
import java.util.HashSet;

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

	@Bean
	CommandLineRunner init(UserServiceImpl userService, ClientServiceImpl clientService, AuthorityService authorityService) {
		return (evt) -> Arrays.asList("user,admin,john,robert,ana".split(",")).forEach(username -> {
			PrivilegeModel publishPrivilege = new PrivilegeModel();
			publishPrivilege.setName("PUBLISH");

			authorityService.savePrilivege(publishPrivilege);

			RoleModel userRole = new RoleModel();
			userRole.setName("ROLE_USER");
			userRole.addPrivilege(publishPrivilege);

			authorityService.saveRole(userRole);

			RoleModel adminRole = new RoleModel();
			adminRole.setName("ROLE_ADMIN");

			authorityService.saveRole(adminRole);

			UserModel acct = new UserModel();
			acct.setUsername(username);
			if (username.equals("admin")) {
				acct.grantAuthority(adminRole);
			}
			acct.setPassword("password");
			acct.setFirstName(username);
			acct.setLastName("LastName");
			acct.grantAuthority(userRole);
			acct.setEmail(String.format("[%s]@giteshdalal.com", username));
			try {
				userService.register(acct);
			} catch (AccountException e) {
				e.printStackTrace();
			}

			ClientModel client = new ClientModel();
			client.setClientId("client_" + username);
			client.setResourceIds(this.properties.getResourceIds());
			client.setAccessTokenValiditySeconds(this.properties.getValidity().getAccessToken());
			client.setRefreshTokenValiditySeconds(this.properties.getValidity().getRefreshToken());
			client.grantAuthority("ROLE_TRUSTED_CLIENT");
			client.setScopes(new HashSet<>(Arrays.asList("publish", "read", "write")));
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
			log.info(String.format("User Credentials: %s/%s", acct.getUsername(), "password"));
			log.info(String.format("Client Credentials: %s/%s", client.getClientId(), "client_password"));
		});
	}

}
