/**
 * 
 */
package com.frogorf.security.service;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.security.domain.Role;

/** @author Tsurkin Alex
 * @version */
public interface RoleService {

	Role getRole(int id);

    void saveRole(Role role);

    void deleteRole(int id);

    DataSourceResult getList(DataSourceRequest request);
}
