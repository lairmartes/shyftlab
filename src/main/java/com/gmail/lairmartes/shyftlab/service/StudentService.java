package com.gmail.lairmartes.shyftlab.service;

import com.gmail.lairmartes.shyftlab.entity.Student;

public interface StudentService {
    long addStudent(Student newStudent) throws IllegalArgumentException;
}
