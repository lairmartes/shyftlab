package com.gmail.lairmartes.shyftlab.student.service.impl;

import com.gmail.lairmartes.shyftlab.student.domain.Student;
import com.gmail.lairmartes.shyftlab.student.repository.StudentRepository;
import com.gmail.lairmartes.shyftlab.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Validated
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student addStudent(@NonNull @Valid Student newStudent) {

        log.info("[STUDENT_SERVICE] Saving Student {}", newStudent);

        return Student.fromEntity(studentRepository.save(newStudent.toEntity()));
    }

    @Override
    public List<Student> listAllStudents() {
        log.info("[STUDENT_SERVICE] Fetching all Students data");

        return studentRepository.findAll().stream().map(Student::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void removeStudentById(long id) {
        log.info("[STUDENT_SERVICE] Removing Student id {}", id);
        studentRepository.deleteById(id);
    }
}
