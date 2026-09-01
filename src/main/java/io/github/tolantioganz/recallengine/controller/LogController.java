package io.github.tolantioganz.recallengine.controller;

import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.domain.UserProblem;
import io.github.tolantioganz.recallengine.dto.RecordRequest;
import io.github.tolantioganz.recallengine.service.LogOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
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
    @GetMapping("/validate")
    public ResponseEntity<LCProblem> validateProblem(@RequestParam("id") int id) {
        LCProblem lcProblem = logOrchestrator.getOfficialProblem(id);
        if(lcProblem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lcProblem);
    }

    /**
     *
     * @param request LeetCode Problem Object
     * @return Response indicating that result of method call
     */
    @PostMapping("/record")
    public ResponseEntity<String> recordLog(@RequestBody RecordRequest request) {
        UserProblem savedUserProblem = logOrchestrator.record(
                request.getProblemID(),
                request.getConfidence(),
                request.getPattern(),
                request.getFailLog()
        );
        return ResponseEntity.ok("Logged successfully \n" + savedUserProblem);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserProblem>> listLogged() {
        return ResponseEntity.ok(logOrchestrator.getAllLogged());
    }
}
