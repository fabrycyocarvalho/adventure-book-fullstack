package com.adventure.book.domain.repository;

import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.model.Section;

import java.util.Optional;
import java.util.UUID;

public interface SectionRepository {

    Integer getNextSectionId(UUID bookId);

    Optional<Section> findById(Integer sectionId, UUID bookId);

    void save(Section section, UUID bookId);

    void delete(Section section, UUID bookId);
}
