package ${service.group.toLowerCase()}.${service.name.toLowerCase()}service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * @author Serve Engine
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableSpringDataWebSupport
public class ${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}Application {

	public static void main(String[] args) {
		SpringApplication.run(${service.name.substring(0,1).toUpperCase()}${service.name.substring(1)}Application.class, args);
	}

	@Bean
	public ModelMapper modelMapperInstance() {
		return new ModelMapper();
	}
}
