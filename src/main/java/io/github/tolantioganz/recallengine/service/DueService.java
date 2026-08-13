package io.github.tolantioganz.recallengine.service;

import org.example.Domain.Attempt;
import org.example.Repository.AttemptRepository;
import org.example.Repository.ProblemRepository;

import java.time.LocalTime;

public class DueService {
    private  ProblemRepository problemRepo;
    private AttemptRepository attemptRepo;

    public DueService(ProblemRepository problemRepo, AttemptRepository attemptRepo) {
        this.problemRepo = problemRepo;
        this.attemptRepo = attemptRepo;
    }

    public void getDueProblems() {
        // Query database using topic today's date
        problemRepo.getDueProblems(LocalTime.now());
        // Query attempt repo and get highest priority problem
        Attempt attempt = attemptRepo.getPriorityProblem();
        System.out.println("Attempt Number LeetCode Number -> : " + attempt.getProblemID());
        attemptRepo.updateScore(attempt);

        // When is a problem mastered? when to move to mastered -
        // Graph problems mastered - recall n times perfectly with BFS / DFS / Union-Find
        // So for Problem - recall n times with score above 9.0 for all approaches
    }
}
