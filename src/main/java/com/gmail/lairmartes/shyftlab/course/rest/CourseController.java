package com.gmail.lairmartes.shyftlab.course.rest;

import com.gmail.lairmartes.shyftlab.course.rest.dto.CourseDTO;
import com.gmail.lairmartes.shyftlab.course.service.CourseService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/")
    public CourseDTO addCourse(@RequestBody CourseDTO course) {
        return CourseDTO.fromDomain(courseService.addCourse(course.toDomain()));
    }

    @GetMapping("/all")
    public List<CourseDTO> listAllCourses() {
        return courseService.listAllCourses().stream().map(CourseDTO::fromDomain).toList();
    }

    @DeleteMapping("/{id}")
    public void removeStudent(@PathVariable("id") @NotNull Long id) {
        courseService.removeCourseById(id);
    }
}
