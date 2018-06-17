package com.giteshdalal.productservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author gitesh
 *
 */
/*@Configuration
@EnableResourceServer*/
public class ResourceSecurityConfig extends ResourceServerConfigurerAdapter {
/*	@Value("${security.oauth2.resource.id:oauth_id}")
	private String resourceId;

	// The DefaultTokenServices bean provided at the AuthorizationConfig
	@Autowired
	private DefaultTokenServices tokenServices;

	// The TokenStore bean provided at the AuthorizationConfig
	@Autowired
	private TokenStore tokenStore;

	// To allow the rResourceServerConfigurerAdapter to understand the token,
	// it must share the same characteristics with
	// AuthorizationServerConfigurerAdapter.
	// So, we must wire it up the beans in the ResourceServerSecurityConfigurer.
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(resourceId).tokenServices(tokenServices).tokenStore(tokenStore);
	}*/
}