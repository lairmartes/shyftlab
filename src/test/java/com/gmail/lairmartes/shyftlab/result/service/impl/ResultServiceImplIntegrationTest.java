package com.gmail.lairmartes.shyftlab.result.service.impl;

import com.gmail.lairmartes.shyftlab.common.validator.MinimumAgeValidator;
import com.gmail.lairmartes.shyftlab.course.entity.Course;
import com.gmail.lairmartes.shyftlab.course.repository.CourseRepository;
import com.gmail.lairmartes.shyftlab.result.domain.Result;
import com.gmail.lairmartes.shyftlab.student.entity.Student;
import com.gmail.lairmartes.shyftlab.student.repository.StudentRepository;
import com.gmail.lairmartes.shyftlab.util.TestUtilLocalValidatorFactoryBean;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    }

    @Test
    void listAllResults() {
    }
}