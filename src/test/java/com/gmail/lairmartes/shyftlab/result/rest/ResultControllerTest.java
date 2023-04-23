package com.gmail.lairmartes.shyftlab.result.rest;

import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.result.domain.Result;
import com.gmail.lairmartes.shyftlab.result.service.ResultService;
import com.gmail.lairmartes.shyftlab.student.domain.Student;
import com.gmail.lairmartes.shyftlab.util.TestUtilFileLoader;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

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
            final var addResultRequest = Result.builder().studentId(10L).courseId(15L).score("B").build();

            //when(resultService.addResult(getStudentRequest())).thenReturn(getStudentResponse());

            final var expectedResponse = TestUtilFileLoader.loadTestFile("/students/addStudent/response.json");
        }
    }

    private Student createStudent(final long id, final String firstName, final String lastName) {
        return Student
                .builder()
                .id(id)
                .firstName(firstName)
                .familyName(lastName)
                .birthDate(LocalDate.of(1993, 8, 23))
                .email(String.format("%s.%s@email.com", firstName, lastName))
                .build();
    }

    private Result createResultResponse(final long id, final Student student, Course course, String score) {
        return Result
                .builder()
                .id(id)
                .studentId(student.getId())
                .student(student)
                .courseId(course.getId())
                .course(course)
                .score(score)
                .build();
    }
}
