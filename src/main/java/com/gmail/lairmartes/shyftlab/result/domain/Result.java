package com.gmail.lairmartes.shyftlab.result.domain;

import com.gmail.lairmartes.shyftlab.common.validator.ValueOfScore;
import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.student.domain.Student;
import com.gmail.lairmartes.shyftlab.result.enums.Score;
import jakarta.validation.constraints.Min;
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

    @ValueOfScore
    private String score;

    private Student student;
    private Course course;

    public com.gmail.lairmartes.shyftlab.result.entity.Result toEntity(final Student student, final Course course) {
        return com.gmail.lairmartes.shyftlab.result.entity.Result.builder()
                .student(student.toEntity())
                .course(course.toEntity())
                .score(Score.valueOf(this.score))
                .build();
    }

    public static Result fromEntity(com.gmail.lairmartes.shyftlab.result.entity.Result entity) {
        return Result.builder()
                .id(entity.getId())
                .studentId(entity.getStudent().getId())
                .student(Student.fromEntity(entity.getStudent()))
                .courseId(entity.getCourse().getId())
                .course(Course.fromEntity(entity.getCourse()))
                .score(entity.getScore().name())
                .build();
    }
}
