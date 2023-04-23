package com.gmail.lairmartes.shyftlab.result.service.impl;

import com.gmail.lairmartes.shyftlab.common.exception.RecordNotFoundException;
import com.gmail.lairmartes.shyftlab.course.entity.Course;
import com.gmail.lairmartes.shyftlab.course.repository.CourseRepository;
import com.gmail.lairmartes.shyftlab.result.ResultRepository;
import com.gmail.lairmartes.shyftlab.result.domain.Result;
import com.gmail.lairmartes.shyftlab.result.enums.Score;
import com.gmail.lairmartes.shyftlab.student.entity.Student;
import com.gmail.lairmartes.shyftlab.student.repository.StudentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class ResultServiceImplTest {

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private ResultRepository resultRepository;

    @Autowired
    private ResultServiceImpl resultService;


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

    private com.gmail.lairmartes.shyftlab.result.entity.Result createResultEntity(long id,
                                                                                  final Student student,
                                                                                  final Course course,
                                                                                  final Score score ) {

        return com.gmail.lairmartes.shyftlab.result.entity.Result
                .builder()
                .id(id)
                .student(student)
                .course(course)
                .score(score)
                .build();
    }

    private Result createResult(final long id, final Student student, Course course, String score) {
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

    @Nested
    class AddResult {

        @Test
        void whenDataAreValid_thenSaveCourse_andReturnNewCourse() {

            final Student student = createStudent(10L, "Sasuke", "Uchicha");
            final Course course = Course.builder().id(15L).name("Kunai II").build();

            when(studentRepository.findById(10L)).thenReturn(Optional.of(student));

            when(courseRepository.findById(15L)).thenReturn(Optional.of(course));

            final Result newResult = Result.builder().studentId(10L).courseId(15L).score("B").build();

            final var resultParameter = com.gmail.lairmartes.shyftlab.result.entity.Result
                    .builder().student(student).course(course).score(Score.B).build();

            final var savedResult = com.gmail.lairmartes.shyftlab.result.entity.Result
                    .builder().id(100L).student(student).course(course).score(Score.B).build();

            when(resultRepository.save(resultParameter)).thenReturn(savedResult);

            final var expectedServiceResult = Result
                    .builder()
                    .id(100L)
                    .studentId(10L)
                    .student(student)
                    .courseId(15L)
                    .course(course)
                    .score("B")
                    .build();

            final var actualServiceResult = resultService.addResult(newResult);

            verify(studentRepository, times(1)).findById(10L);
            verify(courseRepository, times(1)).findById(15L);
            verify(resultRepository, times(1)).save(resultParameter);

            assertEquals(expectedServiceResult, actualServiceResult);
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

            when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
            when(courseRepository.findById(anyLong()))
                    .thenReturn(Optional.of(Course.builder().id(15L).name("Course Name").build()));

            final RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class,
                    () -> resultService.addResult(Result.builder().studentId(10390L).courseId(15L).score("B").build()));

            final var expectedErrorMessage = "Student with id 10390 not found.";

            assertEquals(expectedErrorMessage, recordNotFoundException.getMessage());

        }

        @Test
        void whenCourseDoesNotExist_thenThrowsRecordNotFoundException_andReturnsCorrectMessage() {

            when(studentRepository.findById(anyLong()))
                    .thenReturn(Optional.of(createStudent(10L, "Mary", "Wolf")));

            final RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class,
                    () -> resultService.addResult(Result.builder().studentId(10L).courseId(20985L).score("B").build()));

            final var expectedErrorMessage = "Course with id 20985 not found.";

            assertEquals(expectedErrorMessage, recordNotFoundException.getMessage());

        }
    }

    @Test
    void listAllResults() {
        final var student1 = createStudent(10L, "First", "Student");
        final var student2 = createStudent(20L, "Second", "Student");

        final var course1 = Course.builder().id(15L).name("Course #1").build();
        final var course2 = Course.builder().id(25L).name("Course #2").build();

        final var resultEntities = List.of(
                createResultEntity(100L, student1, course1, Score.B),
                createResultEntity(110L, student1, course2, Score.A),
                createResultEntity(120L, student2, course1, Score.D),
                createResultEntity(130L, student2, course2, Score.F));

        final var expectedResultList = List.of(
                createResult(100L, student1, course1, "B"),
                createResult(110L, student1, course2, "A"),
                createResult(120L, student2, course1, "D"),
                createResult(130L, student2, course2, "F"));

        when(resultRepository.findAll()).thenReturn(resultEntities);

        final var actualResultList = resultService.listAllResults();

        verify(resultRepository, times(1)).findAll();

        assertEquals(expectedResultList, actualResultList);
    }
}