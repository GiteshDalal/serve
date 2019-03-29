package com.giteshdalal.authservice.resource;

import java.util.List;

import lombok.Data;

/**
 * @author gitesh
 */
@Data
public class PrivilegeResource {
	private Long uid;
	private String name;
	private List<RoleResource> roles;
}
