package io.github.tolantioganz.recallengine.service;



import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class ScheduleService {
    private final int[] standardRecalls = new int[]{1, 3, 5, 7, 14};
    private final int[] advancedRecall = new int[]{1, 3, 5, 7, 14, 30};
    public ScheduleService() {

    }
    public LocalDate getStandardRecall() {
        int recallIndex = standardRecalls[0];
        return LocalDate.now().plusDays(recallIndex);
    }
    public void initAdvancedRecall(Problem problem) {}
}
