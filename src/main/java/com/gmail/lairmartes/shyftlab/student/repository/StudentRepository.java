package com.gmail.lairmartes.shyftlab.student.repository;

import com.gmail.lairmartes.shyftlab.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT * FROM STUDENTS ORDER BY FIRST_NAME", nativeQuery = true)
    List<Student> findStudentsOrderedByName();
}
