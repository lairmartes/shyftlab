package com.gmail.lairmartes.shyftlab.result.domain;

import com.gmail.lairmartes.shyftlab.course.entity.Course;
import com.gmail.lairmartes.shyftlab.student.entity.Student;
import com.gmail.lairmartes.shyftlab.result.enums.Score;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Result {

    private long id;

    @Min(value = 1, message = "Student ID is mandatory.")
    private long studentId;

    @Min(value = 1, message = "Course ID is mandatory.")
    private long courseId;

    @NotBlank(message = "Provide a score A, B, C, D, E or F.")
    private String score;

    private Student student;
    private Course course;

    public com.gmail.lairmartes.shyftlab.result.entity.Result toEntity(final Student student, final Course course) {
        return com.gmail.lairmartes.shyftlab.result.entity.Result.builder()
                .student(student)
                .course(course)
                .score(Score.valueOf(this.score))
                .build();
    }

    public static Result fromEntity(com.gmail.lairmartes.shyftlab.result.entity.Result entity) {
        return Result.builder()
                .student(entity.getStudent())
                .course(entity.getCourse())
                .score(entity.getScore().name())
                .build();
    }
}
