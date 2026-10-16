package com.adventure.book.domain.repository;

import com.adventure.book.domain.model.Section;

import java.util.Optional;

public interface SectionRepository {

    Integer getNextSectionId();
    Optional<Section> findById(Integer sectionId);

    void save(Section section);

    void delete(Section section);
}
