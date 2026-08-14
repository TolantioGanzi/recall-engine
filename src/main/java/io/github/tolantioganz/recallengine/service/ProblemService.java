package io.github.tolantioganz.recallengine.service;


import io.github.tolantioganz.recallengine.domain.LCProblem;
import io.github.tolantioganz.recallengine.repository.UserProblemRepository;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    private final LogService logService;
    private final UserProblemRepository problemRepository;

    public ProblemService(LogService logService, UserProblemRepository problemRepository) {
        this.logService = logService;
        this.problemRepository = problemRepository;
    }

    // Retrieve LeetCode problem from main Database table
    public LCProblem get(int problemNumber) {
        return problemRepository.getProblemByID(problemNumber);
    }
}
