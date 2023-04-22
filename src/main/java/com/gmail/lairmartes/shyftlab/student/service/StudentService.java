package com.gmail.lairmartes.shyftlab.student.service;

import com.gmail.lairmartes.shyftlab.student.domain.Student;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    Student addStudent(@NonNull @Valid Student newStudent);
}
