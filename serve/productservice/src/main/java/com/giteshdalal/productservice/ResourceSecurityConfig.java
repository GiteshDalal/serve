package com.giteshdalal.productservice;

import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * @author gitesh
 *
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceSecurityConfig extends ResourceServerConfigurerAdapter {
	private final Log logger = LogFactory.getLog(getClass());

	@Value("${security.oauth2.resource-id}")
	private String resourceId;

	@Value("${security.oauth2.service-id}")
	private String authServiceId;

	@Value("${security.oauth2.client-id}")
	private String clientId;

	@Value("${security.oauth2.client-secret}")
	private String clientSecret;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		logger.info("\n security.oauth2.resource-id: " + resourceId);
		config.resourceId(resourceId).tokenServices(tokenServices());
	}

	@Bean
	@Primary
	public ResourceServerTokenServices tokenServices() {
		List<ServiceInstance> authServerInstances = discoveryClient.getInstances(authServiceId);
		ServiceInstance instance = authServerInstances.get(new Random().nextInt(authServerInstances.size()));
		RemoteAuthTokenServices tokenServices = new RemoteAuthTokenServices();
		tokenServices.setClientId(clientId);
		tokenServices.setClientSecret(clientSecret);
		logger.info("\n Auth Server Uri: " + instance.getUri());
		tokenServices.setCheckTokenEndpointUrl(instance.getUri() + "/oauth/check_token");
		return tokenServices;
	}
}