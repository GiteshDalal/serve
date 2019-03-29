package com.giteshdalal.authservice.resource;

import java.util.List;

import lombok.Data;

/**
 * @author gitesh
 */
@Data
public class RoleResource {
	private Long uid;
	private String name;
	private List<PrivilegeResource> privileges;
	private List<UserResource> users;
}
