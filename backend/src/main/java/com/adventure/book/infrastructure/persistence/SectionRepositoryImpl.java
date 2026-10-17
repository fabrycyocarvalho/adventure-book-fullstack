package com.adventure.book.infrastructure.persistence;

import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.model.Section;
import com.adventure.book.domain.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class SectionRepositoryImpl implements SectionRepository {

    private final DB db;
    private final HTreeMap<UUID, Book> books;

    @Override
    public Integer getNextSectionId(UUID bookId) {
        return getSectionStream(bookId)
                .map(Section::id)
                .mapToInt(id -> id)
                .max()
                .orElse(1) + 1;
    }

    private @NonNull Stream<Section> getSectionStream(UUID bookId) {
        return books.values().stream()
                .filter(book -> book.id().equals(bookId))
                .flatMap(book -> book.sections().stream());
    }

    @Override
    public Optional<Section> findById(Integer sectionId, UUID bookId) {
        return getSectionStream(bookId)
                .filter(section -> section.id().equals(sectionId))
                .findFirst();
    }

    @Override
    public void save(Section update, UUID bookId) {
        findBookBySectionId(update.id(), bookId).ifPresent(book -> {
            book.sections().remove(update);
            book.sections().add(update);
            books.put(book.id(), book);
            db.commit();
        });
    }

    @Override
    public void delete(Section section, UUID bookId) {
        findBookBySectionId(section.id(), bookId).ifPresent(book -> {
            book.sections().remove(section);
            books.put(book.id(), book);
            db.commit();
        });
    }

    private Optional<Book> findBookBySectionId(Integer sectionId, UUID bookId) {
        return books.values().stream()
                .filter(book -> book.id().equals(bookId))
                .filter(book -> book
                        .sections()
                        .stream()
                        .anyMatch(section -> section.id().equals(sectionId)))
                .findFirst();
    }
}
