package com.giteshdalal.authservice.resource;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;

/**
 * @author gitesh
 */
@Data
public class ClientResource {
	private Long uid;
	private String clientId, clientSecret, email;
	private Integer accessTokenValiditySeconds, refreshTokenValiditySeconds;
	private boolean seceretRequired, scopeRequired;
	private List<String> clientRoles, resourceIds, scopes, autoApprovedScopes, authorizedGrantTypes, registeredRedirectUri;
	private Map<String, String> additionalInformation;
}
