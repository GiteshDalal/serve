package com.giteshdalal.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giteshdalal.authservice.model.PrivilegeModel;

/**
 * @author gitesh
 *
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeModel, String> {

}
