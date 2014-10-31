package com.frogorf.person.dao.impl;

import com.frogorf.person.dao.PersonDAO;
import com.frogorf.person.domain.Person;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alex on 29.10.14.
 */
@Repository("personDAO")
public class PersonDAOImpl implements PersonDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Person> list() {
        return sessionFactory.getCurrentSession().createQuery("from Person").list();
    }

    @Override
    public void save(Person person) {
        sessionFactory.getCurrentSession().saveOrUpdate(person);
    }
}
