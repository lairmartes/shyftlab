package com.gmail.lairmartes.shyftlab.result.service.impl;

import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.course.service.CourseService;
import com.gmail.lairmartes.shyftlab.result.ResultRepository;
import com.gmail.lairmartes.shyftlab.result.domain.Result;
import com.gmail.lairmartes.shyftlab.result.service.ResultService;
import com.gmail.lairmartes.shyftlab.student.domain.Student;
import com.gmail.lairmartes.shyftlab.student.service.StudentService;
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

    public final StudentService studentService;
    public final CourseService courseService;
    public final ResultRepository resultRepository;

    @Override
    public Result addResult(@NonNull @Valid Result result) {

        final Student student = studentService.findById(result.getStudentId());

        final Course course = courseService.findById(result.getCourseId());

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
