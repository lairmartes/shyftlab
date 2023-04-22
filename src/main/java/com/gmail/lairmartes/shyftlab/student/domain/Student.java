package com.gmail.lairmartes.shyftlab.student.domain;

import com.gmail.lairmartes.shyftlab.common.validator.MinimumAge;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Getter
@Builder
@EqualsAndHashCode
@ToString
@Validated
public class Student {
    private long id;
    @NotBlank(message = "Provide a first name.")
    private String firstName;
    @NotBlank(message = "Provide a family name.")
    private String familyName;
    @NotNull(message = "Provide a birth date")
    @MinimumAge(value = 10, message = "Minimum student age must be 10 years old.")
    private LocalDate birthDate;
    @Email(message = "Provide a valid email.")
    private String email;

    public com.gmail.lairmartes.shyftlab.student.entity.Student toEntity() {
        return com.gmail.lairmartes.shyftlab.student.entity.Student.builder()
                .firstName(this.firstName)
                .familyName(this.familyName)
                .birthDate(this.birthDate)
                .email(this.email)
                .build();
    }

    public static Student fromEntity(com.gmail.lairmartes.shyftlab.student.entity.Student entity) {
        return Student.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .familyName(entity.getFamilyName())
                .birthDate(entity.getBirthDate())
                .email(entity.getEmail())
                .build();
    }
}
