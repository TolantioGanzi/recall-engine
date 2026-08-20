package io.github.tolantioganz.recallengine.service;


import io.github.tolantioganz.recallengine.domain.UserProblem;
import io.github.tolantioganz.recallengine.repository.UserAttemptRepository;
import io.github.tolantioganz.recallengine.repository.UserProblemRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class DueService {
    private final UserProblemRepository problemRepo;
    private final UserAttemptRepository attemptRepo;

    public DueService(UserProblemRepository problemRepo, UserAttemptRepository attemptRepo) {
        this.problemRepo = problemRepo;
        this.attemptRepo = attemptRepo;
    }
    public List<UserProblem> getUserProblem() {
        List<UserProblem> dueProblems = problemRepo.getDueProblems(LocalDateTime.now());

        attemptRepo.findFirstByOrderByPriorityScoreDesc().ifPresent(attempt -> {
            System.out.println("Attempt number LeetCode Number -> : " + attempt.getProblemId());

        });
        return dueProblems;
    }
}
