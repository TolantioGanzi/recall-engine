package io.github.tolantioganz.recallengine.repository;

import io.github.tolantioganz.recallengine.domain.UserAttempt;
import org.springframework.data.jpa.repository.JpaRepository;




public interface UserAttemptRepository extends JpaRepository<UserAttempt, Integer> {
}
