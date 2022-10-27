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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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


    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    void getPerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void postPerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"martin2\", \n" +
                                "}"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void modifyPerson() throws Exception {

        PersonDto dto = PersonDto.of(
                "martin",
                "programming",
                "판교",
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
            () -> Assertions.assertEquals("판교", result.getAddress()),
            () -> Assertions.assertEquals(Birthday.of(LocalDate.now()), result.getBirthday()),
            () -> Assertions.assertEquals("programmer", result.getJob()),
            () -> Assertions.assertEquals("010-1111-2222", result.getPhoneNumber())
        );
        // assertAll 로 단번에 여러가지 assert문을 실행하고 콘솔창에서 모든 결과를 확인할 수 있다.
        // 이 때, 각 assert문은 콤마로 구분하고, 각각을 함수형 람다문으로 작성해 준다.

    }

    @Test
    void modifyPersonIfNameIsDifferent() throws Exception {

        PersonDto dto = PersonDto.of(
                "james",
                "programming",
                "판교", LocalDate.now(),
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
        // .isOk()는 200 코드 상태 확인만 한다.
        // 추가적인 검증을 하는 방법 :
        //  1. 컨트롤러에서 void 리턴이 아니라 정상적인 값(boolean 등)을 리턴하도록 하는 것.
        //  deletePerson 의 반환값을 true로 바꾸면 간단하게 체크가 되지만,
        //  해당 함수의 성공적 실행만 확인할 뿐 삭제 여부에 대해 정확한 확인은 불가하다.
        //
        //  2. 삭제된 데이터를 확인하여 리턴값으로 돌려주는 방법.
        // return personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(id));
        //
        // 가장 좋은 방법:
        //  3.
        // 우선 controller부에서 void 리턴 후, 테스트에서 assertTrue로 올바른 값이 반환되었는지 체크.

        Assertions.assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));

    }

    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }



}