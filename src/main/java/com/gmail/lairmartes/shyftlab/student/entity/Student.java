package com.gmail.lairmartes.shyftlab.student.entity;

import com.gmail.lairmartes.shyftlab.result.entity.Result;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String firstName;
    @NotNull
    private String familyName;
    @NotNull
    private LocalDate birthDate;
    @NotNull
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
