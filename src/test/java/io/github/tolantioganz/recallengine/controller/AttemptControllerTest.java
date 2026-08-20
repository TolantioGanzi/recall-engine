package io.github.tolantioganz.recallengine.controller;

import io.github.tolantioganz.recallengine.domain.UserAttempt;
import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import io.github.tolantioganz.recallengine.service.AttemptOrchestrator;
import io.github.tolantioganz.recallengine.service.LogOrchestrator;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AttemptController.class) // Load the controller test
public class AttemptControllerTest {

    @Autowired private MockMvc mockMvc; // Fake HTTP client to simulate HTTP class
    @Autowired private ObjectMapper objectMapper; // Converts Java OBJECTS to Json

    @MockitoBean private AttemptOrchestrator attemptOrchestrator; // Creates fake version of attemptOrchestrator service
    @MockitoBean private LogOrchestrator logOrchestrator;


    @Test
    void recordAttempt_ShouldReturnHTTPOkStatus() throws Exception{
        // Fake data
        DiagnosisRequest req = new DiagnosisRequest(
                1, 10, 10, 10,
                10, 0, 1, 20, "arrays",
                "hash_map", "easy"
        );
        UserAttempt mockSavedAttempt = new UserAttempt(req);
        mockSavedAttempt.setPriorityScore(40.60);

        // Instruct attempt orchestrator to return mock attempt when record() is called
        Mockito.when(attemptOrchestrator.record(Mockito.any(DiagnosisRequest.class)))
                .thenReturn(mockSavedAttempt);
        // WHEN & THEN. Send fake HTTP POST request verify status and JSON Output
        mockMvc.perform(post("/api/attempts/diagnose")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req))) // Pass request payload as JSON
                .andExpect(status().isOk()) // Verify response is 200 ok
                .andExpect(jsonPath("$.priorityScore").value(40.60)); // Verify JSON field matches
    }
}
