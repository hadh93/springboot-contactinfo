package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();
        person.setName("martin");
        person.setAge(10);
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

        List<Person> people = personRepository.findAll();
        assertEquals(1, people.size());
        assertEquals("martin", people.get(0).getName());
        assertEquals(10, people.get(0).getAge());
        assertEquals("A", people.get(0).getBloodType());

        System.out.println(people.get(0));

    }

}