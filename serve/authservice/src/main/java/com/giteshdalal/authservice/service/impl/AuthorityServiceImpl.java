package com.giteshdalal.authservice.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.giteshdalal.authservice.exceptions.BadRequestAuthServiceException;
import com.giteshdalal.authservice.exceptions.NotFoundAuthServiceException;
import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.repository.PrivilegeRepository;
import com.giteshdalal.authservice.repository.RoleRepository;
import com.giteshdalal.authservice.resource.RoleResource;
import com.giteshdalal.authservice.service.AuthorityService;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
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
	protected ModelMapper mapper;

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
	public PrivilegeModel savePrivilege(PrivilegeModel privilege) {
		return privilegeRepo.save(privilege);
	}

	@Override
	public List<PrivilegeModel> saveAllPrivileges(Collection<PrivilegeModel> privileges) {
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
	public Page<RoleResource> findAllRoles(Predicate predicate, Pageable pageable) {
		return null;
	}

	@Override
	public Optional<RoleResource> findRoleByName(String name) {
		return Optional.empty();
	}

	@Override
	public RoleResource saveRole(RoleResource resource) {
		Optional<RoleModel> role = roleRepo.findById(resource.getName());
		if (!role.isPresent()) {
			RoleModel entity = mapper.map(resource, RoleModel.class);
			return mapper.map(roleRepo.save(entity), RoleResource.class);
		}
		throw new BadRequestAuthServiceException("Role with name : '" + resource.getName() + "' already exists!");
	}

	@Override
	public RoleResource updateRole(String name, RoleResource resource) {
		Optional<RoleModel> role = roleRepo.findById(name);
		if (role.isPresent()) {
			RoleModel entity = mapper.map(resource, RoleModel.class);
			entity.setName(role.get().getName());
			return mapper.map(roleRepo.save(entity), RoleResource.class);
		}
		throw new NotFoundAuthServiceException("Role with name : '" + name + "' not found!");
	}

	@Override
	public void deleteRoleByName(String name) {
		Optional<RoleModel> role = roleRepo.findById(name);
		if (role.isPresent()) {
			roleRepo.delete(role.get());
		}
		throw new NotFoundAuthServiceException("Role with name : '" + name + "' not found!");
	}

	@Override
	public Optional<PrivilegeModel> getPrivilegeByName(String name) {
		return privilegeRepo.findById(name);
	}
}
