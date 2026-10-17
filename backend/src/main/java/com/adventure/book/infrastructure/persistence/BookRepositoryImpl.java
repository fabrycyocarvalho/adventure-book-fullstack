package com.adventure.book.infrastructure.persistence;

import com.adventure.book.domain.exception.BookNotFoundException;
import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.model.Difficulty;
import com.adventure.book.domain.model.Section;
import com.adventure.book.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.mapdb.DB;
import org.mapdb.HTreeMap;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final DB db;
    private final HTreeMap<UUID, Book> books;

    @Override
    public Book save(Book book) {
        books.put(book.id(), book);
        db.commit();
        return book;
    }

    @Override
    public List<Book> findAll(String search, List<Difficulty> difficulties, List<String> genres) {
        String term = search == null ? "" : search.trim().toLowerCase(Locale.ROOT);
        return books.values().stream()
                .filter(b -> (difficulties == null || difficulties.isEmpty()) || difficulties.contains(b.difficulty()))
                .filter(b -> applyGenreFilter(genres, b))
                .filter(b -> term.isEmpty()
                        || contains(b.title(), term)
                        || contains(b.author(), term))
                .toList();
    }

    private static boolean applyGenreFilter(List<String> genres, Book b) {
        if ((Objects.isNull(genres) || genres.isEmpty())) {
            return true;
        }
        String genre = b.genre();
        return Objects.nonNull(genre) && genres
                .stream()
                .map(String::toLowerCase)
                .toList()
                .contains(genre.toLowerCase(Locale.ROOT));
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Optional<Book> findByTitleAndAuthorIgnoreCase(String title, String author) {
        if (title == null || author == null) {
            return Optional.empty();
        }
        return books.values().stream()
                .filter(b -> title.equalsIgnoreCase(b.title())
                        && author.equalsIgnoreCase(b.author()))
                .findFirst();
    }

    @Override
    public List<Section> findSectionsByBookId(UUID bookId) {
        Book book = books.get(bookId);

        if (book == null) {
            throw new BookNotFoundException(bookId.toString());
        }
        return List.copyOf(book.sections());
    }

    @Override
    public void delete(Book book) {
        books.remove(book.id());
        db.commit();
    }

    @Override
    public List<String> findAllGenres() {
        return List.copyOf(books.values().stream().map(Book::genre).filter(Objects::nonNull).toList());
    }

    private static boolean contains(String value, String term) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(term);
    }
}
