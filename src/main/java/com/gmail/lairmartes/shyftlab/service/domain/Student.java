package com.gmail.lairmartes.shyftlab.service.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Student {
    private long id;
    private String firstName;

    public Student(final String firstName,
                   final String familyName,
                   final LocalDate birthDate,
                   final String email) throws IllegalArgumentException {



        this.firstName = firstName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.email = email;
    }

    private String familyName;
    private LocalDate birthDate;
    private String email;

    public com.gmail.lairmartes.shyftlab.entity.Student toEntity() {
        return com.gmail.lairmartes.shyftlab.entity.Student.builder()
                .firstName(this.firstName)
                .familyName(this.familyName)
                .birthDate(this.birthDate)
                .email(this.email)
                .build();
    }

    public static Student fromEntity(com.gmail.lairmartes.shyftlab.entity.Student entity) {
        return Student.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .familyName(entity.getFamilyName())
                .birthDate(entity.getBirthDate())
                .email(entity.getEmail())
                .build();
    }
}
