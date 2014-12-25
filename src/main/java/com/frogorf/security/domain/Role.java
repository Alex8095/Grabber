package com.frogorf.security.domain;

import com.frogorf.core.domain.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Tsurkin Alex
 */
@Entity
@Table(name = "role")
public class Role extends BaseEntity {
    @Column
    @NotEmpty
    private String role;

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

    public Role() {

    }
}