package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.domain.UserAttempt;
import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import io.github.tolantioganz.recallengine.repository.LCProblemRepository;
import io.github.tolantioganz.recallengine.repository.UserAttemptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AttemptOrchestratorTest {
    @Mock private LCProblemRepository lcRepo;
    @Mock private UserAttemptRepository userAttemptRepo;
    @Mock private ScoringService scoringService;
    @InjectMocks private AttemptOrchestrator attemptOrchestrator;

    @Test
    void attemptProblem_ShouldCalculateScoreAndSave() {
        DiagnosisRequest req = new DiagnosisRequest(
                1, 10, 10, 10, 10, 0, 1, 20, "arrays", "hash_map", "easy"
        );
        Mockito.when(scoringService.generateScore(Mockito.eq(req), Mockito.anyLong())).thenReturn(40.60);
        Mockito.when(userAttemptRepo.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        UserAttempt saved = attemptOrchestrator.record(req);
        assertEquals(40.60, saved.getPriorityScore());
        Mockito.verify(userAttemptRepo).save(Mockito.any());
    }
}
