package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.domain.UserProblem;
import io.github.tolantioganz.recallengine.repository.LCProblemRepository;
import io.github.tolantioganz.recallengine.repository.UserProblemRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class LogOrchestrator {
    private final UserProblemRepository userProblemRepo;
    private final LCProblemRepository lcRepo;
    private final ScheduleService scheduleService;
    private final ProblemService problemService;

    public LogOrchestrator (UserProblemRepository userProblemRepo,
                           ScheduleService scheduleService,
                           ProblemService problemService,
                            LCProblemRepository lcRepo) {
        this.userProblemRepo = userProblemRepo;
        this.scheduleService = scheduleService;
        this.problemService = problemService;
        this.lcRepo = lcRepo;
    }
    public LCProblem getOfficialProblem(int problemID) {
        return lcRepo.findById(problemID).orElse(null);
    }
    public UserProblem record(int problemID,
                              int confidence,
                              String pattern,
                              String failLog) {
        LCProblem lcProblem = lcRepo.findById(problemID).orElseThrow(() ->
                new IllegalArgumentException("Problem not found " + problemID));

        LocalDate nextRecallDate = scheduleService.getStandardRecall();

        UserProblem userProblem = new UserProblem(
                lcProblem.getDifficulty(),
                lcProblem.getTitle(),
                pattern,
                problemID,
                confidence,
                nextRecallDate,
                failLog
        );

        return userProblemRepo.save(userProblem);
    }
}
