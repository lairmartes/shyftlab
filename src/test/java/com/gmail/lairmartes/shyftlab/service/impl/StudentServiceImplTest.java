package com.gmail.lairmartes.shyftlab.service.impl;

import com.gmail.lairmartes.shyftlab.entity.Student;
import com.gmail.lairmartes.shyftlab.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

    @Mock
    private Clock mockClock;

    @Mock
    private StudentRepository mockStudentRepository;

    @Test
    void addStudent_whenEmailIsNotValid_thenThrowsException() {
        final Student studentTest = Student.builder().build();
    }
}