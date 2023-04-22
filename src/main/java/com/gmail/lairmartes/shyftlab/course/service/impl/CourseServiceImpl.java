package com.gmail.lairmartes.shyftlab.course.service.impl;

import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.course.repository.CourseRepository;
import com.gmail.lairmartes.shyftlab.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    public final CourseRepository courseRepository;
    @Override
    public Course addCourse(@NonNull @Valid Course newCourse) {
        return Course.fromEntity(courseRepository.save(newCourse.toEntity()));
    }

    @Override
    public List<Course> listAllCourses() {
        return courseRepository.findAll().stream().map(Course::fromEntity).toList();
    }

    @Override
    public void removeCourseById(long id) {
        courseRepository.deleteById(id);
    }
}
