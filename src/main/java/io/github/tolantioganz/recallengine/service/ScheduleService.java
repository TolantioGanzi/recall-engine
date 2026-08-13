package io.github.tolantioganz.recallengine.service;

import org.example.Domain.Problem;

import java.time.LocalDate;

public class ScheduleService {
    private final int[] standardRecalls = new int[]{1, 3, 5, 7, 14};
    private final int[] advancedRecall = new int[]{1, 3, 5, 7, 14, 30};
    public ScheduleService() {

    }
    public void initStandardRecall(Problem problem) {
        int recallIndex = standardRecalls[0];
        LocalDate firstRecall =  LocalDate.now().plusDays(recallIndex);
        problem.setNextRecall(firstRecall);
    }
    public void initAdvancedRecall(Problem problem) {}
}
