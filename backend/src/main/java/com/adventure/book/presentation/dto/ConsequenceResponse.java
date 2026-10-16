package com.adventure.book.presentation.dto;

import com.adventure.book.domain.model.ConsequenceType;

public record ConsequenceResponse(String value, String text, ConsequenceType type) {
}
