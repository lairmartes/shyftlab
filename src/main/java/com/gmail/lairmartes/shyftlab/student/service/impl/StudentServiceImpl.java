package com.gmail.lairmartes.shyftlab.student.service.impl;

import com.gmail.lairmartes.shyftlab.student.domain.Student;
import com.gmail.lairmartes.shyftlab.student.repository.StudentRepository;
import com.gmail.lairmartes.shyftlab.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@Validated
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student addStudent(@NonNull @Valid Student newStudent) {

        return Student.fromEntity(studentRepository.save(newStudent.toEntity()));
    }
}
