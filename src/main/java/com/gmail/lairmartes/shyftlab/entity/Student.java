package com.gmail.lairmartes.shyftlab.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    private String firstName;
    private String familyName;
    private LocalDate birthDate;
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
