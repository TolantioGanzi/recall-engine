package io.github.tolantioganz.recallengine.repository;

import io.github.tolantioganz.recallengine.domain.UserAttempt;
import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import io.github.tolantioganz.recallengine.service.ScoringService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class UserAttemptRepositoryTest {

    @Autowired private UserAttemptRepository userAttemptRepo;
    @MockitoBean private ScoringService scoringService;

    @Test
    void findHighestPriorityProblem_ShouldReturnProblemWithHighestPriorityScore() {
        DiagnosisRequest reqA = new DiagnosisRequest(
                1, 10, 10, 10, 10,
                0, 1, 20, "hash_map",
                "For each number, store complement (target - number) in a hash map and scan array",
                "easy"
        );
        double lowScore = scoringService.generateScore(reqA, 0);
        UserAttempt userAttemptLowPriority = new UserAttempt(reqA);
        userAttemptLowPriority.setDaysSinceAttempt(0);
        userAttemptLowPriority.setPriorityScore(lowScore);
        userAttemptRepo.save(userAttemptLowPriority);

        DiagnosisRequest reqB = new DiagnosisRequest(
                3, 5, 5, 5,
                5, 2, 0, 30, "sliding_window",
                "sliding window + frequency map",
                "medium"
        );
        double highScore = scoringService.generateScore(reqB, 0);
        UserAttempt userAttemptHighPriority = new UserAttempt(reqB);
        userAttemptHighPriority.setDaysSinceAttempt(0);
        userAttemptLowPriority.setPriorityScore(highScore);
        userAttemptRepo.save(userAttemptHighPriority);

        Optional<UserAttempt> priorityAttempt = userAttemptRepo.findFirstByOrderByPriorityScoreDesc();

        assertTrue(priorityAttempt.isPresent());
        assertEquals(3, priorityAttempt.get().getProblemId());
    }
}
