package io.github.tolantioganz.recallengine.service;
import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.domain.UserAttempt;
import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import io.github.tolantioganz.recallengine.repository.LCProblemRepository;
import io.github.tolantioganz.recallengine.repository.UserAttemptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class AttemptOrchestrator {
    private final LCProblemRepository lcProblemRepo;
    private final UserAttemptRepository userAttemptRepo;
    private final ScoringService scoringService;


    // validate problem
    public LCProblem getOfficialProblem(int problemID) {
        return lcProblemRepo.findById(problemID).orElse(null);
    }
    public UserAttempt record(DiagnosisRequest diagnosisRequest) {
        // calculate score
        double score = scoringService.generateScore(diagnosisRequest, 5); // CHANGE TEST
        // Create new UserAttempt using Data
        UserAttempt userAttempt = new UserAttempt(diagnosisRequest, score);
        // Save UserAttempt to Repo
        return userAttemptRepo.save(userAttempt);
    }
    public UserAttempt record(int problemID, DiagnosisRequest diagnosisRequest) {
        double score = scoringService.generateScore(diagnosisRequest, 5);
        UserAttempt userAttempt = new UserAttempt(diagnosisRequest, score);
        return userAttemptRepo.save(userAttempt);
    }

}


