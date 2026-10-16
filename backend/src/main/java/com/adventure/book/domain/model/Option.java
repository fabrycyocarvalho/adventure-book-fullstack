package com.adventure.book.domain.model;

import java.io.Serial;
import java.io.Serializable;

/*
    description: string;
    tip?: string;
    gotoId: number;
    consequence?: Consequence;
* */
public record Option(
        String description,
        String tip,
        Integer gotoId,
        Consequence consequence
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
