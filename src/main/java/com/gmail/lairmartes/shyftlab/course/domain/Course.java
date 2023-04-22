package com.gmail.lairmartes.shyftlab.course.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Course {

    private long id;

    @NotBlank(message = "Provide a name for the course.")
    private String name;

    public com.gmail.lairmartes.shyftlab.course.entity.Course toEntity() {
        return com.gmail.lairmartes.shyftlab.course.entity.Course.builder().name(this.name).build();
    }

    public static Course fromEntity(com.gmail.lairmartes.shyftlab.course.entity.Course entity) {
        return Course.builder().id(entity.getId()).name(entity.getName()).build();
    }
}
