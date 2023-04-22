package com.gmail.lairmartes.shyftlab.student.rest;

import com.gmail.lairmartes.shyftlab.student.rest.dto.StudentDTO;
import com.gmail.lairmartes.shyftlab.student.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/")
    public StudentDTO addStudent(@RequestBody StudentDTO newStudent) {
        return StudentDTO.fromDomain(studentService.addStudent(newStudent.toDomain()));
    }

}
