package com.gmail.lairmartes.shyftlab.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
public class Student {

    //include logs in service

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String firstName;
    @NotNull
    private String familyName;
    @NotNull
    private LocalDate birthDate;
    @Email(message = "Provide a valid email.")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Result> results;

    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Student)) return false;

        return this.id == ((Student) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
