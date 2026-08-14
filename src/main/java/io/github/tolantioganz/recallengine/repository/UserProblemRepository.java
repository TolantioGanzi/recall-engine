package io.github.tolantioganz.recallengine.repository;

import io.github.tolantioganz.recallengine.domain.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProblemRepository extends JpaRepository<UserProblem, Integer> {

}

