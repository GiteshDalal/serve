package com.giteshdalal.authservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.security.auth.login.AccountException;

import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.resource.PrivilegeResource;
import com.giteshdalal.authservice.resource.RoleResource;
import com.giteshdalal.authservice.resource.UserResource;
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
		Map<String, String> info = new HashMap<>();
		info.put("ROLE_TRUSTED_CLIENT","This is an important role for client");
		info.put("client_admin","This client is always created on server startup");
		client.setAdditionalInformation(info);
		try {
			clientService.register(client);
		} catch (ClientRegistrationException e) {
			e.printStackTrace();
		}
	}

	private void createAdminUser(UserService userService, AuthorityService authorityService) {
		List<PrivilegeResource> privileges = new ArrayList<>();

		PrivilegeResource readPrivilege = new PrivilegeResource();
		readPrivilege.setName("READ");
		PrivilegeResource writePrivilege = new PrivilegeResource();
		writePrivilege.setName("WRITE");
		PrivilegeResource publishPrivilege = new PrivilegeResource();
		publishPrivilege.setName("PUBLISH");
		PrivilegeResource deletePrivilege = new PrivilegeResource();
		deletePrivilege.setName("DELETE");

		privileges.add(authorityService.savePrivilege(readPrivilege));
		privileges.add(authorityService.savePrivilege(writePrivilege));
		privileges.add(authorityService.savePrivilege(publishPrivilege));
		privileges.add(authorityService.savePrivilege(deletePrivilege));

		RoleResource adminRole = new RoleResource();
		adminRole.setName("ROLE_ADMIN");
		adminRole = authorityService.saveRole(adminRole);

		RoleResource employeeRole = new RoleResource();
		employeeRole.setName("ROLE_EMPLOYEE");
		employeeRole.setPrivileges(privileges);
		employeeRole = authorityService.saveRole(employeeRole);

		RoleResource userRole = new RoleResource();
		userRole.setName("ROLE_USER");
		userRole.setPrivileges(privileges);
		userRole = authorityService.saveRole(userRole);

		UserResource acct = new UserResource();
		List<RoleResource> userRoles = new ArrayList<>();
		userRoles.add(adminRole);
		userRoles.add(employeeRole);
		userRoles.add(userRole);
		acct.setRoles(userRoles);
		acct.setUsername(ADMIN);
		acct.setPassword(adminPassword);
		acct.setFirstName(ADMIN);
		acct.setLastName("");
		acct.setEmail(ADMIN_EMAIL);
		acct.setEnabled(true);
		acct.setCredentialsNonExpired(true);
		acct.setAccountNonLocked(true);
		acct.setAccountNonExpired(true);

		try {
			userService.saveUser(acct);
		} catch (AccountException e) {
			e.printStackTrace();
		}
	}
}
