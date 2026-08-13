package io.github.tolantioganz.recallengine.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString

public class LCProblem {
    private int id;
    private String title;
    private int difficulty;
    private List<String> topics;
    private boolean premium_only;
    private String similar_questions;

    public LCProblem(int id,
                     String title,
                     int difficulty,
                     List<String >topics,
                     boolean premium_only,
                     String similar_questions) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.topics = topics;
        this.premium_only = premium_only;
        this.similar_questions = similar_questions;
    }
}
