package com.giteshdalal.authservice.security;

import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenRequest;

/**
 * @author gitesh
 */
public class ServeOAuth2RequestValidator implements OAuth2RequestValidator {

	public void validateScope(AuthorizationRequest authorizationRequest, ClientDetails client) throws InvalidScopeException {
		validateScope(authorizationRequest.getScope(), client.getScope());
	}

	public void validateScope(TokenRequest tokenRequest, ClientDetails client) throws InvalidScopeException {
		validateScope(tokenRequest.getScope(), client.getScope());
	}

	private void validateScope(Set<String> requestScopes, Set<String> clientScopes) {

		if (CollectionUtils.isNotEmpty(clientScopes)) {
			if (clientScopes.contains(ServeOAuth2RequestFactory.CLIENT_ADMIN)) {
				return;
			}
			requestScopes.forEach(s -> {
				if (!clientScopes.stream().anyMatch(cs -> s
						.matches(ServeOAuth2RequestFactory.ROLE_REGEX.replaceAll("#", cs.toUpperCase())))) {
					throw new InvalidScopeException(
							"Either the client or the user is not allowed the requested scopes");
				}
			});
		} else if (CollectionUtils.isEmpty(requestScopes)) {
			throw new InvalidScopeException("Client did not request for any scopes");
		}
	}

}