package com.adventure.book.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OptionRequest(
        @NotBlank String description,
        String tip,
        @NotNull Integer gotoId,
        ConsequenceRequest consequence
) {
}
