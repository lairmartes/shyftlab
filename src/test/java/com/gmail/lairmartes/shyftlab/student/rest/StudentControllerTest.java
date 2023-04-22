package com.gmail.lairmartes.shyftlab.student.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.lairmartes.shyftlab.student.domain.Student;
import com.gmail.lairmartes.shyftlab.student.service.StudentService;
import com.gmail.lairmartes.shyftlab.util.TestFileLoader;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mvc;

    @Test
    void addStudent_whenDataAreValid_thenCallsService_andReturnStudentIncluded() throws Exception {

        when(studentService.addStudent(getStudentRequest())).thenReturn(getStudentResponse());

        String expectedResponse = TestFileLoader.loadTestFile("/students/validStudentResponse.json");

        String addStudentRequestBody = TestFileLoader.loadTestFile("/students/validStudentRequest.json");

        MvcResult mvcResponse = mvc.perform(post("/students/")
                .contentType("application/json")
                .content(addStudentRequestBody))
                .andExpect(status().isOk())
                .andReturn();

        verify(studentService, times(1)).addStudent(getStudentRequest());

        JSONAssert.assertEquals(expectedResponse, mvcResponse.getResponse().getContentAsString(), JSONCompareMode.STRICT);
    }


    private Student getStudentResponse() {
        return Student
                .builder()
                .id(1L)
                .firstName("Shikamaru")
                .familyName("Nara")
                .birthDate(LocalDate.of(1993,8,23))
                .email("shikamaru.nara@adm.konoha.gov.br")
                .build();
    }

    private Student getStudentRequest() {
        return Student
                .builder()
                .firstName("Shikamaru")
                .familyName("Nara")
                .birthDate(LocalDate.of(1993,8,23))
                .email("shikamaru.nara@adm.konoha.gov.br")
                .build();
    }
}