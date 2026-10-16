package com.adventure.game.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record GameSessionRequest(@NotNull UUID bookId,
                                 @NotNull Integer currentSectionId,
                                 @NotBlank String username,
                                 @NotNull Integer healthPoints) {
}
