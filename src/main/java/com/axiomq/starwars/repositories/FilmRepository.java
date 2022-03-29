package com.axiomq.starwars.repositories;

import com.axiomq.starwars.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}
