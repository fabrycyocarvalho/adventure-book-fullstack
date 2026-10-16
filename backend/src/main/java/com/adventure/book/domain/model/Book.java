package com.adventure.book.domain.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public record Book(
        UUID id,
        String title,
        String author,
        String description,
        Difficulty difficulty,
        String genre,
        String durationMinutes,
        Set<Section> sections,
        List<String> tags
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Book {
        if (id == null) id = UUID.randomUUID();
        sections = (sections == null) ? new HashSet<>() : new HashSet<>(sections);
        tags = (tags == null) ? new ArrayList<>() : new ArrayList<>(tags);
    }

    public static Book create(
            UUID id,
            String title,
            String author,
            String description,
            Difficulty difficulty,
            String genre,
            String durationMinutes
    ) {
        return new Book(id, title, author, description, difficulty, genre, durationMinutes, null, null);
    }

    public Section addSection(Integer id, String text, SectionType type) {
        Section section = new Section(id, text, type, null);
        sections.add(section);
        return section;
    }

    public Integer chaptersCount() {
        return sections.size();
    }
}