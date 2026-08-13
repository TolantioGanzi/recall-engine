package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.domain.Attempt;
import io.github.tolantioganz.recallengine.repository.AttemptRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;


import static java.lang.Math.clamp;

@Service
class PriorityScoreService {

    private final AttemptRepository attemptRepo;
    public PriorityScoreService(AttemptRepository attemptRepo){
        this.attemptRepo = attemptRepo;
    }

    // Difficulty
    private final double[] diffMultiplier       = new double[]{0.8, 1.0, 1.3};
    private final int[]    timeMap              = new int[]   {20, 30, 45   };
    // Weights
    private        final double W_PATTERN       = 0.35;
    private        final double W_IMPL          = 0.25;
    private        final double W_COMPLEXITY    = 0.20;
    private        final double W_DEBUG         = 0.20;

    private static final double HINT_PENALTY    = 0.15;
    private static final double MIN_PENALTY     = 0.40;

    private static final double HALFLIFE_BOMBED = 2.0;  // days
    private static final double HALFLIFE_DECENT = 7.0;
    private static final double HALFLIFE_CLEAN  = 14.0;

    // Refractor to a record for attempt.
    // Install a Task List or Notes for IntelliJ
    public void generateScore(Attempt attempt) {
        double rawScore      = computeRawScore     (attempt);
        double penaltyFactor = computePenaltyFactor(attempt.getHintsUsed());
        double timeFactor    = computeTimeFactor   (timeMap[attempt.getDifficulty()], attempt.getActualTime());
        System.out.println("medium time " + timeMap[attempt.getDifficulty()]);
        double performance = rawScore * penaltyFactor * timeFactor;

        long   daysSince   = attemptRepo.getDaysSinceLastAttempt(attempt);
        double halfLife    = computeHalfLife (performance);
        double decayBoost  = computeDecayBost(daysSince, halfLife);                                                        // days since attempt

        double need        = 1.0 - performance;
        double diffNorm    = diffMultiplier[attempt.getDifficulty()] / 1.3;
        double priority    = 1.0 + 99.0 * (need * decayBoost * diffNorm);

        attempt.setPriorityScore(clamp(priority, 1.0, 100.0));

    }
    private double computeRawScore(Attempt attempt) {
        return W_PATTERN       * (attempt.getPatternScore() / 10.0)
                + W_IMPL       * (attempt.getImplementationScore() / 10.0)
                + W_DEBUG      * (attempt.getDebugScore() / 10.0)
                + W_COMPLEXITY * (attempt.getComplexityScore() / 10.0);
    }
    private double computePenaltyFactor(int hintsUsed) {
        double penalty = 1.0 - (hintsUsed * HINT_PENALTY);
        return Math.max(MIN_PENALTY, penalty);
    }
    private double computeHalfLife(double performance) {
        if(performance < 0.4) return HALFLIFE_BOMBED; // failed
        else if(performance < 0.7) return HALFLIFE_DECENT; // Took time, but got it to work, needed hints and guidance
        else return HALFLIFE_CLEAN; // Solved it easy
    }
    private double computeDecayBost(long daysSince, double halfLife) {
        System.out.println("Days Since Test " + daysSince);
        long cappedDays = Math.min(daysSince, 30);
        return Math.exp((double) cappedDays / halfLife);
    }
    private double computeTimeFactor(double expected, double actual) {
        if(expected <= 0 || actual <= 0) return 1.0;// prevent divide by zero error
        double ratio = expected / actual;
        return clamp(ratio, 0.5, 1.2);
    }
}
