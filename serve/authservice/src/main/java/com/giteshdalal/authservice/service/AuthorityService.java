package com.giteshdalal.authservice.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author gitesh
 */
public interface AuthorityService {

	RoleModel saveRole(RoleModel role);

	List<RoleModel> saveRoles(Collection<RoleModel> roles);

	PrivilegeModel savePrilivege(PrivilegeModel privilege);

	List<PrivilegeModel> savePrivilegess(Collection<PrivilegeModel> privileges);

	Page<RoleModel> getAllRoles(Pageable pageable);

	Page<PrivilegeModel> getAllPrivileges(Pageable pageable);

	Optional<PrivilegeModel> getPrivilegeByName(String name);

	Optional<RoleModel> getRoleByName(String name);

}
