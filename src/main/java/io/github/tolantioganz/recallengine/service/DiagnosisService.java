package io.github.tolantioganz.recallengine.service;

import lombok.NoArgsConstructor;
import org.example.Domain.Attempt;
import org.example.Domain.LCService;

import java.time.LocalDate;
import java.util.Scanner;

@NoArgsConstructor

public class DiagnosisService {


    public Attempt diagnoseProblem(LCService problem) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Diagnosing: " + problem.getTitle() + " ===");

        System.out.print("Pattern Score (0-10): ");
        int patternScore = scanner.nextInt();

        System.out.print("Implementation Score (0-10): ");
        int implementationScore = scanner.nextInt();

        System.out.print("Complexity Score (0-10): ");
        int complexityScore = scanner.nextInt();

        System.out.print("Debug Score (0-10): ");
        int debugScore = scanner.nextInt();

        System.out.print("Hints Used: ");
        int hintsUsed = scanner.nextInt();

        System.out.print("Result (0 = Fail, 1 = Pass): ");
        int result = scanner.nextInt();

        System.out.print("Actual Time (minutes): ");
        int actualTime = scanner.nextInt();

        scanner.nextLine(); // flush newline

        System.out.print("Optimal Solution (one sentence): ");
        String optimalSolution = scanner.nextLine();

        System.out.print("Pattern: ");
        String pattern = scanner.nextLine();


        LocalDate attemptDate = LocalDate.now();
        long daysSinceAttempt = 0;

        int priorityScore = patternScore + implementationScore + complexityScore + debugScore;

        return new Attempt(
                problem.getId(),
                priorityScore,
                patternScore,
                implementationScore,
                complexityScore,
                debugScore,
                hintsUsed,
                result,
                problem.getDifficulty(),
                actualTime,
                pattern,
                optimalSolution,
                daysSinceAttempt,
                attemptDate
        );
    }
}
