package com.adventure.book.application.command;

import com.adventure.book.domain.model.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookCommand(String title,
                                String author,
                                String description,
                                Difficulty difficulty,
                                String genre,
                                String durationMinutes) {
}
