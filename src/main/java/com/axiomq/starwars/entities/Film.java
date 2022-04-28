package com.axiomq.starwars.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "films", fetch = FetchType.LAZY)
    private Set<Character> characters;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film film = (Film) o;
        return id.equals(film.id) && name.equals(film.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
