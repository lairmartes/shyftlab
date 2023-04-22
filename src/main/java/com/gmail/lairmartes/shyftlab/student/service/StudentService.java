package com.gmail.lairmartes.shyftlab.student.service;

import com.gmail.lairmartes.shyftlab.student.entity.Student;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public interface StudentService {
    Student addStudent(@NonNull @Valid Student newStudent) throws MethodArgumentNotValidException;
}
