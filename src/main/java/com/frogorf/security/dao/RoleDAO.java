/**
 * 
 */
package com.frogorf.security.dao;

import com.frogorf.security.domain.Role;

/** @author Tsurkin Alex
 * @version */
public interface RoleDAO {

	public Role getRole(int id);

    void saveRole(Role role);

    void deleteRole(int id);
}
