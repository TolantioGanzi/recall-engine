package io.github.tolantioganz.recallengine.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter

public class Problem {
    private String title;
    private int difficulty;
    private String pattern;
    private String state; // NEW / LEARNING / MASTERED
    private String failLog;

    private int problemNumber;
    private int confidence;
    private int step;

    private LocalDate dateAdded;
    private LocalDate nextRecall;


    public Problem(int difficulty, String title, String pattern,
                   int problemNumber, int confidence,
                   LocalDate nextRecall, String failLog) {
        this.difficulty = difficulty;
        this.title = title;
        this.pattern = pattern;
        this.problemNumber = problemNumber;
        this.confidence = confidence;
        this.dateAdded = LocalDate.now();
        this.nextRecall = nextRecall;
        this.state = "NEW";
        this.failLog = failLog;
        this.step = 1;
    }
}
