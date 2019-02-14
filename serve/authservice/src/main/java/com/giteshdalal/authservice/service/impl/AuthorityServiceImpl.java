package com.giteshdalal.authservice.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.repository.PrivilegeRepository;
import com.giteshdalal.authservice.repository.RoleRepository;
import com.giteshdalal.authservice.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author gitesh
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PrivilegeRepository privilegeRepo;

	@Override
	public RoleModel saveRole(RoleModel role) {
		return roleRepo.save(role);
	}

	@Override
	public List<RoleModel> saveRoles(Collection<RoleModel> roles) {
		return roleRepo.saveAll(roles);
	}

	@Override
	public PrivilegeModel savePrilivege(PrivilegeModel privilege) {
		return privilegeRepo.save(privilege);
	}

	@Override
	public List<PrivilegeModel> savePrivilegess(Collection<PrivilegeModel> privileges) {
		return privilegeRepo.saveAll(privileges);
	}

	@Override
	public Page<RoleModel> getAllRoles(Pageable pageable) {
		return roleRepo.findAll(pageable);
	}

	@Override
	public Page<PrivilegeModel> getAllPrivileges(Pageable pageable) {
		return privilegeRepo.findAll(pageable);
	}

	@Override
	public Optional<RoleModel> getRoleByName(String name) {
		return roleRepo.findById(name);
	}

	@Override
	public Optional<PrivilegeModel> getPrivilegeByName(String name) {
		return privilegeRepo.findById(name);
	}
}
