package io.github.tolantioganz.recallengine.repository;

import io.github.tolantioganz.recallengine.domain.UserAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserAttemptRepository extends JpaRepository<UserAttempt, Integer> {
    // Generates "SELECT * FROM attempts ORDER by priority_score DESC LIMIT 1"
    Optional<UserAttempt> findFirstByOrderByPriorityScoreDesc();
}
