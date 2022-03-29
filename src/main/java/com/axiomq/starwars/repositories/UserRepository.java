package com.axiomq.starwars.repositories;

import com.axiomq.starwars.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
