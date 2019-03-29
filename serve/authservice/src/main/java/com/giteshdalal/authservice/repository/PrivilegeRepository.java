package com.giteshdalal.authservice.repository;

import com.giteshdalal.authservice.model.PrivilegeModel;
import com.giteshdalal.authservice.model.QPrivilegeModel;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface PrivilegeRepository extends BaseServeRepository<PrivilegeModel, QPrivilegeModel, String> {

}
