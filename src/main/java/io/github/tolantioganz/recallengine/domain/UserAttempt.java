package io.github.tolantioganz.recallengine.domain;

import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attempts")
public class UserAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "problem_id")
    private int problemID;

    @Column(name = "priority_score")
    private double priorityScore;

    @Column(name = "pattern_score")
    private int patternScore;

    @Column(name = "implementation_score")
    private int implementationScore;

    @Column(name = "complexity_score")
    private int complexityScore;

    @Column(name = "debug_score")
    private int debugScore;

    @Column(name = "hints_used")
    private int hintsUsed;

    @Column(name = "days_since_attempt")
    private long daysSinceAttempt;

    @Column(name = "result")
    private int result;

    @Column(name = "difficulty")
    private int difficulty;

    @Column(name = "actual_time")
    private int actualTime;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "optimal_solution")
    private String optimalSolution;

    @Column(name = "attempt_date")
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
        this.daysSinceAttempt = getDaysSinceAttempt();
    }
    // method to get days since attempt
    public long getDaysSinceAttempt() {
        if(this.attemptDate == null) return 0;
        return ChronoUnit.DAYS.between(this.attemptDate, LocalDate.now());
    }
}
