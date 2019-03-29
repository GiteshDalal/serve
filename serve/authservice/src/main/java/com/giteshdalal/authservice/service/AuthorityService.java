package com.giteshdalal.authservice.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.resource.PrivilegeResource;
import com.giteshdalal.authservice.resource.RoleResource;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author gitesh
 */
public interface AuthorityService {

	RoleModel saveRole(RoleModel role);

	List<RoleModel> saveRoles(Collection<RoleModel> roles);

	PrivilegeModel savePrivilege(PrivilegeModel privilege);

	List<PrivilegeModel> saveAllPrivileges(Collection<PrivilegeModel> privileges);

	Page<RoleModel> getAllRoles(Pageable pageable);

	Page<PrivilegeModel> getAllPrivileges(Pageable pageable);

	Optional<PrivilegeModel> getPrivilegeByName(String name);

	Optional<RoleModel> getRoleByName(String name);

	Page<RoleResource> findAllRoles(Predicate predicate, Pageable pageable);

	Page<PrivilegeResource> findAllPrivileges(Predicate predicate, Pageable pageable);

	Optional<RoleResource> findRoleById(Long uid);

	Optional<PrivilegeResource> findPrivilegeById(Long uid);

	RoleResource saveRole(RoleResource resource);

	PrivilegeResource savePrivilege(PrivilegeResource resource);

	RoleResource updateRole(Long uid, RoleResource resource);

	PrivilegeResource updatePrivilege(Long uid, PrivilegeResource resource);

	void deleteRoleById(Long uid);

	void deletePrivilegeByUid(Long uid);
}
