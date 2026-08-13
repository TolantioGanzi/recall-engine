package io.github.tolantioganz.recallengine.repository;

import io.github.tolantioganz.recallengine.domain.LCProblem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.parsing.Problem;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Only class to speak to database
public class ProblemRepository {
    // Data source properties
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "1234";

    public LCProblem get(int id) {
        try {
            Connection connection = DriverManager.getConnection(URL,username,password);
            //The object used for executing a static SQL statement and returning the results it produces
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM problems WHERE id = ?");
            prepStatement.setInt(1, id);
            ResultSet resultSet = prepStatement.executeQuery();
            // Process the results from the query
            while(resultSet.next()) {
                int problemID = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String difficulty = resultSet.getString("difficulty");
                int diff = 0;
                if (difficulty == "medium") {
                    diff = 1;
                } else if(difficulty == "hard"){
                    diff = 2;
                }
                String topicsAsString = resultSet.getString("topics");
                List<String> topics = Arrays.stream(topicsAsString.split(", "))
                        .map(String::trim)
                        .toList();
                boolean premium_only = Boolean.parseBoolean(resultSet.getString("premium_only"));
                String similar_questions = resultSet.getString("similar_questions");
                return new LCService(problemID, title, diff, topics, premium_only, similar_questions);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void logNewProblem(Problem problem) {
        try {
            Connection connection = DriverManager.getConnection(URL, username, password);
            String sql = "INSERT INTO user_problems (id, title, difficulty, pattern, state, " +
                    "confidence, date_added, next_recall, fail_log, step) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStatement = connection.prepareStatement(sql);
            prepStatement.setInt(1, problem.getProblemNumber());
            prepStatement.setString(2, problem.getTitle());
            prepStatement.setInt(3, problem.getDifficulty());
            prepStatement.setString(4, problem.getPattern());
            prepStatement.setString(5, problem.getState());
            prepStatement.setInt(6, problem.getConfidence());
            prepStatement.setObject(7, problem.getDateAdded());
            prepStatement.setObject(8, problem.getNextRecall());
            prepStatement.setString(9, problem.getFailLog());
            prepStatement.setInt(10, problem.getStep());

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Getter
    @Setter
    private static class ReturnProblem {
        private String title;
        private String difficulty;
        private int number;

        public ReturnProblem(int number, String difficulty, String title) {
            this.number = number;
            this.difficulty = difficulty;
            this.title = title;
        }

    }
    public void getDueProblems(LocalTime currentDate) {
        List<ReturnProblem> returnProblems = new ArrayList<>();
        // Helper Data model class
        try {
            // Query database and use comparator, return all problems that are equal or less than (past due)
            Connection connection = DriverManager.getConnection(URL, username, password);
            String sql = "SELECT * from user_problems WHERE next_recall <= ?::date";
            PreparedStatement prepStatement = connection.prepareStatement(sql);

            prepStatement.setObject(1, LocalDate.now());
            ResultSet resultSet = prepStatement.executeQuery();

            while(resultSet.next()) {
                String title = resultSet.getString("title");
                String difficulty = resultSet.getString("difficulty");
                int number = resultSet.getInt("id");

                returnProblems.add(new ReturnProblem(number, difficulty, title));
            }
            for (ReturnProblem problem : returnProblems) {
                System.out.printf("""
            ┌─────────────────────────────────────┐
            │  #%-5d %-27s│
            │  Difficulty: %-23s│
            └─────────────────────────────────────┘
            """,
                        problem.getNumber(),
                        problem.getTitle(),
                        problem.getDifficulty());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void updateRecall(int id) {
        try {
            Connection connection = DriverManager.getConnection(URL, username, password);
            PreparedStatement prepStatement  = connection.prepareStatement("UPDATE user_problems SET step = step + 1 where id = ? RETURNING id, title, step");
            prepStatement.setInt(1, id);
            boolean resultSet = prepStatement.execute();

            if(resultSet) {
                try (ResultSet rs = prepStatement.getResultSet()) {
                    if(rs.next()) {
                        int probID = rs.getInt("id");
                        String title = rs.getString("title");
                        int step = rs.getInt("step");
                        System.out.println("Updated problem " + probID + " | " + title + " | " + step);

                        // Remove From database
                        if(step == 5 || step == 6) {
                            PreparedStatement prepDeleteStatement = connection.prepareStatement("DELETE FROM user_problems where id = ?");
                            prepDeleteStatement.setInt(1, id);

                            int rowsDeleted = prepDeleteStatement.executeUpdate();
                            if(rowsDeleted > 0) {
                                System.out.println("Problem recall completed and has been removed from recall ");
                            } else {
                                System.out.println("No Problem found with specific ID " + probID);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

