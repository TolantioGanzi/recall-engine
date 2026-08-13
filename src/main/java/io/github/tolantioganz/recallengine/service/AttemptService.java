package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.domain.Attempt;
import io.github.tolantioganz.recallengine.repository.AttemptRepository;
import org.springframework.stereotype.Service;

@Service
public class AttemptService {
    private final AttemptRepository attemptRepository;

    public AttemptService(AttemptRepository attemptRepository) {
        this.attemptRepository = attemptRepository;
    }
    public Attempt log(Attempt attempt) {
        return attemptRepository.logAttempt(attempt);
    }
}
