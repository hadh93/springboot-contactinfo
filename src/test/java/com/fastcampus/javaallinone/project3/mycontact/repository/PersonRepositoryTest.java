package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();
        person.setName("john");
        person.setBloodType("A");
        personRepository.save(person);

        // System.out.println(personRepository.findAll());
        // 첫번째 테스트 출력값:
        // [com.fastcampus.javaallinone.project3.mycontact.domain.Person@13390a96]
        //
        // 문제점:
        // 1. 객체의 해시값이 출력되었음. -> ToString 메서드로 해결.
        // 2. parameter 설정이 불가
        // 3. Console에 Log를 찍을 뿐 자동화된 테스트라 보기 어려움.
        //
        // 1차 수정 후 출력값:
        // [Person{id=1, name='null', age=0}]

        List<Person> people = personRepository.findByName("john");

        assertEquals(1, people.size());
        assertEquals("john", people.get(0).getName());
//        assertEquals(10, people.get(0).getAge());
        assertEquals("A", people.get(0).getBloodType());

    }

    @Test
    void findByBloodType(){


        List<Person> result = personRepository.findByBloodType("A");
        assertEquals(2, result.size());
        assertEquals("martin", result.get(0).getName());
        assertEquals("benny", result.get(1).getName());


    }

    @Test
    void findByBirthdayBetween(){
        List<Person> result = personRepository.findByMonthOfBirthday(8);


        assertEquals(2, result.size());
        assertEquals("martin", result.get(0).getName());
        assertEquals("sophia", result.get(1).getName());

    }

    private void givenPerson(String name, int age, String bloodType){
        givenPerson(name, age, bloodType, null);
    }

    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));
        personRepository.save(person);

    }
}