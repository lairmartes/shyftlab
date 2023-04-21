package com.gmail.lairmartes.shyftlab.service.domain;

import com.gmail.lairmartes.shyftlab.entity.Course;
import com.gmail.lairmartes.shyftlab.entity.Student;
import com.gmail.lairmartes.shyftlab.enums.Score;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Result {

    private long id;

    private Student student;

    private Course course;

    private Score score;

    public com.gmail.lairmartes.shyftlab.entity.Result toEntity() {
        return com.gmail.lairmartes.shyftlab.entity.Result.builder()
                .student(this.student)
                .course(this.course)
                .score(this.score)
                .build();
    }

    public static Result fromEntity(com.gmail.lairmartes.shyftlab.entity.Result entity) {
        return Result.builder()
                .student(entity.getStudent())
                .course(entity.getCourse())
                .score(entity.getScore())
                .build();
    }
}
