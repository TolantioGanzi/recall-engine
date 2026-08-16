package io.github.tolantioganz.recallengine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "user_problems")
public class UserProblem {
    @Id
    private int problemNumber;
    private int confidence;
    private int step;
    private int difficulty;
    private String title;
    private String pattern;
    private String failLog;
    private LocalDate dateAdded;
    private LocalDate nextRecall;

    public UserProblem(int difficulty, String title, String pattern,
                       int problemNumber, int confidence,
                       LocalDate nextRecall, String failLog) {
        this.difficulty = difficulty;
        this.title = title;
        this.pattern = pattern;
        this.problemNumber = problemNumber;
        this.confidence = confidence;
        this.dateAdded = LocalDate.now();
        this.nextRecall = nextRecall;
        this.failLog = failLog;
        this.step = 1;
    }
}
