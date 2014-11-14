/**
 *
 */
package com.frogorf.security.dao.impl;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.security.dao.RoleDAO;
import com.frogorf.security.domain.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author Tsurkin Alex
 */
@Repository("roleDao")
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Role getRole(int id) {
        Role role = (Role) getCurrentSession().load(Role.class, id);
        return role;
    }

    @Override
    public void saveRole(Role role) {
        sessionFactory.getCurrentSession().saveOrUpdate(role);
    }

    @Override
    public void deleteRole(int id) {
        sessionFactory.getCurrentSession().delete(getRole(id));
    }

    @Override
    public DataSourceResult getList(DataSourceRequest request) {
        return request.toDataSourceResult(sessionFactory.getCurrentSession(), Role.class);
    }

}
