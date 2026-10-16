package com.adventure.book.presentation.dto;

import com.adventure.book.domain.model.SectionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SectionRequest(
        @NotBlank String text,
        @NotNull SectionType type
//        @NotEmpty Set<OptionRequest> options
) {
}
