package com.gmail.lairmartes.shyftlab.service.impl;

import com.gmail.lairmartes.shyftlab.entity.Student;
import com.gmail.lairmartes.shyftlab.repository.StudentRepository;
import com.gmail.lairmartes.shyftlab.service.StudentService;
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

        return studentRepository.save(newStudent);
    }
}
