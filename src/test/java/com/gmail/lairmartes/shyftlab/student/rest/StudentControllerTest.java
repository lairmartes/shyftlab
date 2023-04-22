package com.gmail.lairmartes.shyftlab.student.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.lairmartes.shyftlab.student.domain.Student;
import com.gmail.lairmartes.shyftlab.student.service.StudentService;
import com.gmail.lairmartes.shyftlab.util.TestUtilConstraintViolationBuilder;
import com.gmail.lairmartes.shyftlab.util.TestUtilFileLoader;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mvc;

    @Nested
    class AddStudent {

        @Test
        void whenDataAreValid_thenCallsService_andReturnStudentIncluded() throws Exception {

            when(studentService.addStudent(getStudentRequest())).thenReturn(getStudentResponse());

            final var expectedResponse = TestUtilFileLoader.loadTestFile("/students/validStudentResponse.json");

            final var addStudentRequestBody = TestUtilFileLoader.loadTestFile("/students/validStudentRequest.json");

            var mvcResult = mvc.perform(post("/students/")
                            .contentType("application/json")
                            .content(addStudentRequestBody))
                    .andExpect(status().isOk())
                    .andReturn();

            verify(studentService, times(1)).addStudent(getStudentRequest());

            JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
        }

        @Test
        void whenDataIsNotValid_thenCallService_andReturns400ErrorWithErrorMessages() throws Exception {

            final Set<ConstraintViolation<String>> violationList = Set.of(
                    TestUtilConstraintViolationBuilder.buildConstraintViolation("Validation Message 1"),
                    TestUtilConstraintViolationBuilder.buildConstraintViolation("Validation Message 2")
            );

            final var expectedErrorMessage = "null: Validation message 1, null: Validation message 2";

            when(studentService.addStudent(any(Student.class))).thenThrow(new ConstraintViolationException(violationList));

            var mvcResult = mvc.perform(post("/students/")
                            .contentType("application/json")
                            .content("{ }"))
                    .andExpect(status().is4xxClientError())
                    .andReturn();

            verify(studentService, times(1)).addStudent(any(Student.class));

            final String responseContent = mvcResult.getResponse().getContentAsString();

            assertNotNull(responseContent);
            assertTrue(responseContent.contains("Validation Message 1"));
            assertTrue(responseContent.contains("Validation Message 2"));
        }
    }

    @Nested
    class ListAllStudents {
        @Test
        void whenCalls_thenReturnStudentList() throws Exception {

            when(studentService.listAllStudents()).thenReturn(listAllStudentResponse());

            var mvcResult = mvc.perform(get("/students/"))
                    .andExpect(status().isOk())
                    .andReturn();

            final var expectedResponse = TestUtilFileLoader.loadTestFile("/students/allStudentsList.json");

            verify(studentService, times(1)).listAllStudents();

            JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
        }
    }

    @Nested
    class RemoveStudentById {

        @Test
        void whenProvidesId_thenCallsRemoveService() throws Exception {

            mvc.perform(delete("/students/34894"))
                    .andExpect(status().isOk());

            verify(studentService, times(1)).removeStudentById(34894L);
        }

        @Test
        void whenDoesNotProvideId_thenReturns400Error_andDoesNotCallService() throws Exception {

            mvc.perform(delete("/students/"))
                    .andExpect(status().is4xxClientError());

            verify(studentService, times(0)).removeStudentById(anyLong());
        }
    }

    private Student getStudentResponse() {
        return Student
                .builder()
                .id(1L)
                .firstName("Shikamaru")
                .familyName("Nara")
                .birthDate(LocalDate.of(1993,8,23))
                .email("shikamaru.nara@adm.konoha.gov.lv")
                .build();
    }

    private Student getStudentRequest() {
        return Student
                .builder()
                .firstName("Shikamaru")
                .familyName("Nara")
                .birthDate(LocalDate.of(1993,8,23))
                .email("shikamaru.nara@adm.konoha.gov.lv")
                .build();
    }

    private List<Student> listAllStudentResponse() {
        return List.of(
                getStudentResponse(),
                Student
                .builder()
                .id(2L)
                .firstName("Sasuke")
                .familyName("Uchiha")
                .birthDate(LocalDate.of(1993,7,17))
                .email("sasukeuchiha1993@konohamail.com")
                .build());
    }
}