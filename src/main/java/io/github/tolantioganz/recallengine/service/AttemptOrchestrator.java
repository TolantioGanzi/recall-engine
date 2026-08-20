package io.github.tolantioganz.recallengine.service;
import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.domain.UserAttempt;
import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import io.github.tolantioganz.recallengine.repository.LCProblemRepository;
import io.github.tolantioganz.recallengine.repository.UserAttemptRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AttemptOrchestrator {
    private final LCProblemRepository lcProblemRepo;
    private final UserAttemptRepository userAttemptRepo;
    private final ScoringService scoringService;


    public LCProblem getOfficialProblem(int problemId) {
        return lcProblemRepo.findById(problemId).orElse(null);
    }
    public UserAttempt record(DiagnosisRequest diagnosisRequest) {
        UserAttempt userAttempt = new UserAttempt(diagnosisRequest);
        long daysSinceAttempt = getDaysSinceLastAttempt(userAttempt);
        double score = scoringService.generateScore(diagnosisRequest, daysSinceAttempt);
        userAttempt.setDaysSinceAttempt(daysSinceAttempt);
        userAttempt.setPriorityScore(score);
        return userAttemptRepo.save(userAttempt);
    }
    public UserAttempt record(int problemID, DiagnosisRequest diagnosisRequest) {
        // ***TEST | MIGHT BE WRONG | PROBLEM_ID NEEDS TO BE USED**
        UserAttempt userAttempt = new UserAttempt(diagnosisRequest);
        long daysSinceAttempt = getDaysSinceLastAttempt(userAttempt);
        double score = scoringService.generateScore(diagnosisRequest, daysSinceAttempt);
        userAttempt.setDaysSinceAttempt(daysSinceAttempt);
        userAttempt.setPriorityScore(score);
        return userAttemptRepo.save(userAttempt);
    }
    public long getDaysSinceLastAttempt(UserAttempt userAttempt) {
        return userAttemptRepo.findLatestAttemptDateByProblemId(userAttempt.getProblemId())
                .map(latestDate -> ChronoUnit.DAYS.between(latestDate, LocalDate.now()))
                .orElse(0L);
    }
}


