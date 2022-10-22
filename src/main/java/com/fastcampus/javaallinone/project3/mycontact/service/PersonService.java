package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
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
    public void modify(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id).orElseThrow( () -> new RuntimeException("아이디가 존재하지 않습니다."));

        if(!person.getName().equals(personDto.getName())){
            throw new RuntimeException("이름이 다릅니다.");
        }

        if (personDto.getBirthday() != null){
            person.setBirthday(new Birthday(personDto.getBirthday()));
        }

        person.set(personDto);

        personRepository.save(person);

    }

    @Transactional
    public void modify(Long id, String name){
        Person person = personRepository.findById(id).orElseThrow( () -> new RuntimeException("아이디가 존재하지 않습니다."));

        person.setName(name);
        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        Person person = personRepository.findById(id).orElseThrow( () -> new RuntimeException("아이디가 존재하지 않습니다."));

        person.setDeleted(true);
        personRepository.save(person);
    }
}
