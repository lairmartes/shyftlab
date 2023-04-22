package com.gmail.lairmartes.shyftlab.service.impl;

import com.gmail.lairmartes.shyftlab.entity.Student;
import com.gmail.lairmartes.shyftlab.repository.StudentRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceImplTest {

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

    @Test
    void addStudent_whenStudentIsNotValid_thenThrowsConstraintViolationException() {
        final Student invalidStudentTest = Student.builder().build();

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                () ->  studentService.addStudent(invalidStudentTest));

        assertFalse(exception.getConstraintViolations().isEmpty());
    }

    @Test
    void addStudent_whenStudentIsValid_thenSaveInRepository() {
        final Student validStudent = Student
                .builder()
                .firstName("Naruto")
                .familyName("Uzumaki")
                .birthDate(LocalDate.of(1995, 10, 10))
                .email("naruto.uzumaki@adm.konoha.gov.br")
                .build();
    }
}