/**
 *
 */
package com.frogorf.security.service.impl;

import com.frogorf.kendo.data.source.DataSourceRequest;
import com.frogorf.kendo.data.source.DataSourceResult;
import com.frogorf.security.dao.UserDAO;
import com.frogorf.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Tsurkin Alex
 */
@Service("customAuthenticationService")
@Transactional
public class CustomAuthenticationServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserDAO userDao;

    /*
     * @see org.springframework.security.core.userdetails.UserDetailsService# loadUserByUsername(java.lang.String) */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        com.frogorf.security.domain.User domainUser = userDao.getUser(login);
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        return new User(domainUser.getLogin(), domainUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(domainUser.getRole().getId()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();
        if (role.intValue() == 1) {
            roles.add("ROLE_MODERATOR");
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 2) {
            roles.add("ROLE_MODERATOR");
        }
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
    public List<com.frogorf.security.domain.User> findUsers() {
        return userDao.findUsers();
    }

    @Override
    public com.frogorf.security.domain.User getUser(String login) {
        return userDao.getUser(login);
    }

    @Override
    public com.frogorf.security.domain.User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public void saveUser(com.frogorf.security.domain.User user) {
        userDao.saveUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    @Override
    public DataSourceResult getList(DataSourceRequest request) {
        return null;
    }
}
