/**
 *
 */
package com.frogorf.security.dao;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.security.domain.User;

import java.util.List;

/**
 * @author Tsurkin Alex
 */
public interface UserDAO {

    public List<User> findUsers();

    public User getUser(String login);

    public User findUserById(int id);

    public void saveUser(User user);

    public void deleteUser(int id);

    DataSourceResult getList(DataSourceRequest request);
}
