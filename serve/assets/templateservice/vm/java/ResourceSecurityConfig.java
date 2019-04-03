package ${service.group.toLowerCase()};

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;

/**
 * @author Serve Engine
 */
@Slf4j
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceSecurityConfig extends ResourceServerConfigurerAdapter {

	private final OAuth2ResourceServerProperties properties;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	public ResourceSecurityConfig(OAuth2ResourceServerProperties properties) {
		this.properties = properties;
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		log.info("\n security.oauth2.resource-id: " + this.properties.getResourceId());
		config.resourceId(this.properties.getResourceId()).tokenServices(tokenServices());
	}

	@Bean
	@Primary
	public ResourceServerTokenServices tokenServices() {
		List<ServiceInstance> authServerInstances = discoveryClient
				.getInstances(this.properties.getAuthServerServiceId());
		ServiceInstance instance = authServerInstances.get(new Random().nextInt(authServerInstances.size()));
		RemoteAuthTokenServices tokenServices = new RemoteAuthTokenServices();
		tokenServices.setClientId(this.properties.getClientId());
		tokenServices.setClientSecret(this.properties.getClientSecret());
		log.info("\n Auth Server Uri: " + instance.getUri());
		tokenServices.setCheckTokenEndpointUrl(instance.getUri() + "/oauth/check_token");
		return tokenServices;
	}
}
