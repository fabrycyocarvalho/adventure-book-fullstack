package com.adventure.book.application.usecase;

import com.adventure.book.application.command.AddBookSectionCommand;
import com.adventure.book.domain.exception.BookNotFoundException;
import com.adventure.book.domain.model.Book;
import com.adventure.book.domain.model.Section;
import com.adventure.book.domain.repository.BookRepository;
import com.adventure.book.domain.repository.SectionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddBookSectionUseCase {

    private final BookRepository bookRepository;
    private final SectionRepository sectionRepository;

    @Transactional
    public Section execute(AddBookSectionCommand command) {
        UUID bookId = command.bookId();
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId.toString()));

        Integer sectionId = sectionRepository.getNextSectionId(bookId);
        Section section = book.addSection(sectionId, command.text(), command.type());
        bookRepository.save(book);
        return section;
    }
}
