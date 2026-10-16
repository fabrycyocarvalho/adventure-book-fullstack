package com.adventure.book.presentation.dto;

import com.adventure.book.domain.model.Difficulty;

import java.util.List;
import java.util.UUID;

public record BookResponse(UUID id,
                           String title,
                           String author,
                           String description,
                           Difficulty difficulty,
                           String genre,
                           String durationMinutes,
                           List<String> tags,
                           Integer chaptersCount) {
}
