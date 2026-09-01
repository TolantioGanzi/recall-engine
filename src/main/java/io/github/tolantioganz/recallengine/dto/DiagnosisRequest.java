package io.github.tolantioganz.recallengine.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record DiagnosisRequest(
        @NotEmpty(message = "Problem ID cannot be blank")
        int problemId,
        @NotEmpty(message = "Pattern Score cannot be blank")
        int patternScore,
        @NotEmpty(message = "Implementation Score cannot be blank")
        int implementationScore,
        @NotEmpty(message = "Complexity Score cannot be blank")
        int complexityScore,
        @NotEmpty(message = "Problem ID cannot be blank")
        int debugScore,
        int hintsUsed,
        int result,
        int actualTime,
        String pattern,
        String optimalSolution,
        @NotEmpty(message = "Difficulty cannot be blank")
        String difficulty
) {
}
