package com.giteshdalal.authservice.repository;

import com.giteshdalal.authservice.model.PrivilegeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<PrivilegeModel, String> {

}
