package com.adventure.book.presentation.dto;

import com.adventure.book.domain.model.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotBlank String title,
        @NotBlank String author,
        String description,
        @NotNull Difficulty difficulty,
        String genre,
        String durationMinutes
) {
}
