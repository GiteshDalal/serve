package com.giteshdalal.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giteshdalal.authservice.model.RoleModel;

/**
 * @author gitesh
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleModel, String> {

}
