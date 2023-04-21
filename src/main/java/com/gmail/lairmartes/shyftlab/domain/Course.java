package com.gmail.lairmartes.shyftlab.domain;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Course {

    private long id;

    @NonNull
    private String name;

    public com.gmail.lairmartes.shyftlab.entity.Course toEntity() {
        return com.gmail.lairmartes.shyftlab.entity.Course.builder().name(this.name).build();
    }

    public static Course fromEntity(com.gmail.lairmartes.shyftlab.entity.Course entity) {
        return Course.builder().id(entity.getId()).name(entity.getName()).build();
    }
}