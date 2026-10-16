package com.adventure.book.presentation.dto;

public record OptionResponse(String description,
                             String tip,
                             Integer gotoId,
                             ConsequenceResponse consequence) {
}
