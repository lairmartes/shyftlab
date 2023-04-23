package com.gmail.lairmartes.shyftlab.course.service.impl;

import com.gmail.lairmartes.shyftlab.common.exception.RecordNotFoundException;
import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.course.repository.CourseRepository;
import com.gmail.lairmartes.shyftlab.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Slf4j
@Validated
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    public final CourseRepository courseRepository;
    @Override
    public Course addCourse(@NonNull @Valid Course newCourse) {

        log.info("[COURSE_SERVICE] Adding course {}", newCourse);

        return Course.fromEntity(courseRepository.save(newCourse.toEntity()));
    }

    @Override
    public List<Course> listAllCourses() {

        log.info("[COURSE_SERVICE] Fetching all courses data");

        return courseRepository.findAll().stream().map(Course::fromEntity).toList();
    }

    @Override
    public Course findById(long id) throws RecordNotFoundException {
        return Course.fromEntity(courseRepository
                .findById(id).orElseThrow(() -> new RecordNotFoundException("Course", id)));
    }

    @Override
    public void removeCourseById(long id) {
        log.info("[COURSE_SERVICE] Removing course {}", id);

        courseRepository.deleteById(id);
    }
}
