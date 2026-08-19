package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoringServiceTest {

    @MockitoBean
    private ScoringService scoringService;

    @BeforeEach
    void setUp() {
        scoringService = new ScoringService();
    }
    @Test
    void generateScore_ShouldReturnCorrectScore() throws Exception{
        DiagnosisRequest diagnosisRequest = new DiagnosisRequest(
                1,
                10,
                10,
                10,
                10,
                1,
                1,
                10,
                "arrays",
                "hash_map",
                "easy"
        );
        assertEquals(47.20, scoringService.generateScore(diagnosisRequest, 1));
    }
}
