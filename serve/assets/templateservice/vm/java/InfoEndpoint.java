package ${service.group.toLowerCase()}.${service.name.toLowerCase()}service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

/**
 * @author Serve Engine
 * 
 */
@RestController
@PreAuthorize("hasRole('USER:OAUTH')")
public class InfoEndpoint {

	@Autowired
	private JsonSchemaGenerator schemaGenerator;

	@GetMapping("/info")
	public Map<String, JsonSchema> getCount() throws JsonMappingException {
		Map<String, JsonSchema> schemas = new HashMap<>();
		// TODO: Needs to be implemented for integration with admin applications
		// schemas.put("/items", schemaGenerator.generateSchema(ItemResource.class));
		return schemas;
	}

}
