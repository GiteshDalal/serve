package com.giteshdalal.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.giteshdalal.userservice.model.generated.ClientModel;
import com.giteshdalal.userservice.model.generated.PrivilegeModel;
import com.giteshdalal.userservice.model.generated.RoleModel;
import com.giteshdalal.userservice.model.generated.UserModel;
import com.giteshdalal.userservice.resource.generated.ClientResource;
import com.giteshdalal.userservice.resource.generated.PrivilegeResource;
import com.giteshdalal.userservice.resource.generated.RoleResource;
import com.giteshdalal.userservice.resource.generated.UserResource;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
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
		TypeMap<ClientModel, ClientResource> clientTypeMap = mapper.createTypeMap(ClientModel.class, ClientResource.class);
		clientTypeMap.addMappings(m -> m.skip(ClientResource::setClientSecret));
		TypeMap<ClientResource, ClientModel> clientModelTypeMap = mapper.createTypeMap(ClientResource.class, ClientModel.class);
		clientModelTypeMap.addMappings(m -> m.skip(ClientModel::setClientSecret));

		TypeMap<UserModel, UserResource> userTypeMap = mapper.createTypeMap(UserModel.class, UserResource.class);
		userTypeMap.addMappings(m -> m.skip(UserResource::setPassword));
		TypeMap<UserResource, UserModel> userModelTypeMap = mapper.createTypeMap(UserResource.class, UserModel.class);
		userModelTypeMap.addMappings(m -> m.skip(UserModel::setPassword));

		TypeMap<RoleModel, RoleResource> roleTypeMap = mapper.createTypeMap(RoleModel.class, RoleResource.class);
		roleTypeMap.addMappings(m -> m.skip(RoleResource::setUsers));

		TypeMap<PrivilegeModel, PrivilegeResource> privilegeTypeMap = mapper
				.createTypeMap(PrivilegeModel.class, PrivilegeResource.class);
		privilegeTypeMap.addMappings(m -> m.skip(PrivilegeResource::setRoles));
	}
}
