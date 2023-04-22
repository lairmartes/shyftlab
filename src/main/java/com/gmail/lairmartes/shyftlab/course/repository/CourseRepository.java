package com.gmail.lairmartes.shyftlab.course.repository;

import com.gmail.lairmartes.shyftlab.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
