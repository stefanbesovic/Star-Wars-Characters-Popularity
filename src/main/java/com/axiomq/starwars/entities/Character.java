package com.axiomq.starwars.entities;

import com.axiomq.starwars.enums.Gender;
import com.axiomq.starwars.enums.Planet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
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
    private Set<Film> films = new HashSet<>();
    private Planet planet;
    private Integer votersCount;

    @OneToMany(mappedBy = "character", orphanRemoval = true)
    private Set<Vote> votes = new HashSet<>();

    @ElementCollection
    private Set<String> usersEmail;

    public void addEmail(String email) { usersEmail.add(email); }
    public void removeEmail(String email) { usersEmail.remove(email); }

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
                ", films=" + films +
                ", planet=" + planet +
                ", votersCount=" + votersCount +
                '}';
    }
}
