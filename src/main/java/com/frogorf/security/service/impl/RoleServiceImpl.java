/**
 *
 */
package com.frogorf.security.service.impl;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.security.dao.RoleDAO;
import com.frogorf.security.domain.Role;
import com.frogorf.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tsurkin Alex
 */

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;

    @Override
    @Transactional
    public Role getRole(int id) {
        return roleDAO.getRole(id);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    public void deleteRole(int id) {
        roleDAO.deleteRole(id);
    }

    @Override
    @Transactional
    public DataSourceResult getList(DataSourceRequest request) {
        return roleDAO.getList(request);
    }

}
