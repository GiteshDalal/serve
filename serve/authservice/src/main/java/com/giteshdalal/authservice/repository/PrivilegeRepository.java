package com.giteshdalal.authservice.repository;

import java.util.Optional;

import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.QPrivilegeModel;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface PrivilegeRepository extends BaseServeRepository<PrivilegeModel, QPrivilegeModel, Long> {

	Optional<PrivilegeModel> findOptionalByName(String name);
}
