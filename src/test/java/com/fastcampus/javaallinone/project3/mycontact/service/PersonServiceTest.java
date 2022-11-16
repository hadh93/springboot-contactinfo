package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));
        Person person = personService.getPerson(1L);
        assertEquals("martin", person.getName());
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
        Person person = personService.getPerson(1L);
        assertNull(person);
    }

    @Test
    void put(){
        personService.put(mockPersonDto());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void modifyIfPersonNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modifyIfNameIsDifferent(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("tony")));
        assertThrows(RuntimeException.class, () -> personService.modify(1L, mockPersonDto()));
    }

    @Test
    void modify(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));
        personService.modify(1L, mockPersonDto());

        verify(personRepository, times(1)).save(any(Person.class));
    }

    private PersonDto mockPersonDto(){
        return PersonDto.of("martin", "programming", "판교", LocalDate.now(), "programmer", "010-1111-2222");
    }


}