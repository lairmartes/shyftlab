package com.gmail.lairmartes.shyftlab.course.rest.dto;

import com.gmail.lairmartes.shyftlab.course.domain.Course;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CourseDTO {

    private long id;
    private String name;

    public Course toDomain() {
        return Course.builder().id(this.id).name(this.name).build();
    }

    public static CourseDTO fromDomain(final Course domain) {
        return CourseDTO.builder().id(domain.getId()).name(domain.getName()).build();
    }
}
