package com.axiomq.starwars.repositories;

import com.axiomq.starwars.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
}
