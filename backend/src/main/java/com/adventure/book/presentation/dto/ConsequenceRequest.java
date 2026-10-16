package com.adventure.book.presentation.dto;

import com.adventure.book.domain.model.ConsequenceType;

public record ConsequenceRequest(String value, String text, ConsequenceType type) {
}
