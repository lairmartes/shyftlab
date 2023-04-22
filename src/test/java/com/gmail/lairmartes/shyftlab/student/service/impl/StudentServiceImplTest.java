package com.gmail.lairmartes.shyftlab.student.service.impl;

import com.gmail.lairmartes.shyftlab.student.domain.Student;
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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class StudentServiceImplTest {

    public static final String EXPECTED_MINIMUM_AGE_ERROR_MESSAGE = "Minimum student age must be " + Student.STUDENT_MINIMUM_ALLOWED_AGE + " years old.";
    @MockBean
    private Clock mockClock;

    @MockBean
    private StudentRepository mockStudentRepository;

    @Autowired
    private StudentServiceImpl studentService;

    @BeforeEach
    void setupClock() {
        var fixedTimeForTest = LocalDateTime.of(2023, 4, 21, 15, 30, 0);

        when(mockClock.getZone()).thenReturn(ZoneOffset.UTC);
        when(mockClock.instant()).thenReturn(fixedTimeForTest.toInstant(ZoneOffset.UTC));
    }

    @Nested
    class AddStudent {
        @Test
        void whenFieldsAreNotProvided_theValidatesFields_andThrowsConstraintViolationException() {
            final Student invalidStudentTest = Student.builder().build();

            final List<String> expectedMessages = List.of(
                    "Provide a first name.",
                    "Provide a family name.",
                    "Provide a birth date.",
                    EXPECTED_MINIMUM_AGE_ERROR_MESSAGE,
                    "Provide a valid email."
            );

            ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                    () -> studentService.addStudent(invalidStudentTest));

            assertFalse(exception.getConstraintViolations().isEmpty());
            assertTrue(exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
                    .containsAll(expectedMessages));

            verify(mockStudentRepository, never()).save(any());
        }

        @Test
        void whenEmailIsInvalid_thenThrowsViolationException() {

            final Student studentWithInvalidEmail = Student
                    .builder()
                    .firstName("Naruto")
                    .familyName("Uzumaki")
                    .birthDate(LocalDate.of(1995, 10, 10))
                    .email("naruto.uzumaki_adm.konoha.gov.lv")
                    .build();

            ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                    () -> studentService.addStudent(studentWithInvalidEmail));

            assertTrue(exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
                    .contains("Provide a valid email."));

            verify(mockStudentRepository, never()).save(any());
        }

        @Test
        void whenAgeIsNotMinimumAllowed_thenThrowsViolationException() {

            final Student studentWithInvalidEmail = Student
                    .builder()
                    .firstName("Naruto")
                    .familyName("Uzumaki")
                    .birthDate(LocalDate.of(2013, 4, 22))
                    .email("naruto.uzumaki@adm.konoha.gov.lv")
                    .build();

            ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                    () -> studentService.addStudent(studentWithInvalidEmail));

            assertTrue(exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
                    .contains(EXPECTED_MINIMUM_AGE_ERROR_MESSAGE));

            verify(mockStudentRepository, never()).save(any());
        }

        @Test
        void whenStudentIsValid_thenSaveInRepository() {

            final Student validStudent = Student
                    .builder()
                    .firstName("Naruto")
                    .familyName("Uzumaki")
                    .birthDate(LocalDate.of(1995, 10, 10))
                    .email("naruto.uzumaki@adm.konoha.gov.lv")
                    .build();

            final com.gmail.lairmartes.shyftlab.student.entity.Student mockResultEntity =
                    com.gmail.lairmartes.shyftlab.student.entity.Student
                            .builder()
                            .id(10L)
                            .firstName("Naruto")
                            .familyName("Uzumaki")
                            .birthDate(LocalDate.of(1995, 10, 10))
                            .email("naruto.uzumaki@adm.konoha.gov.lv")
                            .build();

            final Student expectedResult = Student
                    .builder()
                    .id(10L)
                    .firstName("Naruto")
                    .familyName("Uzumaki")
                    .birthDate(LocalDate.of(1995, 10, 10))
                    .email("naruto.uzumaki@adm.konoha.gov.lv")
                    .build();

            when(mockStudentRepository.save(validStudent.toEntity())).thenReturn(mockResultEntity);

            Student actualResult = studentService.addStudent(validStudent);

            verify(mockStudentRepository, times(1)).save(validStudent.toEntity());

            assertEquals(expectedResult, actualResult);
        }
    }
}