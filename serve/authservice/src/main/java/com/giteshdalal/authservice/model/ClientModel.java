package com.giteshdalal.authservice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * @author gitesh
 */
@Entity(name = "Client")
public class ClientModel implements ClientDetails {

	private static final long serialVersionUID = 101L;
	public static final String NAME = "Client";

	@Id
	@TableGenerator(name = "clientSeqGen", table = "ID_GEN", pkColumnValue = "CLIENT_ID", initialValue = 100)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "clientSeqGen")
	@Getter
	@Setter
	private Long uid;

	@Setter
	@Column(unique = true, nullable = false)
	private String clientId;

	@Setter
	private String clientSecret;

	@Setter
	private Integer accessTokenValiditySeconds, refreshTokenValiditySeconds;

	@Setter
	private boolean seceretRequired, scopeRequired;

	@Getter
	@Setter
	@Column(unique = true, nullable = false)
	private String email;

	@Getter
	@Setter
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> clientRoles;

	@Setter
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> resourceIds, scopes, autoApprovedScopes, authorizedGrantTypes, registeredRedirectUri;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@Setter
	@CollectionTable(name = "additionalInformation", joinColumns = @JoinColumn(name = "additionalInformation_id"))
	private Map<String, String> additionalInformation;

	@Override
	public String getClientId() {
		return this.clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return this.resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		return this.seceretRequired;
	}

	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}

	@Override
	public boolean isScoped() {
		return this.scopeRequired;
	}

	@Override
	public Set<String> getScope() {
		return this.scopes;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return this.authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return this.registeredRedirectUri;
	}

	public void grantAuthority(String authority) {
		if (this.clientRoles == null) {
			this.clientRoles = new HashSet<>();
		}
		clientRoles.add(authority);
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		clientRoles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
		return authorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValiditySeconds;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return autoApprovedScopes != null ? autoApprovedScopes.contains(scope) : false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return new HashMap<>(this.additionalInformation);
	}

}
