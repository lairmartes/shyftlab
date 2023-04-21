package com.gmail.lairmartes.shyftlab.domain;

import com.gmail.lairmartes.shyftlab.entity.Course;
import com.gmail.lairmartes.shyftlab.entity.Student;
import com.gmail.lairmartes.shyftlab.enums.Score;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Result {

    private long id;

    @NonNull
    private Student student;

    @NonNull
    private Course course;

    @NonNull
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
