package com.adventure.book.presentation.dto;

import com.adventure.book.domain.model.SectionType;

import java.util.Set;

public record SectionResponse(Integer id,
                              String text,
                              SectionType type,
                              Set<OptionResponse> options
) {
}
