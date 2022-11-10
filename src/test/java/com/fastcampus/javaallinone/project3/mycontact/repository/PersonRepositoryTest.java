package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByName(){
        List<Person> people = personRepository.findByName("tony");
        Assertions.assertEquals(1, people.size());
        Person person = people.get(0);
        Assertions.assertAll(
                () -> Assertions.assertEquals("tony", person.getName()),
                () -> Assertions.assertEquals("reading", person.getHobby()),
                () -> Assertions.assertEquals("서울", person.getAddress()),
                () -> Assertions.assertEquals(Birthday.of(LocalDate.of(1991, 7, 10)), person.getBirthday()),
                () -> Assertions.assertEquals("010-2222-5555", person.getPhoneNumber()),
                () -> Assertions.assertFalse(person.isDeleted())
                );
    }

    @Test
    void findByNameIfDeleted(){
        List<Person> people = personRepository.findByName("andrew");
        Assertions.assertEquals(0, people.size());
    }

    @Test
    void findByMonthOfBirthday(){
        List<Person> people = personRepository.findByMonthOfBirthday(7);

        Assertions.assertEquals(2, people.size());
        Assertions.assertAll(
                () -> Assertions.assertEquals("david", people.get(0).getName()),
                () -> Assertions.assertEquals("tony", people.get(1).getName())
        );
    }

    @Test
    void findPeopleDeleted(){
        List<Person> people = personRepository.findPeopleDeleted();

        Assertions.assertEquals(1, people.size());
        Assertions.assertEquals("andrew", people.get(0).getName());
    }

}