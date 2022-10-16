package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.sun.javafx.collections.MappingChange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();
        person.setName("john");
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

        assertEquals(2, people.size());
        assertEquals("john", people.get(0).getName());
        assertEquals(10, people.get(0).getAge());
        assertEquals("A", people.get(0).getBloodType());

        System.out.println(people.get(0));

    }



    @Test
    void hashCodeAndEquals(){
        Person person1 = new Person("martin", 10, "A");
        Person person2 = new Person("martin", 10, "A");
        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        HashMap<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());
        System.out.println(map);
        System.out.println(map.get(person2));

    }


    @Test
    void findByBloodType(){
        givenPerson("martin", 10, "A");
        givenPerson("david", 9, "B");
        givenPerson("dennis", 8, "O");
        givenPerson("sophia", 7, "AB");
        givenPerson("benny", 6, "A");


        List<Person> result = personRepository.findByBloodType("A");
        result.forEach(System.out::println);

    }

    @Test
    void findByBirthdayBetween(){
        givenPerson("martin", 10, "A", LocalDate.of(1991,8,15));
        givenPerson("david", 9, "B", LocalDate.of(1992,7,10));
        givenPerson("dennis", 8, "O", LocalDate.of(1993,1,5));
        givenPerson("sophia", 7, "AB", LocalDate.of(1994,6,30));
        givenPerson("benny", 6, "A", LocalDate.of(1995,8,30));

        List<Person> result = personRepository.findByMonthOfBirthday(8);
        result.forEach(System.out::println);
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