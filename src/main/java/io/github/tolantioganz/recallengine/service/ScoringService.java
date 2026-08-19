package io.github.tolantioganz.recallengine.service;

import io.github.tolantioganz.recallengine.dto.DiagnosisRequest;
import org.springframework.stereotype.Service;

import static java.lang.Math.clamp;

@Service
public class ScoringService {

    private static final double W_PATTERN    = 0.35;
    private static final double W_IMPL       = 0.25;
    private static final double W_COMPLEXITY = 0.20;
    private static final double W_DEBUG      = 0.20;

    private static final double HINT_PENALTY = 0.15;
    private static final double MIN_PENALTY  = 0.40;

    private static final double HALFLIFE_BOMBED = 2.0;  // days
    private static final double HALFLIFE_DECENT = 7.0;
    private static final double HALFLIFE_CLEAN  = 14.0;

    public double generateScore(DiagnosisRequest request, long daysSince) {
        if (request == null) throw new IllegalArgumentException("Request cannot be null");
        int hints = Math.max(0, request.hintsUsed());
        long safeDays = Math.max(0, Math.min(daysSince, 30));

        double rawScore      = computeRawScore(request);
        double penaltyFactor = computePenaltyFactor(hints);
        double expectedTime  = getExpectedTime(request.difficulty());
        double timeFactor    = computeTimeFactor(expectedTime, request.actualTime());

        // Performance scaled [0.0, 1.0]
        double performance   = Math.min(1.0, rawScore * penaltyFactor * timeFactor);

        double halfLife        = computeHalfLife(performance);
        double forgottenFraction = computeForgottenFraction(safeDays, halfLife); // Bounded [0.0, 1.0)

        double difficultyWeight = getDifficultyWeight(request.difficulty());

        // Combine performance loss (need) with forgetting decay and difficulty
        double need = 1.0 - (performance * (1.0 - forgottenFraction));
        double priority = 1.0 + 99.0 * (need * difficultyWeight);

        return clamp(priority, 1.0, 100.0);
    }

    private double computeRawScore(DiagnosisRequest req) {
        double p = clamp(req.patternScore(), 0, 10) / 10.0;
        double i = clamp(req.implementationScore(), 0, 10) / 10.0;
        double d = clamp(req.debugScore(), 0, 10) / 10.0;
        double c = clamp(req.complexityScore(), 0, 10) / 10.0;
        return (W_PATTERN * p) + (W_IMPL * i) + (W_DEBUG * d) + (W_COMPLEXITY * c);
    }

    private double computePenaltyFactor(int hintsUsed) {
        return Math.max(MIN_PENALTY, 1.0 - (hintsUsed * HINT_PENALTY));
    }

    private double computeHalfLife(double performance) {
        if (performance < 0.4) return HALFLIFE_BOMBED;
        if (performance < 0.7) return HALFLIFE_DECENT;
        return HALFLIFE_CLEAN;
    }

    /**
     * Ebbinghaus Forgotten Fraction: 1 - 2^(-t / HalfLife)
     * At t = 0 -> 0.0 forgotten
     * At t = HalfLife -> 0.5 (50% forgotten)
     */
    private double computeForgottenFraction(long daysSince, double halfLife) {
        return 1.0 - Math.pow(2.0, -(double) daysSince / halfLife);
    }

    private double computeTimeFactor(double expected, double actual) {
        if (expected <= 0 || actual <= 0) return 1.0;
        return clamp(expected / actual, 0.5, 1.2);
    }

    private double getExpectedTime(String difficulty) {
        if (difficulty == null) return 30.0;
        return switch (difficulty.toLowerCase()) {
            case "easy" -> 20.0;
            case "hard" -> 45.0;
            default -> 30.0; // Medium
        };
    }

    private double getDifficultyWeight(String difficulty) {
        if (difficulty == null) return 1.0;
        return switch (difficulty.toLowerCase()) {
            case "easy" -> 0.8;
            case "hard" -> 1.3;
            default -> 1.0; // Medium
        };
    }
}