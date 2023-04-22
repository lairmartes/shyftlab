package com.gmail.lairmartes.shyftlab.result.service.impl;

import com.gmail.lairmartes.shyftlab.common.exception.RecordNotFoundException;
import com.gmail.lairmartes.shyftlab.course.entity.Course;
import com.gmail.lairmartes.shyftlab.course.repository.CourseRepository;
import com.gmail.lairmartes.shyftlab.result.ResultRepository;
import com.gmail.lairmartes.shyftlab.result.domain.Result;
import com.gmail.lairmartes.shyftlab.result.service.ResultService;
import com.gmail.lairmartes.shyftlab.student.entity.Student;
import com.gmail.lairmartes.shyftlab.student.repository.StudentRepository;
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
public class ResultServiceImpl implements ResultService {

    public final StudentRepository studentRepository;
    public final CourseRepository courseRepository;
    public final ResultRepository resultRepository;

    @Override
    public Result addResult(@NonNull @Valid Result result) {

        final Student student = studentRepository.findById(result.getStudentId())
                .orElseThrow(() -> new RecordNotFoundException("Student", result.getStudentId()));

        final Course course = courseRepository.findById(result.getCourseId())
                .orElseThrow(() -> new RecordNotFoundException("Course", result.getCourseId()));

        log.info("Adding score {} for Student ID {} and Course ID {}",
                result.getScore(), result.getStudentId(), result.getCourseId());

        return Result.fromEntity(resultRepository.save(result.toEntity(student, course)));
    }

    @Override
    public List<Result> listAllResults() {

        log.info("Fetching all results data.");

        return resultRepository.findAll().stream().map(Result::fromEntity).toList();
    }

}
