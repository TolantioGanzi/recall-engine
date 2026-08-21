package io.github.tolantioganz.recallengine.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record DiagnosisRequest(
        @NotEmpty(message = "Problem ID cannot be blank")
        int problemID,
        int patternScore,
        int implementationScore,
        int complexityScore,
        int debugScore,
        int hintsUsed,
        int result,
        int actualTime,
        String pattern,
        String optimalSolution,
        String difficulty
) {
}
