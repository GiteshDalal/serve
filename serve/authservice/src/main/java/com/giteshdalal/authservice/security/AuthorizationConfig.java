package com.giteshdalal.authservice.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.giteshdalal.authservice.OAuth2Properties;

/**
 * @author gitesh
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	private final OAuth2Properties properties;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("clientServiceImpl")
	public ClientDetailsService clientDetailsService;

	@Autowired
	public AuthorizationConfig(OAuth2Properties properties) {
		this.properties = properties;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(this.authenticationManager) // AuthenticationManager
				.tokenServices(tokenServices()) // DefaultTokenServices
				.tokenStore(tokenStore()) // JwtTokenStore
				.requestFactory(requestFactory()) // OAuth2RequestFactory
				.requestValidator(requestValidator())// OAuth2RequestValidator
				.accessTokenConverter(accessTokenConverter()); // AccessTokenConverter
	}

	@Bean
	public OAuth2RequestFactory requestFactory() {
		ServeOAuth2RequestFactory requestFactory = new ServeOAuth2RequestFactory(clientDetailsService);
		requestFactory.setCheckUserScopes(true);
		return requestFactory;
	}

	@Bean
	public OAuth2RequestValidator requestValidator() {
		return new ServeOAuth2RequestValidator();
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new AuthorizationTokenEnhancer();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		ClassPathResource keyStoreResource = new ClassPathResource(properties.getKeystore().getFile());
		char[] secret = properties.getKeystore().getPassword().toCharArray();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyStoreResource, secret);
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(properties.getKeystore().getKey()));
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Primary
	public AuthorizationServerTokenServices tokenServices() {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
		defaultTokenServices.setClientDetailsService(clientDetailsService);
		return defaultTokenServices;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
				// we're allowing access to the token only for clients with
				// 'ROLE_TRUSTED_CLIENT' authority
				.tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')").checkTokenAccess("permitAll()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}
}
