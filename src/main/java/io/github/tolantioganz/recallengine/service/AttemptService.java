package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.domain.UserAttempt;
import io.github.tolantioganz.recallengine.repository.UserAttemptRepository;
import org.springframework.stereotype.Service;

@Service
public class AttemptService {
    private final UserAttemptRepository attemptRepository;

    public AttemptService(UserAttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }
    public UserAttempt log(UserAttempt attempt) {
        return attemptRepository.logAttempt(attempt);
    }
}
