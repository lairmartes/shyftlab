package com.gmail.lairmartes.shyftlab.service.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Course {

    private long id;
    private String name;

    public com.gmail.lairmartes.shyftlab.entity.Course toEntity() {
        return com.gmail.lairmartes.shyftlab.entity.Course.builder().name(this.name).build();
    }

    public static Course fromEntity(com.gmail.lairmartes.shyftlab.entity.Course entity) {
        return Course.builder().id(entity.getId()).name(entity.getName()).build();
    }
}
