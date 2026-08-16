package io.github.tolantioganz.recallengine.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private int difficulty;
    private boolean premium_only;
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> topics;
    private String similar_questions;
    private String title;
}
