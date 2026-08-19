package io.github.tolantioganz.recallengine.repository;

import io.github.tolantioganz.recallengine.domain.UserAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;


public interface UserAttemptRepository extends JpaRepository<UserAttempt, Integer> {
    // Generates "SELECT * FROM attempts ORDER by priority_score DESC LIMIT 1"
    Optional<UserAttempt> findFirstByOrderByPriorityScoreDesc();

    @Query("SELECT a.attemptDate FROM UserAttempt a WHERE a.problemID = :problemID ORDER BY a.attemptDate DESC LIMIT 1")
    Optional<LocalDate> findLatestAttemptDateByProblemId(int problemId);

}
