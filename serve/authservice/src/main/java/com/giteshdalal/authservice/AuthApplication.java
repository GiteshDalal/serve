package com.giteshdalal.authservice;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.security.auth.login.AccountException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.service.AuthorityService;
import com.giteshdalal.authservice.service.ClientService;
import com.giteshdalal.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * @author gitesh
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class AuthApplication {

	public static final String ADMIN_EMAIL = "admin@giteshdalal.co.nz";
	public static final String ADMIN = "admin";
	public static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";
	private static final List<String> CLIENT_GRANTS = Arrays
			.asList("authorization_code", "client_credentials", "refresh_token", "password");
	private static final List<String> CLIENT_ADMIN_SCOPE = Arrays.asList("CLIENT_ADMIN");
	private static final List<String> CLIENT_REDIRECT_URIS = Collections.singletonList("http://localhost:1234/api");

	private final OAuth2Properties properties;

	private final String adminPassword;

	private final String adminClientPassword;

	public AuthApplication(OAuth2Properties properties, @Value("${serve.default.admin.secret:serve}") String adminPassword,
			@Value("${serve.default.admin.client.secret:serveclient}") String adminClientPassword) {
		this.properties = properties;
		this.adminPassword = adminPassword;
		this.adminClientPassword = adminClientPassword;
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserService userService, ClientService clientService, AuthorityService authorityService) {
		return (evt) -> {
			// admin user
			createAdminUser(userService, authorityService);

			// admin client
			createAdminClient(clientService);
		};
	}

	private void createAdminClient(ClientService clientService) {
		ClientModel client = new ClientModel();
		client.setClientId("client_admin");
		client.setResourceIds(this.properties.getResourceIds());
		client.setAccessTokenValiditySeconds(this.properties.getValidity().getAccessToken());
		client.setRefreshTokenValiditySeconds(this.properties.getValidity().getRefreshToken());
		client.grantAuthority(ROLE_TRUSTED_CLIENT);
		client.setClientSecret(this.adminClientPassword);
		client.setAuthorizedGrantTypes(new HashSet<>(CLIENT_GRANTS));
		client.setRegisteredRedirectUri(new HashSet<>(CLIENT_REDIRECT_URIS));
		client.setScopes(new HashSet<>(CLIENT_ADMIN_SCOPE));
		client.setSeceretRequired(true);
		client.setEmail(ADMIN_EMAIL);
		try {
			clientService.register(client);
		} catch (ClientRegistrationException e) {
			e.printStackTrace();
		}
	}

	private void createAdminUser(UserService userService, AuthorityService authorityService) {
		RoleModel adminRole = new RoleModel();
		adminRole.setName("ROLE_ADMIN");

		PrivilegeModel readPrivilege = new PrivilegeModel();
		readPrivilege.setName("READ");
		PrivilegeModel writePrivilege = new PrivilegeModel();
		writePrivilege.setName("WRITE");
		PrivilegeModel publishPrivilege = new PrivilegeModel();
		publishPrivilege.setName("PUBLISH");
		PrivilegeModel deletePrivilege = new PrivilegeModel();
		deletePrivilege.setName("DELETE");

		authorityService.savePrilivege(readPrivilege);
		authorityService.savePrilivege(writePrivilege);
		authorityService.savePrilivege(publishPrivilege);
		authorityService.savePrilivege(deletePrivilege);

		RoleModel userRole = new RoleModel();
		userRole.setName("ROLE_USER");
		userRole.addPrivilege(readPrivilege);
		userRole.addPrivilege(writePrivilege);
		userRole.addPrivilege(publishPrivilege);
		userRole.addPrivilege(deletePrivilege);

		authorityService.saveRole(userRole);

		authorityService.saveRole(adminRole);
		UserModel acct = new UserModel();
		acct.setUsername(ADMIN);
		acct.grantAuthority(adminRole);
		acct.grantAuthority(userRole);
		acct.setPassword(adminPassword);
		acct.setFirstName(ADMIN);
		acct.setLastName("");
		acct.setEmail(ADMIN_EMAIL);
		try {
			userService.register(acct);
		} catch (AccountException e) {
			e.printStackTrace();
		}
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
