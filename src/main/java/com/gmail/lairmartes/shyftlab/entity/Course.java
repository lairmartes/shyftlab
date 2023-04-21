package com.gmail.lairmartes.shyftlab.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
public class Course {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Result> results;

    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Course)) return false;

        return this.id == ((Course) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
