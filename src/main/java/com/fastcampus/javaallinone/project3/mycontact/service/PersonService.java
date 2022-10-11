package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.domain.Block;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.BlockRepository;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        Person person = personRepository.findById(id).get();
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
}
