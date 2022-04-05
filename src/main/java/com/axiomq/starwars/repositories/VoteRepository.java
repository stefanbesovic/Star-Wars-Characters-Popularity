package com.axiomq.starwars.repositories;

import com.axiomq.starwars.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query(
            value = "SELECT COUNT(DISTINCT v.user_id) FROM vote v WHERE v.character_id = ?1",
            nativeQuery = true)
    Integer countDistinctUsers(Long characterId);
}
