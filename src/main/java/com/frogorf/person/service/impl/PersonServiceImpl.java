package com.frogorf.person.service.impl;

import com.frogorf.person.dao.PersonDAO;
import com.frogorf.person.domain.Person;
import com.frogorf.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by alex on 29.10.14.
 */
@Service("personService")
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDAO personDAO;

    @Override
    @Transactional
    public List<Person> list() {
        return personDAO.list();
    }

    @Override
    @Transactional
    public void save(Person person) {
        personDAO.save(person);
    }
}
