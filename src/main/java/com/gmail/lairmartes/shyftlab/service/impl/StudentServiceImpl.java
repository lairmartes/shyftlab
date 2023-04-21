package com.gmail.lairmartes.shyftlab.service.impl;

import com.gmail.lairmartes.shyftlab.entity.Student;
import com.gmail.lairmartes.shyftlab.exception.InvalidDataException;
import com.gmail.lairmartes.shyftlab.repository.StudentRepository;
import com.gmail.lairmartes.shyftlab.service.StudentService;
import jakarta.validation.Validation;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final Clock clock;
    private final StudentRepository studentRepository;

    private static final int STUDENT_MINIMUM_AGE_ALLOWED = 10;
    @Override
    public Student addStudent(@NonNull Student newStudent) {

        return studentRepository.save(newStudent);
    }

    private boolean studentIsMinimumAllowedAge(Student newStudent) {
        final LocalDate now = LocalDate.now(clock);
        return ChronoUnit.YEARS.between(now, newStudent.getBirthDate()) >= STUDENT_MINIMUM_AGE_ALLOWED;
    }
}
