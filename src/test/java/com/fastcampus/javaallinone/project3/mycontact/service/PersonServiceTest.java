package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Transactional
@ExtendWith(MockitoExtension.class) // Mockito 기능 이용하여 테스트가 가능해진다.
class PersonServiceTest {
    @InjectMocks //Test의 대상이 되는 클래스- Autowired를 대체
    private PersonService personService;
    @Mock // Test의 대상 이외가 되는 클래스- Autowired를 대체
    private PersonRepository personRepository;


    @Test
    void getPeopleByName(){
        when(personRepository.findByName("martin")) // if와 유사
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertEquals(1, result.size());
        assertEquals("martin", result.get(0).getName());
    }

}