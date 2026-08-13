package io.github.tolantioganz.recallengine.controller;

import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.service.LogOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/logs")
public class LogController {
    private final LogOrchestrator logOrchestrator;

    public LogController(LogOrchestrator logOrchestrator) {
        this.logOrchestrator = logOrchestrator;
    }

    /**
     *
     * @param id Problem ID of the LeetCode problem
     * @return LeetCode Problem Object
     */
    @GetMapping("validate")
    public ResponseEntity<LCProblem> validateProblem(@RequestParam("id") int id) {
        LCProblem lcProblem = logOrchestrator.get(id);
        if(lcProblem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lcProblem);
    }

    /**
     *
     * @param problem LeetCode Problem Object
     * @return Response indicating that result of method call
     */
    @PostMapping("/record")
    public ResponseEntity<String> recordLog(@RequestBody LCProblem problem) {
        logOrchestrator.record(problem);
        return ResponseEntity.ok("Logged successfully");
    }
}
