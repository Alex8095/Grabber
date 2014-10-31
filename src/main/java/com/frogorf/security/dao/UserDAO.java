/**
 * 
 */
package com.frogorf.security.dao;

import java.util.List;

import com.frogorf.security.domain.User;

/** @author Tsurkin Alex
 * @version */
public interface UserDAO {

	public List<User> findUsers();
	
	public User getUser(String login);

	public User findUserById(int id);

	public void saveUser(User user);

	public void deleteUser(int id);

}
