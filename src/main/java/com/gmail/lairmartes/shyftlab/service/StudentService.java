package com.gmail.lairmartes.shyftlab.service;

import com.gmail.lairmartes.shyftlab.entity.Student;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public interface StudentService {
    Student addStudent(@NonNull @Valid Student newStudent) throws MethodArgumentNotValidException;
}
