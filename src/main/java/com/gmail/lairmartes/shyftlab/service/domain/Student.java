package com.gmail.lairmartes.shyftlab.service.domain;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Student {
    private long id;
    @NonNull
    private String firstName;
    @NonNull
    private String familyName;
    @NonNull
    private LocalDate birthDate;
    @Email(message = "Provide a valid email.")
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
