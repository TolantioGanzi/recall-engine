package io.github.tolantioganz.recallengine.dto;


public record DiagnosisRequest(
        int problemID,
        int patternScore,
        int implementationScore,
        int complexityScore,
        int debugScore,
        int hintsUsed,
        int result,
        int actualTime,
        String pattern,
        String optimalSolution
) {
}
