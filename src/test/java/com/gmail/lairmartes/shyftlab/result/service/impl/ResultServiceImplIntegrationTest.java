package com.gmail.lairmartes.shyftlab.result.service.impl;

import com.gmail.lairmartes.shyftlab.common.exception.RecordNotFoundException;
import com.gmail.lairmartes.shyftlab.course.entity.Course;
import com.gmail.lairmartes.shyftlab.course.repository.CourseRepository;
import com.gmail.lairmartes.shyftlab.result.domain.Result;
import com.gmail.lairmartes.shyftlab.result.enums.Score;
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
import java.util.Objects;

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
                        .build())
        );
    }

    private List<Course> createCourses() {
        return List.of(
                courseRepository.save(Course.builder().name("Kage Bushin III").build()),
                courseRepository.save(Course.builder().name("Kekkai Ninjutsu").build())
        );
    }

    private void createResults(final List<Student> savedStudents, final List<Course> savedCourses) {

        final var studentId1 = savedStudents.get(0).getId();
        final var studentId2 = savedStudents.get(1).getId();
        final var studentId3 = savedStudents.get(2).getId();

        final var courseId1 = savedCourses.get(0).getId();
        final var courseId2 = savedCourses.get(1).getId();

        resultService.addResult(Result.builder().studentId(studentId1).courseId(courseId1).score("B").build());
        resultService.addResult(Result.builder().studentId(studentId1).courseId(courseId2).score("C").build());
        resultService.addResult(Result.builder().studentId(studentId2).courseId(courseId1).score("A").build());
        resultService.addResult(Result.builder().studentId(studentId2).courseId(courseId2).score("B").build());
        resultService.addResult(Result.builder().studentId(studentId3).courseId(courseId1).score("D").build());
        resultService.addResult(Result.builder().studentId(studentId3).courseId(courseId2).score("F").build());
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

            var savedCourses = createCourses();

            final Result newResult = Result
                    .builder()
                    .studentId(10390L)
                    .courseId(savedCourses.get(0).getId())
                    .score("B")
                    .build();

            final RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class,
                    () -> resultService.addResult(newResult) );

            final var expectedErrorMessage = "Student with id 10390 not found.";

            assertEquals(expectedErrorMessage, recordNotFoundException.getMessage());

        }

        @Test
        void whenCourseDoesNotExist_thenThrowsRecordNotFoundException_andReturnsCorrectMessage() {

            var savedStudents = createStudents();

            final Result newResult = Result
                    .builder()
                    .studentId(savedStudents.get(0).getId())
                    .courseId(20985L)
                    .score("B")
                    .build();

            final RecordNotFoundException recordNotFoundException = assertThrows(RecordNotFoundException.class,
                    () -> resultService.addResult(newResult) );

            final var expectedErrorMessage = "Course with id 20985 not found.";

            assertEquals(expectedErrorMessage, recordNotFoundException.getMessage());

        }
    }

    @Nested
    class ListAllResults {

        @Test
        void whenAllEntitiesArePresent_thenReturnsAllResults() {

            final var savedStudents = createStudents();
            final var savedCourses = createCourses();

            final var expectedResults = List.of(
                    new CompareResults(savedStudents.get(0), savedCourses.get(0), "B"),
                    new CompareResults(savedStudents.get(0), savedCourses.get(1), "C"),
                    new CompareResults(savedStudents.get(1), savedCourses.get(0), "A"),
                    new CompareResults(savedStudents.get(1), savedCourses.get(1), "B"),
                    new CompareResults(savedStudents.get(2), savedCourses.get(0), "D"),
                    new CompareResults(savedStudents.get(2), savedCourses.get(1), "F")
            );

            createResults(savedStudents, savedCourses);

            final var actualResults = resultService.listAllResults().stream().map(CompareResults::create).toList();

            assertTrue(actualResults.containsAll(expectedResults));
        }

        @Test
        void whenRemovingStudent_thenDoesNotShowTheirResults() {

            final var savedStudents = createStudents();
            final var savedCourses = createCourses();

            final var expectedResults = List.of(
                    new CompareResults(savedStudents.get(0), savedCourses.get(0), "B"),
                    new CompareResults(savedStudents.get(0), savedCourses.get(1), "C"),
                    new CompareResults(savedStudents.get(2), savedCourses.get(0), "D"),
                    new CompareResults(savedStudents.get(2), savedCourses.get(1), "F")
            );

            createResults(savedStudents, savedCourses);

            studentRepository.delete(savedStudents.get(1));

            final var actualResults = resultService.listAllResults().stream().map(CompareResults::create).toList();

            assertTrue(actualResults.containsAll(expectedResults));
        }

    }

    private static class CompareResults {
        final long studentId;
        final long courseId;
        final String score;

        private CompareResults(final Student student, final Course course, final String score) {
            this.studentId = student.getId();
            this.courseId = course.getId();
            this.score = score;
        }

        private static CompareResults create(final Result result) {
            return new CompareResults(result.getStudent(), result.getCourse(), result.getScore());
        }

        public boolean equals(Object o) {
            if (!(o instanceof CompareResults)) return false;

            return ( ((CompareResults)o).courseId == this.courseId
                    && ((CompareResults)o).studentId == this.studentId
                    && Objects.equals(((CompareResults) o).score, this.score));
        }
    }
}