package com.gmail.lairmartes.shyftlab.course.entity;

import com.gmail.lairmartes.shyftlab.result.entity.Result;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
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
