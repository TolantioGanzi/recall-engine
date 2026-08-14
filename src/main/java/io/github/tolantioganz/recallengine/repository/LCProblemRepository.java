package io.github.tolantioganz.recallengine.repository;

import io.github.tolantioganz.recallengine.domain.LCProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LCProblemRepository extends JpaRepository<LCProblem, Integer> {
}
