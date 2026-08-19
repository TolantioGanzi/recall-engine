package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoringServiceTest {

    private ScoringService scoringService;

    @BeforeEach
    void setUp() {
        scoringService = new ScoringService();
    }

    @Nested
    @DisplayName("Perfect Score Scenarios")
    class PerfectScores {

        @Test
        @DisplayName("Flawless attempt on Day 0 should give low priority (near 1.0)")
        void perfectAttempt_Day0_ReturnsLowPriority() {
            DiagnosisRequest req = new DiagnosisRequest(
                    1, 10, 10, 10, 10, 0, 1, 20, "arrays", "hash_map", "easy"
            );
            // Day 0: 0% forgotten -> performance = 1.0 -> need = 0 -> priority = 1.0
            assertEquals(1.00, scoringService.generateScore(req, 0), 0.01);
        }

        @Test
        @DisplayName("Flawless attempt on Day 14 (Half-Life) should increase priority smoothly")
        void perfectAttempt_Day14_ReturnsMediumPriority() {
            DiagnosisRequest req = new DiagnosisRequest(
                    1, 10, 10, 10, 10, 0, 1, 20, "arrays", "hash_map", "easy"
            );
            // Day 14: 50% forgotten -> priority increases naturally without maxing out to 100
            assertEquals(40.60, scoringService.generateScore(req, 14), 0.01);
        }
    }

    @Nested
    @DisplayName("Mid & Mediocre Score Scenarios")
    class MidScores {

        @Test
        @DisplayName("Decent attempt on Medium difficulty at Day 7")
        void midAttempt_Day7_ReturnsProportionalPriority() {
            DiagnosisRequest req = new DiagnosisRequest(
                    1, 6, 6, 6, 6, 1, 1, 30, "trees", "dfs", "medium"
            );
            // 6/10s + 1 hint -> Decent half life (7 days) -> At Day 7, forgotten fraction is 0.5
            assertEquals(74.755, scoringService.generateScore(req, 7), 0.01);
        }
    }

    @Nested
    @DisplayName("Bombed / Bad Performance Scenarios")
    class BadScores {

        @Test
        @DisplayName("Bombed Hard problem on Day 0 gets high immediate priority")
        void bombedAttempt_Day0_ReturnsHighPriority() {
            DiagnosisRequest req = new DiagnosisRequest(
                    1, 1, 1, 1, 1, 3, 0, 60, "graphs", "bfs", "hard"
            );
            // Poor scores + hints -> Very high initial priority
            assertEquals(100.00, scoringService.generateScore(req, 0), 0.01);
        }

        @Test
        @DisplayName("Bombed problem on Day 10 clamps safely at 100.0 without crashing")
        void bombedAttempt_Day10_ClampsToMax() {
            DiagnosisRequest req = new DiagnosisRequest(
                    1, 1, 1, 1, 1, 4, 0, 60, "graphs", "bfs", "hard"
            );
            assertEquals(100.00, scoringService.generateScore(req, 10), 0.01);
        }
    }

    @Nested
    @DisplayName("Invalid Data & Edge Cases")
    class EdgeCases {

        @Test
        @DisplayName("Negative hints and case-insensitive difficulty handles gracefully")
        void invalidInputs_HandledSafely() {
            DiagnosisRequest req = new DiagnosisRequest(
                    1, 15, -5, 10, 10, -3, 1, 0, "dp", "memoization", "EASY"
            );
            // Scores clamped between 0-10, hints floored at 0, "EASY" parsed correctly
            assertEquals(20.80, scoringService.generateScore(req, 0), 0.01);
        }

        @Test
        @DisplayName("Null request throws IllegalArgumentException")
        void nullRequest_ThrowsException() {
            assertThrows(IllegalArgumentException.class, () -> scoringService.generateScore(null, 5));
        }
    }
}