package com.giteshdalal.authservice;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author gitesh
 */
@Controller
@RequestMapping("/api")
public class SchemaController {

	@Autowired
	private JsonSchemaGenerator schemaGenerator;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/schema", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	Map<String, JsonSchema> getMetaInfo() throws JsonMappingException {
		Map<String, JsonSchema> schemas = new HashMap<>();
		return schemas;
	}
}
