package com.gmail.lairmartes.shyftlab.result.service.impl;

import com.gmail.lairmartes.shyftlab.common.exception.RecordNotFoundException;
import com.gmail.lairmartes.shyftlab.course.entity.Course;
import com.gmail.lairmartes.shyftlab.course.repository.CourseRepository;
import com.gmail.lairmartes.shyftlab.result.domain.Result;
import com.gmail.lairmartes.shyftlab.student.entity.Student;
import com.gmail.lairmartes.shyftlab.student.repository.StudentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResultServiceImplIntegrationTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @MockBean
    private Clock mockClock;

    @Autowired
    private ResultServiceImpl resultService;

    @BeforeEach
    public void cleanRepositories() {
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    private List<Student> createStudents() {

        return List.of(
                studentRepository.save(Student
                        .builder()
                        .firstName("Sasuke")
                        .familyName("Uchiha")
                        .birthDate(LocalDate.of(1993, 8, 23))
                        .email("su@email.com")
                        .build()),
                studentRepository.save(Student
                        .builder()
                        .firstName("Shikamaru")
                        .familyName("Nara")
                        .birthDate(LocalDate.of(1993, 7, 17))
                        .email("sn@email.com")
                        .build()),
                studentRepository.save(Student
                        .builder()
                        .firstName("Uzumaki")
                        .familyName("Naruto")
                        .birthDate(LocalDate.of(1993, 10, 10))
                        .email("un@email.com")
                        .build()),
                studentRepository.save(Student
                        .builder()
                        .firstName("Sakura")
                        .familyName("Haruno")
                        .birthDate(LocalDate.of(1993,12,13))
                        .email("sn@email.com")
                        .build()),
                studentRepository.save(Student
                        .builder()
                        .firstName("Hinata")
                        .familyName("Hyuga")
                        .birthDate(LocalDate.of(1993,7,17))
                        .email("sn@email.com")
                        .build())
        );
    }

    private List<Course> createCourses() {
        return List.of(
                courseRepository.save(Course.builder().name("Kunai IV").build()),
                courseRepository.save(Course.builder().name("Kage Bushin III").build()),
                courseRepository.save(Course.builder().name("Kekkai Ninjutsu").build()),
                courseRepository.save(Course.builder().name("Advanced Chakra Control").build())
        );
    }

    @Nested
    class AddResult {

        @Test
        void whenDataAreValid_thenSaveCourse_andReturnNewCourse() {

            final List<Student> students = createStudents();
            final List<Course> courses = createCourses();

            final long studentId = students.get(0).getId();
            final long courseId = courses.get(0).getId();

            final Result newResult = Result.builder().studentId(studentId).courseId(courseId).score("B").build();

            final Result savedResult = resultService.addResult(newResult);

            final var expectedListResult = List.of( savedResult );

            assertEquals(expectedListResult, resultService.listAllResults());
        }

        @Test
        void whenFieldsAreNotProvided_theValidatesFields_andThrowsConstraintViolationException() {
            final Result invalidResultTest = Result.builder().build();

            final List<String> expectedMessages = List.of(
                    "Student ID is mandatory.",
                    "Course ID is mandatory.",
                    "Provide a score A, B, C, D, E or F."
            );

            final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                    () -> resultService.addResult(invalidResultTest));

            assertFalse(exception.getConstraintViolations().isEmpty());
            assertTrue(exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
                    .containsAll(expectedMessages));

            assertTrue(resultService.listAllResults().isEmpty());
        }

        @Test
        void whenStudentDoesNotExist_thenThrowsRecordNotFoundException_andReturnsCorrectMessage() {

            var databaseCourseList = createCourses();

            final Result newResult = Result
                    .builder()
                    .studentId(10390L)
                    .courseId(databaseCourseList.get(0).getId())
                    .score("B")
                    .build();

            final RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class,
                    () -> resultService.addResult(newResult) );

            final var expectedErrorMessage = "Student with id 10390 not found.";

            assertEquals(expectedErrorMessage, recordNotFoundException.getMessage());

        }

        @Test
        void whenCourseDoesNotExist_thenThrowsRecordNotFoundException_andReturnsCorrectMessage() {

            var databaseStudentList = createStudents();

            final Result newResult = Result
                    .builder()
                    .studentId(databaseStudentList.get(0).getId())
                    .courseId(20985L)
                    .score("B")
                    .build();

            final RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class,
                    () -> resultService.addResult(newResult) );

            final var expectedErrorMessage = "Course with id 20985 not found.";

            assertEquals(expectedErrorMessage, recordNotFoundException.getMessage());

        }
    }

    @Test
    void listAllResults() {
    }
}