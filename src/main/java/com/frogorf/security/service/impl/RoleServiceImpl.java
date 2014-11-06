/**
 * 
 */
package com.frogorf.security.service.impl;

import com.frogorf.security.dao.RoleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frogorf.security.domain.Role;
import com.frogorf.security.service.RoleService;

/** @author Tsurkin Alex
 * @version */

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	public Role getRole(int id) {
		return roleDAO.getRole(id);
	}

    @Override
    public void saveRole(Role role) {

    }

    @Override
    public void deleteRole(int id) {

    }

}
