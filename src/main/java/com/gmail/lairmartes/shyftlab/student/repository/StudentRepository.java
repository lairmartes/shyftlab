package com.gmail.lairmartes.shyftlab.student.repository;

import com.gmail.lairmartes.shyftlab.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
