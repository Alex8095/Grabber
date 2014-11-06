/**
 *
 */
package com.frogorf.security.domain;

import com.frogorf.domain.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Tsurkin Alex
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    private String role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private Set<User> userRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<User> userRoles) {
        this.userRoles = userRoles;
    }

}