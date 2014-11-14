/**
 *
 */
package com.frogorf.security.service;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.security.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Tsurkin Alex
 */
public interface UserService extends UserDetailsService {

    public List<User> findUsers();

    public User getUser(String login);

    public User findUserById(int id);

    public void saveUser(User user);

    public void deleteUser(int id);

    DataSourceResult getList(DataSourceRequest request);
}
