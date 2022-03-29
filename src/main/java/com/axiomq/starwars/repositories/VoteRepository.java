package com.axiomq.starwars.repositories;

import com.axiomq.starwars.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
