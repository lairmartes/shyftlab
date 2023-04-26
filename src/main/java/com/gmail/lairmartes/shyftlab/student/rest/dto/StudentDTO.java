package com.gmail.lairmartes.shyftlab.student.rest.dto;

import com.gmail.lairmartes.shyftlab.student.domain.Student;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {

    private Long id;
    private String firstName;
    private String familyName;
    private LocalDate birthDate;
    private String email;

    public Long getId() {
        return id == null ? 0L : id;
    }


    public Student toDomain() {
        return Student
                .builder()
                .id(getId())
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