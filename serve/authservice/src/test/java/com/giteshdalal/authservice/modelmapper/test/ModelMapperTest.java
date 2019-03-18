package com.giteshdalal.authservice.modelmapper.test;

import java.util.Collections;
import java.util.LinkedList;

import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.resource.PrivilegeResource;
import com.giteshdalal.authservice.resource.RoleResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.modelmapper.ModelMapper;

/**
 * @author gitesh
 */
@Slf4j
public class ModelMapperTest {

	@Test
	public void RoleResourceMappingTest() {
		ModelMapper modelMapper = new ModelMapper();
		RoleResource roleResource = new RoleResource();
		roleResource.setName("tester");
		PrivilegeResource privilegeResource = new PrivilegeResource();
		privilegeResource.setName("testing");
		roleResource.setPrivileges(new LinkedList<>());
		roleResource.getPrivileges().add(privilegeResource);
		RoleModel role = modelMapper.map(roleResource, RoleModel.class);
		log.info(role.toString());
	}
}
