package com.adventure.book.domain.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public record Section(
        Integer id,
        String text,
        SectionType type,
        Set<Option> options
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public Section {
        options = (options == null) ? new HashSet<>() : new HashSet<>(options);
    }

    public void addOption(String description, String tip, Integer gotoId, Consequence consequence) {
        options.add(new Option(description, tip, gotoId, consequence));
    }
}
