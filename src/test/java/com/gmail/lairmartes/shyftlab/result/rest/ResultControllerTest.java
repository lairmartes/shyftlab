package com.gmail.lairmartes.shyftlab.result.rest;

import com.gmail.lairmartes.shyftlab.common.exception.RecordNotFoundException;
import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.result.domain.Result;
import com.gmail.lairmartes.shyftlab.result.enums.Score;
import com.gmail.lairmartes.shyftlab.result.service.ResultService;
import com.gmail.lairmartes.shyftlab.student.domain.Student;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ResultController.class)
public class ResultControllerTest {

    @MockBean
    private ResultService resultService;

    @Autowired
    private MockMvc mvc;

    @Nested
    class AddResult {

        @Test
        void whenDataAreValid_thenCallsService_andReturnStudentIncluded() throws Exception {
            final var addResultRequest = Result.builder().studentId(10L).courseId(15L).score("F").build();

            final var student = createStudent(addResultRequest.getStudentId(), "Naruto", "Uzumaki");
            final var course = Course.builder().id(addResultRequest.getCourseId()).name("Kage Bushin III").build();

            final var responseFromService = Result
                    .builder()
                    .id(100L)
                    .studentId(addResultRequest.getStudentId())
                    .student(student)
                    .courseId(addResultRequest.getCourseId())
                    .course(course)
                    .score(addResultRequest.getScore())
                    .build();

            when(resultService.addResult(addResultRequest)).thenReturn(responseFromService);

            final var request = TestUtilFileLoader.loadTestFile("/results/post/request.json");
            final var expectedResponse = TestUtilFileLoader.loadTestFile("/results/post/response.json");

            var mvcResult = mvc.perform(post("/results/")
                            .contentType("application/json")
                            .content(request))
                    .andExpect(status().isOk())
                    .andReturn();

            verify(resultService, times(1)).addResult(addResultRequest);

            JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
        }

        @Test
        void whenDataIsNotValid_thenCallService_andReturns400ErrorWithErrorMessages() throws Exception {

            final Set<ConstraintViolation<String>> violationList = Set.of(
                    TestUtilConstraintViolationBuilder.buildConstraintViolation("Validation Message 1"),
                    TestUtilConstraintViolationBuilder.buildConstraintViolation("Validation Message 2")
            );

            when(resultService.addResult(any(Result.class))).thenThrow(new ConstraintViolationException(violationList));

            var mvcResult = mvc.perform(post("/results/")
                            .contentType("application/json")
                            .content("{ }"))
                    .andExpect(status().is4xxClientError())
                    .andReturn();

            verify(resultService, times(1)).addResult(any(Result.class));

            final String responseContent = mvcResult.getResponse().getContentAsString();

            assertNotNull(responseContent);
            assertTrue(responseContent.contains("Validation Message 1"));
            assertTrue(responseContent.contains("Validation Message 2"));
        }

        @Test
        void whenRequiredRecordIsNotFound_thenReturns400ErrorWithErrorMessages() throws Exception {

            final var exception = new RecordNotFoundException("Dependency", 22131L);

            when(resultService.addResult(any(Result.class))).thenThrow(exception);

            final var request = TestUtilFileLoader.loadTestFile("/results/post/request.json");

            var mvcResult = mvc.perform(post("/results/")
                            .contentType("application/json")
                            .content(request))
                    .andExpect(status().is4xxClientError())
                    .andReturn();

            verify(resultService, times(1)).addResult(any(Result.class));

            final String responseContent = mvcResult.getResponse().getContentAsString();

            assertNotNull(responseContent);
            assertTrue(responseContent.contains("Dependency with id 22131 not found."));
        }
    }

    @Nested
    class ListAllStudents {
        @Test
        void whenCalls_thenReturnStudentList() throws Exception {

            final var naruto = createStudent(10L, "Naruto", "Uzumaki");
            final var shikamaru = createStudent(20L, "Shikamaru", "Nara");
            final var sasuke = createStudent(30L, "Sasuke", "Uchiha");
            final var hinata = createStudent(40L, "Hinata", "Hyuuga");
            final var sakura = createStudent(50L, "Sakura", "Haruno");

            final var kageBushin = Course.builder().id(15L).name("Kage Bushin III").build();
            final var kawarin = Course.builder().id(25L).name("Kawarin").build();

            final var allResults = List.of(
                    createResult(100L, naruto, kageBushin, Score.F),
                    createResult(110L, shikamaru, kawarin, Score.C),
                    createResult(120L, sasuke, kageBushin, Score.B),
                    createResult(130L, hinata, kawarin, Score.A),
                    createResult(140, sakura, kawarin, Score.A));

            when(resultService.listAllResults()).thenReturn(allResults);

            var mvcResult = mvc.perform(get("/results/all"))
                    .andExpect(status().isOk())
                    .andReturn();

            final var expectedResponse = TestUtilFileLoader.loadTestFile("/results/get/all/response.json");

            verify(resultService, times(1)).listAllResults();

            JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
        }
    }

    private Student createStudent(final long id, final String firstName, final String lastName) {
        return Student
                .builder()
                .id(id)
                .firstName(firstName)
                .familyName(lastName)
                .birthDate(LocalDate.of(1993, 8, 23))
                .email(String.format("%s.%s@email.com", firstName.toLowerCase(), lastName.toLowerCase()))
                .build();
    }

    private Result createResult(final long id, final Student student, Course course, Score score) {
        return Result
                .builder()
                .id(id)
                .studentId(student.getId())
                .student(student)
                .courseId(course.getId())
                .course(course)
                .score(score.name())
                .build();
    }
}
