package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;

    public List<Person> getPeopleExcludeBlocks(){

//        List<Block> blocks = blockRepository.findAll();
//        List<String> blockNames = blocks.stream().map(Block::getName).collect(Collectors.toList());
        /*
        List<Person> people = personRepository.findAll();
        return people.stream().filter(person -> person.getBlock() == null).collect(Collectors.toList());
         */
        return personRepository.findByBlockIsNull();
    }

    @Transactional
    public Person getPerson(Long id){
        //Person person = personRepository.findById(id).get();

        Person person = personRepository.findById(id).orElse(null);

        // System.out.println("person : "+person);
        log.info("person : {}", person);
        return person;
    }

    public List<Person> getPeopleByName(String name) {
        /*
        List<Person> people = personRepository.findAll();
        return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
         */
        return personRepository.findByName(name);
    }

    public void put(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, Person person) {
        Person personAtDb = personRepository.findById(id).orElseThrow( () -> new RuntimeException("아이디가 존재하지 않습니다."));

        personAtDb.setName(person.getName());
        personAtDb.setPhoneNumber(person.getPhoneNumber());
        personAtDb.setJob(person.getJob());
        personAtDb.setBirthday(person.getBirthday());
        personAtDb.setAddress(person.getAddress());
        personAtDb.setBloodType(person.getBloodType());
        personAtDb.setHobby(person.getHobby());
        personAtDb.setAge(person.getAge());

        personRepository.save(personAtDb);

    }
}
