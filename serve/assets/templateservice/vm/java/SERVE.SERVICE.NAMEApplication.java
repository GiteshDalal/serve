package ${service.group.toLowerCase()};

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import org.modelmapper.ModelMapper;
import org.modelmapper.jackson.JsonNodeValueReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * @author Serve Engine
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
	public JsonSchemaGenerator schemaGenerator() {
		return new JsonSchemaGenerator(new ObjectMapper());
	}

	@Bean
	public ModelMapper modelMapperInstance() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().addValueReader(new JsonNodeValueReader());
		configTypeMaps(modelMapper);
		return modelMapper;
	}

	/**
	 * This method is used to configure type maps for mapper.
	 *
	 * @param mapper
	 */
	private void configTypeMaps(ModelMapper mapper) {
		// TODO: Configure type maps over here if needed
	}
}
