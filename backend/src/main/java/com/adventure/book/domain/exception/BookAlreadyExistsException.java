package com.adventure.book.domain.exception;

public class BookAlreadyExistsException extends DomainException {
    public BookAlreadyExistsException(String title, String author) {
        super("BOOK_EXISTS", "A book with the title [" + title + "] and author [" + author + "] already exists.");
    }
}