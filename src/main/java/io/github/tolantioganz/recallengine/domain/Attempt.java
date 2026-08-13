package io.github.tolantioganz.recallengine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
public class Attempt {
    // Identifier
    private int problemID;
    // Scoring
    private double priorityScore;
    private int patternScore;
    private int implementationScore;
    private int complexityScore;
    private int debugScore;
    private int hintsUsed;

    // Problem meta-data
    private int result; // 0 - Fail | 1 - Pass
    private int difficulty; // 0 - easy | 1 - medium | 2 - Hard
    private int actualTime; // minutes - round to nearest minute
    private String pattern; // Sliding Window | Stack | Queue
    private String optimalSolution; // 1 sentence Short
    long daysSinceAttempt;

    private LocalDate attemptDate;

    // method to get days since attempt
    public long getDaysSinceLastAttempt() {
        LocalDate currentAttempt = LocalDate.now();
        return ChronoUnit.DAYS.between(this.attemptDate, currentAttempt);
    }
}
