package com.gmail.lairmartes.shyftlab.service;

import com.gmail.lairmartes.shyftlab.entity.Student;
import com.gmail.lairmartes.shyftlab.exception.InvalidDataException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public interface StudentService {
    Student addStudent(Student newStudent) throws MethodArgumentNotValidException;
}
