package com.gmail.lairmartes.shyftlab.repository;

import com.gmail.lairmartes.shyftlab.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
