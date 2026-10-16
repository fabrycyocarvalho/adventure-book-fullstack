package com.adventure.book.application.usecase;

import com.adventure.book.application.command.CreateBookCommand;
import com.adventure.book.domain.exception.BookAlreadyExistsException;
import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBookUseCase {
    private final BookRepository bookRepository;

    public Book execute(CreateBookCommand command) {

        bookRepository.findByTitleAndAuthorIgnoreCase(command.title(), command.author()).ifPresent(book -> {
            throw new BookAlreadyExistsException(command.title(), command.author());
        });

        Book book = Book.create(
                UUID.randomUUID(),
                command.title(),
                command.author(),
                command.description(),
                command.difficulty(),
                command.genre(),
                command.durationMinutes()
        );

        return bookRepository.save(book);

    }
}
