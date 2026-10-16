package com.adventure.book.domain.repository;

import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.model.Difficulty;
import com.adventure.book.domain.model.Section;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    Book save(Book book);
    List<Book> findAll(String search, List<Difficulty> difficulty, List<String> genres);
    Optional<Book> findById(UUID id);
    Optional<Book> findByTitleAndAuthorIgnoreCase(@NotBlank String title, @NotBlank String author);
    List<Section> findSectionsByBookId(UUID bookId);
    void delete(Book book);
    List<String> findAllGenres();
}
