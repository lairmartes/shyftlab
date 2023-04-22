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
    public static final int STUDENT_MINIMUM_ALLOWED_AGE = 10;
    private static final String STUDENT_MINIMUM_ALLOWED_AGE_MESSAGE = "Minimum student age must be " + STUDENT_MINIMUM_ALLOWED_AGE + " years old.";

    private long id;
    @NotBlank(message = "Provide a first name.")
    private String firstName;
    @NotBlank(message = "Provide a family name.")
    private String familyName;
    @NotNull(message = "Provide a birth date.")
    @MinimumAge(value = STUDENT_MINIMUM_ALLOWED_AGE, message = STUDENT_MINIMUM_ALLOWED_AGE_MESSAGE)
    private LocalDate birthDate;
    @NotNull(message = "Provide a valid email.")
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
