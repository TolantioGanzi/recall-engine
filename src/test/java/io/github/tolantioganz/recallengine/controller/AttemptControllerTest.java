package io.github.tolantioganz.recallengine.controller;

import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import io.github.tolantioganz.recallengine.service.AttemptOrchestrator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AttemptController.class) // Load the controller test
public class AttemptControllerTest {

    @Autowired private MockMvc mockMvc; // Fake HTTP client to simulate HTTP class
    @MockitoBean private AttemptOrchestrator attemptOrchestrator; // Creates fake version of attemptOrchestrator service


    @Test
    void recordAttempt_ShouldReturnHTTPOkStatus() {
        // Fake data
        DiagnosisRequest req = new DiagnosisRequest(
                1, 10, 10, 10,
                10, 0, 1, 20, "arrays",
                "hash_map", "easy"
        );
    }
}
