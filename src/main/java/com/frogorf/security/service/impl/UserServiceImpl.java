/**
 * 
 */
package com.frogorf.security.service.impl;

import java.util.List;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.security.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.frogorf.security.domain.User;

/** @author Tsurkin Alex
 * @version */
@Service("userService")
public class UserServiceImpl implements UserDAO {

	@Autowired
	private UserDAO userDao;

	@Override
	@Transactional
	public List<User> findUsers() {
		return userDao.findUsers();
	}

	@Override
	@Transactional
	public User getUser(String login) {
		return userDao.getUser(login);
	}

	@Override
	@Transactional
	public User findUserById(int id) {
		return userDao.findUserById(id);
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	@Transactional
	public void deleteUser(int id) {
		userDao.deleteUser(id);
	}

    @Override
    @Transactional
    public DataSourceResult getList(DataSourceRequest request) {
        return userDao.getList(request);
    }

}
