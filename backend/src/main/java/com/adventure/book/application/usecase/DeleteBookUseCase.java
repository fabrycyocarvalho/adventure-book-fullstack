package com.adventure.book.application.usecase;

import com.adventure.book.domain.exception.BookNotFoundException;
import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteBookUseCase {

    private final BookRepository bookRepository;

    @Transactional
    public void execute(UUID bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId.toString()));
        bookRepository.delete(book);


    }
}
