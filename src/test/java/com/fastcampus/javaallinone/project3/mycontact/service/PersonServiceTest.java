package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class PersonServiceTest {
    @Autowired
    private PersonService personService;
    @Autowired
    private PersonRepository personRepository;


    @Test
    void getPeopleByName(){
        List<Person> result = personService.getPeopleByName("martin");

        assertEquals(1, result.size());
        assertEquals("martin", result.get(0).getName());
    }


    @Test
    void getPerson(){
        Person person = personService.getPerson(3L);
        assertEquals("dennis", person.getName());
    }

}