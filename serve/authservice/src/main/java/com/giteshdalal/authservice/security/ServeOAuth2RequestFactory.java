package com.giteshdalal.authservice.security;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.DefaultSecurityContextAccessor;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.SecurityContextAccessor;
import org.springframework.security.oauth2.provider.TokenRequest;

/**
 * @author gitesh
 */
public class ServeOAuth2RequestFactory implements OAuth2RequestFactory {

	public final static String ROLE_REGEX = "(ROLE_#)|(ROLE_[A-Z_-]+[:]#)";

	public final static String CLIENT_ADMIN = "CLIENT_ADMIN";

	private final ClientDetailsService clientDetailsService;

	private SecurityContextAccessor securityContextAccessor = new DefaultSecurityContextAccessor();

	private boolean checkUserScopes = false;

	public ServeOAuth2RequestFactory(ClientDetailsService clientDetailsService) {
		this.clientDetailsService = clientDetailsService;
	}

	/**
	 * @param securityContextAccessor
	 * 		the security context accessor to set
	 */
	public void setSecurityContextAccessor(SecurityContextAccessor securityContextAccessor) {
		this.securityContextAccessor = securityContextAccessor;
	}

	/**
	 * Flag to indicate that scopes should be interpreted as valid authorities. No scopes will be granted to a user unless they are
	 * permitted as a granted authority to that user.
	 *
	 * @param checkUserScopes
	 * 		the checkUserScopes to set (default false)
	 */
	public void setCheckUserScopes(boolean checkUserScopes) {
		this.checkUserScopes = checkUserScopes;
	}

	public AuthorizationRequest createAuthorizationRequest(Map<String, String> authorizationParameters) {

		String clientId = authorizationParameters.get(OAuth2Utils.CLIENT_ID);
		String state = authorizationParameters.get(OAuth2Utils.STATE);
		String redirectUri = authorizationParameters.get(OAuth2Utils.REDIRECT_URI);
		Set<String> responseTypes = OAuth2Utils.parseParameterList(authorizationParameters.get(OAuth2Utils.RESPONSE_TYPE));

		Set<String> scopes = extractScopes(authorizationParameters, clientId);

		AuthorizationRequest request = new AuthorizationRequest(authorizationParameters, Collections.<String, String>emptyMap(),
				clientId, scopes, null, null, false, state, redirectUri, responseTypes);

		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		request.setResourceIdsAndAuthoritiesFromClientDetails(clientDetails);

		return request;

	}

	public OAuth2Request createOAuth2Request(AuthorizationRequest request) {
		return request.createOAuth2Request();
	}

	public TokenRequest createTokenRequest(Map<String, String> requestParameters, ClientDetails authenticatedClient) {

		String clientId = requestParameters.get(OAuth2Utils.CLIENT_ID);
		if (clientId == null) {
			// if the clientId wasn't passed in in the map, we add pull it from the
			// authenticated client object
			clientId = authenticatedClient.getClientId();
		} else {
			// otherwise, make sure that they match
			if (!clientId.equals(authenticatedClient.getClientId())) {
				throw new InvalidClientException("Given client ID does not match authenticated client");
			}
		}
		String grantType = requestParameters.get(OAuth2Utils.GRANT_TYPE);

		Set<String> scopes = extractScopes(requestParameters, clientId);
		TokenRequest tokenRequest = new TokenRequest(requestParameters, clientId, scopes, grantType);

		return tokenRequest;
	}

	public TokenRequest createTokenRequest(AuthorizationRequest authorizationRequest, String grantType) {
		TokenRequest tokenRequest = new TokenRequest(authorizationRequest.getRequestParameters(),
				authorizationRequest.getClientId(), authorizationRequest.getScope(), grantType);
		return tokenRequest;
	}

	public OAuth2Request createOAuth2Request(ClientDetails client, TokenRequest tokenRequest) {
		return tokenRequest.createOAuth2Request(client);
	}

	private Set<String> extractScopes(Map<String, String> requestParameters, String clientId) {
		Set<String> scopes = OAuth2Utils.parseParameterList(requestParameters.get(OAuth2Utils.SCOPE));
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

		if ((scopes == null || scopes.isEmpty())) {
			scopes = clientDetails.getScope();
		}

		if (checkUserScopes) {
			scopes = checkUserScopes(scopes, clientDetails);
		}
		return scopes;
	}

	private Set<String> checkUserScopes(Set<String> scopes, ClientDetails clientDetails) {
		if (!securityContextAccessor.isUser()) {
			return scopes;
		}
		Set<String> result = new LinkedHashSet<String>();
		Set<String> authorities = AuthorityUtils.authorityListToSet(securityContextAccessor.getAuthorities());
		if (scopes.contains(CLIENT_ADMIN)) {
			return authorities;
		}
		scopes.forEach(s -> {
			result.addAll(authorities.stream().filter(a -> a.matches(ROLE_REGEX.replaceAll("#", s.toUpperCase())))
					.collect(Collectors.toList()));
		});
		return result;
	}

}
