package com.fastcampus.javaallinone.project3.mycontact.controller;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@Transactional
class PersonControllerTest {
    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).setMessageConverters(messageConverter).build();
    }

    @Test
    void getPerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"))
                .andExpect(jsonPath("$.hobby").isEmpty()) // empty?????? ???????????? ??????: value??? ????????? key?????? ???????????? ???.
                .andExpect(jsonPath("$.address").isEmpty())
                .andExpect(jsonPath("$.birthday").value("1991-08-15"))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber()) // ????????? ???????????????, ????????? ????????? ????????? ????????????.
                .andExpect(jsonPath("$.birthdayToday").isBoolean()); // ????????? ????????????? ??? ?????? ???????????????, ??? ?????? ????????? ????????? ????????????.


        // ?????? Json ????????? ?????? ??????
        // .andExpect(jsonPath("$.name").value("martin"));

        // ?????? Java ????????? ?????? ??????
        // assertEquals("martin", result.getName());
    }


    @Test
    void postPerson() throws Exception{

        PersonDto dto = PersonDto.of("martin", "programming", "??????", LocalDate.now(), "programmer", "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andDo(print())
                .andExpect(status().isCreated());

        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0); // ?????? ????????? ?????? ??????
        assertAll(
                () -> assertEquals("martin", result.getName()),
                () -> assertEquals("programming", result.getHobby()),
                () -> assertEquals("??????", result.getAddress()),
                () -> assertEquals(Birthday.of(LocalDate.now()), result.getBirthday()),
                () -> assertEquals("programmer", result.getJob()),
                () -> assertEquals("010-1111-2222", result.getPhoneNumber())
        );
    }

    @Test
    void modifyPerson() throws Exception {

        PersonDto dto = PersonDto.of(
                "martin",
                "programming",
                "??????",
                LocalDate.now(),
                "programmer",
                "010-1111-2222");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andDo(print())
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();


        Assertions.assertAll(
            () -> Assertions.assertEquals("martin", result.getName()),
            () -> Assertions.assertEquals("programming", result.getHobby()),
            () -> Assertions.assertEquals("??????", result.getAddress()),
            () -> Assertions.assertEquals(Birthday.of(LocalDate.now()), result.getBirthday()),
            () -> Assertions.assertEquals("programmer", result.getJob()),
            () -> Assertions.assertEquals("010-1111-2222", result.getPhoneNumber())
        );
        // assertAll ??? ????????? ???????????? assert?????? ???????????? ??????????????? ?????? ????????? ????????? ??? ??????.
        // ??? ???, ??? assert?????? ????????? ????????????, ????????? ????????? ??????????????? ????????? ??????.

    }

    @Test
    void modifyPersonIfNameIsDifferent() throws Exception {

        PersonDto dto = PersonDto.of(
                "james",
                "programming",
                "??????", LocalDate.now(),
                "programmer",
                "010-1111-2222");

        assertThrows(NestedServletException.class, () ->
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/api/person/1")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(toJsonString(dto)))
                        .andDo(print())
                        .andExpect(status().isOk())
        );
    }

    @Test
    void modifyName() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                        .param("name","martinModified"))
                .andDo(print())
                .andExpect(status().isOk());

        Assertions.assertTrue(personRepository.findById(1L).isPresent());
        Assertions.assertEquals("martinModified", personRepository.findById(1L).get().getName());
    }

    @Test
    void deletePerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1")
        )
                .andDo(print())
                .andExpect(status().isOk());
        // .isOk()??? 200 ?????? ?????? ????????? ??????.
        // ???????????? ????????? ?????? ?????? :
        //  1. ?????????????????? void ????????? ????????? ???????????? ???(boolean ???)??? ??????????????? ?????? ???.
        //  deletePerson ??? ???????????? true??? ????????? ???????????? ????????? ?????????,
        //  ?????? ????????? ????????? ????????? ????????? ??? ?????? ????????? ?????? ????????? ????????? ????????????.
        //
        //  2. ????????? ???????????? ???????????? ??????????????? ???????????? ??????.
        // return personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(id));
        //
        // ?????? ?????? ??????:
        //  3.
        // ?????? controller????????? void ?????? ???, ??????????????? assertTrue??? ????????? ?????? ?????????????????? ??????.

        Assertions.assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));

    }

    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }



}