package com.adventure.book.domain.model;

import java.io.Serial;
import java.io.Serializable;

/*
    type: ConsequenceType;
    value: string;
    text: string;
 */
public record Consequence(String value, String text, ConsequenceType type) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
