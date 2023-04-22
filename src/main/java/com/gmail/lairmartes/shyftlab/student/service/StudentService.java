package com.gmail.lairmartes.shyftlab.student.service;

import com.gmail.lairmartes.shyftlab.student.domain.Student;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    Student addStudent(@NonNull @Valid Student newStudent);

    List<Student> listAllStudents();
}
