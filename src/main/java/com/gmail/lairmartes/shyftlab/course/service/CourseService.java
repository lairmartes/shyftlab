package com.gmail.lairmartes.shyftlab.course.service;

import com.gmail.lairmartes.shyftlab.course.domain.Course;
import jakarta.validation.Valid;
import lombok.NonNull;

import java.util.List;

public interface CourseService {

    Course addCourse(@NonNull @Valid Course newCourse);

    List<Course> listAllCourses();

    void removeCourseById(long id);
}
