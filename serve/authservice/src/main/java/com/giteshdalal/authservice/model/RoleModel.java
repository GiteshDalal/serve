package com.giteshdalal.authservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

/**
 * @author gitesh
 */
@Data
@Entity(name = "Role")
public class RoleModel {

	@Id
	private String name;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<UserModel> users;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "ROLE_PRIVILEGES", joinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"),
			inverseJoinColumns = @JoinColumn(name = "privilege_name", referencedColumnName = "name"))
	private List<PrivilegeModel> privileges;

	public void addPrivilege(PrivilegeModel privilege) {
		if (getPrivileges() == null) {
			setPrivileges(new ArrayList<>());
		}
		getPrivileges().add(privilege);
	}

	public void addUser(UserModel user) {
		if (getUsers() == null) {
			setUsers(new ArrayList<>());
		}
		getUsers().add(user);
	}

}
