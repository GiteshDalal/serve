package com.giteshdalal.authservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

import lombok.Data;

/**
 * @author gitesh
 */
@Data
@Entity(name = "Role")
public class RoleModel {

	private static final long serialVersionUID = 101L;
	public static final String NAME = "User";

	@Id
	@TableGenerator(name = "roleSeqGen", table = "ID_GEN", pkColumnValue = "ROLE_ID", initialValue = 10)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "roleSeqGen")
	private Long uid;

	@Column(unique = true, nullable = false)
	private String name;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private List<UserModel> users;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PRIVILEGES", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "uid"),
			inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "uid"))
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
