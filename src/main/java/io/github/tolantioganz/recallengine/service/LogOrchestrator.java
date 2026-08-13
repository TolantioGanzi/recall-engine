package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.repository.ProblemRepository;
import org.springframework.stereotype.Service;


@Service
public class LogOrchestrator {
    private final LogService logService;
    private final ProblemRepository problemRepo;
    private final ScheduleService scheduleService;
    private final ProblemService problemService;

    public LogOrchestrator (LogService logService,
                           ProblemRepository problemRepo,
                           ScheduleService scheduleService,
                           ProblemService problemService) {
        this.logService = logService;
        this.problemRepo = problemRepo;
        this.scheduleService = scheduleService;
        this.problemService = problemService;
    }
    public LCProblem get(int problemID) {
        return problemRepo.get(problemID);
    }
    public void record(LCProblem problem) {

    }
}
