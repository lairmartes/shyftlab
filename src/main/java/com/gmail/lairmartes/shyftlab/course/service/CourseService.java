package com.gmail.lairmartes.shyftlab.course.service;

import com.gmail.lairmartes.shyftlab.common.exception.RecordNotFoundException;
import com.gmail.lairmartes.shyftlab.course.domain.Course;
import jakarta.validation.Valid;
import lombok.NonNull;

import java.util.List;

public interface CourseService {

    Course addCourse(@NonNull @Valid Course newCourse);

    List<Course> listAllCourses();

    Course findById(long id) throws RecordNotFoundException;

    void removeCourseById(long id);
}
