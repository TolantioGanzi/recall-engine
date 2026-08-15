package io.github.tolantioganz.recallengine.controller;

import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.domain.UserAttempt;
import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import io.github.tolantioganz.recallengine.service.AttemptOrchestrator;
import io.github.tolantioganz.recallengine.service.LogOrchestrator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/attempts")
public class AttemptController {
    private final AttemptOrchestrator attemptOrchestrator;
    private final LogOrchestrator logOrchestrator;

    @GetMapping("/validate")
    public ResponseEntity<LCProblem> validateProblem(@RequestParam("id") int id) {
        LCProblem lcProblem = logOrchestrator.getOfficialProblem(id);
        if(lcProblem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lcProblem);
    }
    @PostMapping("/diagnose")
    public ResponseEntity<UserAttempt> diagnoseProblem(@RequestBody DiagnosisRequest request) {
        UserAttempt userAttempt = attemptOrchestrator.record(request);
        return ResponseEntity.ok(userAttempt);
    }
    @PostMapping("/diagnose/{problemID}")
    public ResponseEntity<UserAttempt> diagnoseProblem(@PathVariable int problemID, @RequestBody DiagnosisRequest request) {
        UserAttempt userAttempt = attemptOrchestrator.record(problemID, request);
        return ResponseEntity.ok(userAttempt);
    }

}
