package com.giteshdalal.authservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.giteshdalal.authservice.resource.ClientResource;
import com.giteshdalal.authservice.resource.PrivilegeResource;
import com.giteshdalal.authservice.resource.RoleResource;
import com.giteshdalal.authservice.resource.UserResource;

@RestController
@PreAuthorize("hasRole('USER:OAUTH')")
public class InfoEndpoint {

	@Autowired
	private JsonSchemaGenerator schemaGenerator;

	@GetMapping("/info")
	public Map<String, JsonSchema> getCount() throws JsonMappingException {
		Map<String, JsonSchema> schemas = new HashMap<>();
		schemas.put("/users", schemaGenerator.generateSchema(UserResource.class));
		schemas.put("/roles", schemaGenerator.generateSchema(RoleResource.class));
		schemas.put("/privileges", schemaGenerator.generateSchema(PrivilegeResource.class));
		schemas.put("/clients", schemaGenerator.generateSchema(ClientResource.class));
		return schemas;
	}

}
