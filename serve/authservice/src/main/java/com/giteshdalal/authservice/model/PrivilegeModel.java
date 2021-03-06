package com.giteshdalal.authservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

import lombok.Data;

/**
 * @author gitesh
 */
@Data
@Entity(name = "Privilege")
public class PrivilegeModel {

	@Id
	@TableGenerator(name = "privilegeSeqGen", table = "ID_GEN", pkColumnValue = "PRIVILEGE_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "privilegeSeqGen")
	private Long uid;

	@Column(unique = true, nullable = false)
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
