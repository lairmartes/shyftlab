package com.gmail.lairmartes.shyftlab.repository;

import com.gmail.lairmartes.shyftlab.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
