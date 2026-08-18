package io.github.tolantioganz.recallengine.repository;

import io.github.tolantioganz.recallengine.domain.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserProblemRepository extends JpaRepository<UserProblem, Integer> {
    @Query("SELECT p from UserProblem p WHERE p.nextRecall <= :now")
    List<UserProblem> getDueProblems(@Param("now") LocalDateTime now);
}

