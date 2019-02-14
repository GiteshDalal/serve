package com.giteshdalal.authservice.repository;

import com.giteshdalal.authservice.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleModel, String> {

}
