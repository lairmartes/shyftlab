package com.gmail.lairmartes.shyftlab.student.rest;

import com.gmail.lairmartes.shyftlab.student.rest.dto.StudentDTO;
import com.gmail.lairmartes.shyftlab.student.service.StudentService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/")
    public StudentDTO addStudent(@RequestBody StudentDTO newStudent) {
        return StudentDTO.fromDomain(studentService.addStudent(newStudent.toDomain()));
    }

    @GetMapping("/")
    public List<StudentDTO> listAllStudents() {
        return studentService.listAllStudents().stream().map(StudentDTO::fromDomain).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void removeStudent(@PathVariable("id") @NotNull Long id) {
        studentService.removeStudentById(id);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
