package io.github.tolantioganz.recallengine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "problems")

public class LCProblem {
    @Id
    private int id;
    private String title;
    private int difficulty;
    private List<String> topics;
    private boolean premium_only;
    private String similar_questions;

}
