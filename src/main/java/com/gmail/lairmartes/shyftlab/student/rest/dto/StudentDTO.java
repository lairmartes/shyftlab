package com.gmail.lairmartes.shyftlab.student.rest.dto;

import com.gmail.lairmartes.shyftlab.student.domain.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class StudentDTO {

    private long id;
    private String firstName;
    private String familyName;
    private LocalDate birthDate;
    private String email;

    public Student toDomain() {
        return Student
                .builder()
                .id(this.id)
                .firstName(this.firstName)
                .familyName(this.familyName)
                .birthDate(this.birthDate)
                .email(this.email)
                .build();
    }

    public static StudentDTO fromDomain(final Student student) {
        return StudentDTO
                .builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .familyName(student.getFamilyName())
                .birthDate(student.getBirthDate())
                .email(student.getEmail())
                .build();
    }
}