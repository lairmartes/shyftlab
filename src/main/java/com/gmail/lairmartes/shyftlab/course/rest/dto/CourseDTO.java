package com.gmail.lairmartes.shyftlab.course.rest.dto;

import com.gmail.lairmartes.shyftlab.course.domain.Course;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CourseDTO {

    private Long id;
    private String name;

    public Long getId() {
        return id == null ? 0L : id;
    }

    public Course toDomain() {
        return Course.builder().id(getId()).name(this.name).build();
    }

    public static CourseDTO fromDomain(final Course domain) {
        return CourseDTO.builder().id(domain.getId()).name(domain.getName()).build();
    }
}
