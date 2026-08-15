package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.domain.UserAttempt;
import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import io.github.tolantioganz.recallengine.repository.UserAttemptRepository;
import org.springframework.stereotype.Service;


import static java.lang.Math.clamp;

@Service
class ScoringService {

    private final UserAttemptRepository attemptRepo;
    public ScoringService(UserAttemptRepository attemptRepo){
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

    public double generateScore(DiagnosisRequest diagnosisRequest, long daysSince) {
        double rawScore      = computeRawScore(diagnosisRequest);
        double penaltyFactor = computePenaltyFactor(diagnosisRequest.hintsUsed());
        double timeFactor    = 0.7; // Default for now
        double performance = rawScore * penaltyFactor * timeFactor;

        double halfLife    = computeHalfLife (performance);
        double decayBoost  = computeDecayBost(daysSince, halfLife);                                                        // days since attempt

        double need        = 1.0 - performance;
        double diffNorm    =  1.5; //diffMultiplier[attempt.getDifficulty()] / 1.3;
        double priority    = 1.0 + 99.0 * (need * decayBoost * diffNorm);

        return clamp(priority, 1.0, 100.0);

    }
    private double computeRawScore(DiagnosisRequest diagnosisRequest) {
        return W_PATTERN       * (diagnosisRequest.patternScore() / 10.0)
                + W_IMPL       * (diagnosisRequest.implementationScore() / 10.0)
                + W_DEBUG      * (diagnosisRequest.debugScore() / 10.0)
                + W_COMPLEXITY * (diagnosisRequest.complexityScore() / 10.0);
    }
    private double computePenaltyFactor(int hintsUsed) {
        double penalty = 1.0 - (hintsUsed * HINT_PENALTY);
        return Math.max(MIN_PENALTY, penalty);
    }
    private double computeHalfLife(double performance) {
        if(performance < 0.4) return HALFLIFE_BOMBED;
        else if(performance < 0.7) return HALFLIFE_DECENT;
        else return HALFLIFE_CLEAN;
    }
    private double computeDecayBost(long daysSince, double halfLife) {
        System.out.println("Days Since Test " + daysSince);
        long cappedDays = Math.min(daysSince, 30);
        return Math.exp((double) cappedDays / halfLife);
    }
    private double computeTimeFactor(double expected, double actual) {
        if(expected <= 0 || actual <= 0) return 1.0;
        double ratio = expected / actual;
        return clamp(ratio, 0.5, 1.2);
    }
}
