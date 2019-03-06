package com.giteshdalal.authservice.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import javax.security.auth.login.AccountException;

import com.giteshdalal.authservice.OAuth2Properties;
import com.giteshdalal.authservice.model.ClientModel;
import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.model.UserModel;
import com.giteshdalal.authservice.service.AuthorityService;
import com.giteshdalal.authservice.service.ClientService;
import com.giteshdalal.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author gitesh
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class AuthApplicationDataTest {

	private static final String TEST_USERS = "user,admin,john,robert,ana";
	private static final String DEFAULT_PASSWORD = "serve";
	private static final String CLIENT_DEFAULT_PASSWORD = "serveclient";

	private static final String ROLE_ADMIN = "ROLE_ADMIN";
	private static final String ROLE_USER = "ROLE_USER";
	private static final String ROLE_TRUSTED_CLIENT = "ROLE_TRUSTED_CLIENT";
	private static final List<String> CLIENT_SCOPES = Arrays.asList("publish", "read", "write");
	private static final List<String> CLIENT_GRANTS = Arrays.asList("authorization_code", "client_credentials", "refresh_token");
	private static final List<String> CLIENT_REDIRECT_URIS = Collections.singletonList("http://localhost:1234/api");
	private static final String CLIENT_EMAIL = "client.%s@giteshdalal.com";
	private static final String USER_EMAIL = "%s@giteshdalal.com";
	private static final String HR = "********************************************************************";

	@Autowired
	UserService userService;

	@Autowired
	ClientService clientService;

	@Autowired
	AuthorityService authorityService;

	@Autowired
	OAuth2Properties properties;

	@Test
	public void initTestUserAndClientData() {
		List<UserModel> users = new ArrayList();
		List<ClientModel> clients = new ArrayList();

		Arrays.asList(TEST_USERS.split(",")).forEach(username -> {
			users.add(createNewUser(username));
			clients.add(createNewClient(username));
		});

		// client_new have no scopes assigned
		clients.add(createNewClient("new"));

		log.warn(HR);
		users.forEach(u -> log.warn(String.format(">>>>>>   User/Secret: %s/%s", u.getUsername(), DEFAULT_PASSWORD)));
		clients.forEach(c -> log.warn(String.format(">>>>>> Client/Secret: %s/%s", c.getClientId(), CLIENT_DEFAULT_PASSWORD)));
		log.warn(HR);
	}

	private ClientModel createNewClient(String username) {
		ClientModel client = new ClientModel();
		client.setClientId("client_" + username);
		client.setResourceIds(this.properties.getResourceIds());
		client.setAccessTokenValiditySeconds(this.properties.getValidity().getAccessToken());
		client.setRefreshTokenValiditySeconds(this.properties.getValidity().getRefreshToken());
		client.grantAuthority(ROLE_TRUSTED_CLIENT);
		client.setScopes(new HashSet<>(CLIENT_SCOPES));
		client.setClientSecret(CLIENT_DEFAULT_PASSWORD);
		client.setAuthorizedGrantTypes(new HashSet<>(CLIENT_GRANTS));
		client.setRegisteredRedirectUri(new HashSet<>(CLIENT_REDIRECT_URIS));

		// For client_new
		if (client.getClientId().equals("client_new")) {
			client.getAuthorizedGrantTypes().add("password");
			client.setScopes(null);
		}
		client.setSeceretRequired(true);
		client.setEmail(String.format(CLIENT_EMAIL, username));
		try {
			clientService.register(client);
		} catch (ClientRegistrationException e) {
			e.printStackTrace();
		}
		return client;
	}

	private UserModel createNewUser(String username) {
		RoleModel userRole = getNewUserRoleModel();

		UserModel acct = new UserModel();
		acct.setUsername(username);
		acct.setPassword(DEFAULT_PASSWORD);
		acct.setFirstName(username);
		acct.setLastName("testing");
		acct.grantAuthority(userRole);
		acct.setEmail(String.format(USER_EMAIL, username));
		try {
			userService.register(acct);
		} catch (AccountException e) {
			e.printStackTrace();
		}
		return acct;
	}

	private RoleModel getNewUserRoleModel() {
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
		userRole.setName(ROLE_USER);
		userRole.addPrivilege(readPrivilege);
		userRole.addPrivilege(writePrivilege);
		userRole.addPrivilege(publishPrivilege);
		userRole.addPrivilege(deletePrivilege);

		authorityService.saveRole(userRole);
		return userRole;
	}
}
