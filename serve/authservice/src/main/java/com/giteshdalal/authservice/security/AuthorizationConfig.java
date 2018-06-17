package com.giteshdalal.authservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author gitesh
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.oauth2.keystore.file:keystore.jks}")
	private String keystore;

	@Value("${security.oauth2.keystore.key:serve_key}")
	private String key;

	@Value("${security.oauth2.keystore.password:password}")
	private String keystorePass;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("clientServiceImpl")
	public ClientDetailsService clientDetailsService;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(this.authenticationManager) // AuthenticationManager
				.tokenServices(tokenServices()) // DefaultTokenServices
				.tokenStore(tokenStore()) // JwtTokenStore
				.accessTokenConverter(accessTokenConverter()); // JwtAccessTokenConverter
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		ClassPathResource keyStoreResource = new ClassPathResource(keystore);
		char[] secret = keystorePass.toCharArray();
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyStoreResource, secret);
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(key));
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setTokenEnhancer(accessTokenConverter());
		return defaultTokenServices;
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
				// we're allowing access to the token only for clients with
				// 'ROLE_TRUSTED_CLIENT' authority
				.tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
				.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}
}
