package com.hello.person.service;

import com.hello.person.domain.Person;

import java.util.List;

/**
 * Created by alex on 29.10.14.
 */
public interface PersonService {
    public List<Person> list();
    public void save(Person person);
}
