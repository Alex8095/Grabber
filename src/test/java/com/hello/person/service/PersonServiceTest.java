package com.hello.person.service;

import com.hello.config.HibernateConfig;
import com.hello.person.domain.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by alex on 29.10.14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = HibernateConfig.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testList() throws Exception {
        Person person = new Person();
        person.setEmail("setEmail");
        person.setUserName("setUserName");
        personService.save(person);
        List<Person> personList = personService.list();
        assertEquals(personList.size(), 1);
    }

    @Test
    public void testSave() throws Exception {
        Person person = new Person();
        person.setEmail("setEmail");
        person.setUserName("setUserName");
        personService.save(person);
    }
}
