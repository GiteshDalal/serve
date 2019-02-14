package com.giteshdalal.authservice.resource;

import java.util.List;

import lombok.Data;

/**
 * @author gitesh
 */
@Data
public class UserResource {
	private Long uid;
	private String password, firstName, lastName, username, email;
	private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;
	private List<RoleResource> roles;
}
