package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.repository.ProblemRepository;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private final ScheduleService scheduleService;
    private final ProblemRepository problemRepository;

    public LogService (ScheduleService scheduleService,
                       ProblemRepository problemRepository) {
        this.scheduleService = scheduleService;
        this.problemRepository = problemRepository;
    }
}
