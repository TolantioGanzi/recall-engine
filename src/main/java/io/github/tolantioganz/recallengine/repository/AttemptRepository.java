package io.github.tolantioganz.recallengine.repository;

import lombok.NoArgsConstructor;
import org.example.Domain.Attempt;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor

public class AttemptRepository {
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "1234";
    public void logAttempt(Attempt attempt) {
        String sql = "INSERT INTO attempts (problem_id, priority_score, pattern_score, " +
                "implementation_score, complexity_score, debug_score, hints_used, " +
                "result, difficulty, actual_time, pattern, optimal_solution, attempt_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, username, password);
             PreparedStatement prepStatement = connection.prepareStatement(sql)) {

            prepStatement.setInt(1, attempt.getProblemID());
            prepStatement.setDouble(2, attempt.getPriorityScore());
            prepStatement.setInt(3, attempt.getPatternScore());
            prepStatement.setInt(4, attempt.getImplementationScore());
            prepStatement.setInt(5, attempt.getComplexityScore());
            prepStatement.setInt(6, attempt.getDebugScore());
            prepStatement.setInt(7, attempt.getHintsUsed());
            prepStatement.setInt(8, attempt.getResult());
            prepStatement.setInt(9, attempt.getDifficulty());
            prepStatement.setInt(10, attempt.getActualTime());
            prepStatement.setString(11, attempt.getPattern());
            prepStatement.setString(12, attempt.getOptimalSolution());
            prepStatement.setObject(13, attempt.getAttemptDate());

            prepStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Attempt getPriorityProblem() {
        try {
            Connection connection = DriverManager.getConnection(URL, username, password);
            PreparedStatement prepStatement  = connection.prepareStatement("SELECT * FROM attempts ORDER BY priority_score DESC LIMIT 1");
            ResultSet resultSet = prepStatement.executeQuery();

            while(resultSet.next()) {
                int attemptID = resultSet.getInt("attempt_id");
                int problemID = resultSet.getInt("problem_id");
                int priorityScore = resultSet.getInt("priority_score");
                int patternScore = resultSet.getInt("pattern_score");
                int implementationScore = resultSet.getInt("implementation_score");
                int complexityScore = resultSet.getInt("complexity_score");
                int debugScore = resultSet.getInt("debug_score");
                int hintsUsed = resultSet.getInt("hints_used");
                int result = resultSet.getInt("result");
                int difficulty = resultSet.getInt("difficulty");
                int actualTime = resultSet.getInt("actual_time");
                String pattern = resultSet.getString("pattern");
                String optimalSolution = resultSet.getString("optimal_solution");
                LocalDate attemptDate = resultSet.getObject("attempt_date", LocalDate.class);

                long daysSinceAttempt = ChronoUnit.DAYS.between(attemptDate, LocalDate.now());
                return new Attempt(problemID, priorityScore, patternScore, implementationScore, complexityScore, debugScore, hintsUsed, result, difficulty, actualTime, pattern, optimalSolution, daysSinceAttempt, attemptDate);


            }
            System.out.println("Attempt Retrieval Failed. ");
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void updateScore(Attempt attempt) {

    }
    public long getDaysSinceLastAttempt(Attempt attempt) {
        try {
            int problemID = attempt.getProblemID();
            Connection connection = DriverManager.getConnection(URL, username, password);
            PreparedStatement prepStatement  = connection.prepareStatement("SELECT * FROM attempts WHERE problem_id = ?"
            , ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            prepStatement.setInt(1, problemID);
            ResultSet resultSet = prepStatement.executeQuery();

            if(resultSet.absolute(-1)) {
                LocalDate lastAttemptDate = resultSet.getObject("attempt_date", LocalDate.class);
                return ChronoUnit.DAYS.between(lastAttemptDate, LocalDate.now());
            }
            // New Problem so zero days
            return 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
