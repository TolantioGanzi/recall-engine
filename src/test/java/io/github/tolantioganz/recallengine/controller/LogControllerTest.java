package io.github.tolantioganz.recallengine.controller;

import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.service.LogOrchestrator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogController.class) // Loads on the controller layer for superfast tests
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc; // Fake HTTP client to simulate HTTP calls

    @MockitoBean
    private LogOrchestrator logOrchestrator; // Create fake version of the log orchestrator service

    @Test
    @DisplayName("GET /api/logs/validate - Success when problem exists")
    void validateProblem_ShouldReturnProblem_WhenExists() throws Exception {
        // Set up fake Data and tell the mock what to return when called
        LCProblem mockProblem = new LCProblem();
        mockProblem.setId(1);
        mockProblem.setTitle("Two Sum");
        mockProblem.setDifficulty(1);

        Mockito.when(logOrchestrator.getOfficialProblem(1)).thenReturn(mockProblem);

        // WHEN & THEN: Perform the fake GET request and verify the JSON output
        mockMvc.perform(get("/api/logs/validate").param("id", "1"))
                .andExpect(status().isOk())// Checks for HTTP 200 OK
                .andExpect(jsonPath("$.id").value(1)) // Checks for JSON field "id"
                .andExpect(jsonPath("$.title").value("Two Sum")); // Checks that title is Two sum

    }
    @Test
    @DisplayName("GET /api/logs/validate - Return 404 when problem not found")
    void validateProblem_ShouldReturn404_WhenNotFound() throws Exception {
        // GIVEN: Service returns null when problem 999 is requested
        Mockito.when(logOrchestrator.getOfficialProblem(999)).thenReturn(null);

        // WHEN & THEN
        mockMvc.perform(get("/api/logs/validate").param("id", "999"))
                .andExpect(status().isNotFound());
    }
}
