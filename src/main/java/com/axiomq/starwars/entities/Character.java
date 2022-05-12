package com.axiomq.starwars.entities;

import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Gender gender;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "character_films",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Film> films;

    private Planet planet;
    private Integer votersCount;

    @OneToMany(mappedBy = "character", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Vote> votes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return id.equals(character.id) && name.equals(character.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", planet=" + planet +
                ", votersCount=" + votersCount +
                '}';
    }
}
