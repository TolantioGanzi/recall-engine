package io.github.tolantioganz.recallengine.service;


import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.repository.ProblemRepository;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    private final LogService logService;
    private final ProblemRepository problemRepository;

    public ProblemService(LogService logService, ProblemRepository problemRepository) {
        this.logService = logService;
        this.problemRepository = problemRepository;
    }

    // Retrieve LeetCode problem from main Database table
    public LCProblem get(int problemNumber) {
        return problemRepository.getProblemByID(problemNumber);
    }
}
