package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.domain.Attempt;
import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;

@Service
public class AttemptOrchestrator {
    private final ProblemService problemService;
    private final AttemptService attemptService;
    private final DiagnosisService diagnosisService;
    private final PriorityScoreService scoringService;

    public AttemptOrchestrator(ProblemService problemService,
                               AttemptService attemptService,
                               DiagnosisService diagnosisService,
                               PriorityScoreService scoringService) {
        this.problemService = problemService;
        this.attemptService = attemptService;
        this.diagnosisService = diagnosisService;
        this.scoringService = scoringService;
    }
    public Attempt record(int problemID, DiagnosisRequest req) {
        Problem problem = problemService.get(problemID);
        Attempt attempt = diagnosisService.build(problem, req);
        attempt.setPriorityScore(scoringService.score(/* which numbers*/));
        return attemptService.log(attempt);
    }
}
