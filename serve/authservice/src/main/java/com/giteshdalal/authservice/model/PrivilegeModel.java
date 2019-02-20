package com.giteshdalal.authservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

/**
 * @author gitesh
 */
@Data
@Entity(name = "Privilege")
public class PrivilegeModel {

	@Id
	private String name;

	@ManyToMany(mappedBy = "privileges", fetch = FetchType.LAZY)
	private List<RoleModel> roles;

	public void addRole(RoleModel role) {
		if (getRoles() == null) {
			setRoles(new ArrayList<>());
		}
		getRoles().add(role);
	}

}
