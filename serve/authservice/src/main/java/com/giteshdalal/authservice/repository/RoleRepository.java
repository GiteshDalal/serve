package com.giteshdalal.authservice.repository;

import com.giteshdalal.authservice.model.QRoleModel;
import com.giteshdalal.authservice.model.RoleModel;
import org.springframework.stereotype.Repository;

/**
 * @author gitesh
 */
@Repository
public interface RoleRepository extends BaseServeRepository<RoleModel, QRoleModel, String> {

}
