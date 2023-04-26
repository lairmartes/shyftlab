package com.gmail.lairmartes.shyftlab.student.rest;

import com.gmail.lairmartes.shyftlab.student.rest.dto.StudentDTO;
import com.gmail.lairmartes.shyftlab.student.service.StudentService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/")
    public StudentDTO addStudent(@RequestBody StudentDTO newStudent) {
        return StudentDTO.fromDomain(studentService.addStudent(newStudent.toDomain()));
    }

    @GetMapping("/all")
    public List<StudentDTO> listAllStudents() {
        return studentService.listAllStudents().stream().map(StudentDTO::fromDomain).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void removeStudent(@PathVariable("id") @NotNull Long id) {
        studentService.removeStudentById(id);
    }
}
