package com.giteshdalal.authservice.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.giteshdalal.authservice.exceptions.NotFoundAuthServiceException;
import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.RoleModel;
import com.giteshdalal.authservice.repository.PrivilegeRepository;
import com.giteshdalal.authservice.repository.RoleRepository;
import com.giteshdalal.authservice.resource.PrivilegeResource;
import com.giteshdalal.authservice.resource.RoleResource;
import com.giteshdalal.authservice.service.AuthorityService;
import com.querydsl.core.types.Predicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		List<PrivilegeModel> validPrivileges = privileges.stream().filter(p -> StringUtils.isNotBlank(p.getName()))
				.collect(Collectors.toList());
		for (PrivilegeModel p : validPrivileges) {
			if (Objects.isNull(p.getUid())) {
				Optional<PrivilegeModel> optionalByName = getPrivilegeByName(p.getName());
				if (optionalByName.isPresent()) {
					p.setUid(optionalByName.get().getUid());
				}
			}
		}
		return privilegeRepo.saveAll(validPrivileges);
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
		return roleRepo.findOptionalByName(name);
	}

	@Override
	public Optional<PrivilegeModel> getPrivilegeByName(String name) {
		return privilegeRepo.findOptionalByName(name);
	}

	@Override
	public Page<RoleResource> findAllRoles(Predicate predicate, Pageable pageable) {
		Page<RoleModel> roles = roleRepo.findAll(predicate, pageable);
		return roles.map(model -> mapRole(model));
	}

	@Override
	public Page<PrivilegeResource> findAllPrivileges(Predicate predicate, Pageable pageable) {
		Page<PrivilegeModel> roles = privilegeRepo.findAll(predicate, pageable);
		return roles.map(model -> mapPrivilege(model));
	}

	@Override
	public Optional<RoleResource> findRoleById(Long uid) {
		Optional<RoleModel> entity = roleRepo.findById(uid);
		return entity.isPresent() ? Optional.ofNullable(mapRole(entity.get())) : Optional.empty();
	}

	@Override
	public Optional<PrivilegeResource> findPrivilegeById(Long uid) {
		Optional<PrivilegeModel> entity = privilegeRepo.findById(uid);
		return entity.isPresent() ? Optional.ofNullable(mapPrivilege(entity.get())) : Optional.empty();
	}

	@Override
	@Transactional
	public RoleResource saveRole(RoleResource resource) {
		resource.setUid(null);
		RoleModel entity = mapper.map(resource, RoleModel.class);
		return mapRole(roleRepo.save(entity));
	}

	@Override
	@Transactional
	public PrivilegeResource savePrivilege(PrivilegeResource resource) {
		resource.setUid(null);
		PrivilegeModel entity = mapper.map(resource, PrivilegeModel.class);
		return mapPrivilege(privilegeRepo.save(entity));
	}

	@Override
	@Transactional
	public RoleResource updateRole(Long uid, RoleResource resource) {
		Optional<RoleModel> role = roleRepo.findById(uid);
		if (role.isPresent()) {
			RoleModel entity = mapper.map(resource, RoleModel.class);
			entity.setUid(role.get().getUid());
			if (CollectionUtils.isNotEmpty(entity.getPrivileges())) {
				entity.setPrivileges(saveAllPrivileges(entity.getPrivileges()));
			}
			return mapRole(roleRepo.save(entity));
		}
		throw new NotFoundAuthServiceException("Role with id : '" + uid + "' not found!");
	}

	@Override
	@Transactional
	public PrivilegeResource updatePrivilege(Long uid, PrivilegeResource resource) {
		Optional<PrivilegeModel> privilege = privilegeRepo.findById(uid);
		if (privilege.isPresent()) {
			PrivilegeModel entity = mapper.map(resource, PrivilegeModel.class);
			entity.setUid(privilege.get().getUid());
			return mapPrivilege(privilegeRepo.save(entity));
		}
		throw new NotFoundAuthServiceException("Privilege with id : '" + uid + "' not found!");
	}

	@Override
	@Transactional
	public void deleteRoleById(Long uid) {
		Optional<RoleModel> role = roleRepo.findById(uid);
		if (role.isPresent()) {
			roleRepo.delete(role.get());
			return;
		}
		throw new NotFoundAuthServiceException("Role with id : '" + uid + "' not found!");
	}

	@Override
	@Transactional
	public void deletePrivilegeByUid(Long uid) {
		Optional<PrivilegeModel> privilege = privilegeRepo.findById(uid);
		if (privilege.isPresent()) {
			privilegeRepo.delete(privilege.get());
			return;
		}
		throw new NotFoundAuthServiceException("Privilege with id : '" + uid + "' not found!");
	}

	private RoleResource mapRole(RoleModel roleModel) {
		return mapper.map(roleModel, RoleResource.class);
	}

	private PrivilegeResource mapPrivilege(PrivilegeModel privilegeModel) {
		return mapper.map(privilegeModel, PrivilegeResource.class);
	}
}
