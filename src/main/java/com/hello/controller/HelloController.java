package com.hello.controller;

import com.hello.person.domain.Person;
import com.hello.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    PersonService personService;

    @RequestMapping(method = RequestMethod.GET, value = "/{name}")
    public String sayHello(@PathVariable("name") String name) {

        Person person = new Person();
        person.setUserName(name);
        person.setEmail("setEmail");
        personService.save(person);

        List<Person> personList = personService.list();

        StringBuilder sb = new StringBuilder();
        for (Person item : personList) {
            sb.append("<br/>");
            sb.append(item.toString());
        }
        return name + " from the server" + sb.toString();
    }

}
