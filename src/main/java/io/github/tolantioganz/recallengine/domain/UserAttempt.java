package io.github.tolantioganz.recallengine.domain;

import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "attempts")
public class UserAttempt {
    // Identifier
    @Id
    private int problemID;
    private double priorityScore;
    private int patternScore;
    private int implementationScore;
    private int complexityScore;
    private int debugScore;
    private int hintsUsed;

    private long daysSinceAttempt;
    private int result; // 0 - Fail | 1 - Pass
    private int difficulty; // 0 - easy | 1 - medium | 2 - Hard
    private int actualTime; // minutes - round to nearest minute
    private String pattern; // Sliding Window | Stack | Queue
    private String optimalSolution; // 1 sentence Short
    private LocalDate attemptDate;

    public UserAttempt(DiagnosisRequest diagnosisRequest, double score) {
        this.problemID = diagnosisRequest.problemID();
        this.patternScore = diagnosisRequest.patternScore();
        this.implementationScore = diagnosisRequest.implementationScore();
        this.complexityScore = diagnosisRequest.complexityScore();
        this.debugScore = diagnosisRequest.debugScore();
        this.hintsUsed = diagnosisRequest.hintsUsed();
        this.result = diagnosisRequest.result();
        this.priorityScore = score;
        this.pattern = diagnosisRequest.pattern();
        this.optimalSolution = diagnosisRequest.optimalSolution();
        this.attemptDate = LocalDate.now();
        this.actualTime = diagnosisRequest.actualTime();
        // Place-Holders : Change for testing
        this.difficulty = 1; // CHANGE
        this.daysSinceAttempt = 5; // CHANGE
    }
    // method to get days since attempt
    public long getDaysSinceLastAttempt() {
        LocalDate currentAttempt = LocalDate.now();
        return ChronoUnit.DAYS.between(this.attemptDate, currentAttempt);
    }
}
