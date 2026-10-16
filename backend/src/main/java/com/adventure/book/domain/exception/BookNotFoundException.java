package com.adventure.book.domain.exception;

public class BookNotFoundException extends DomainException {
    public BookNotFoundException(String id) {
        super("RESOURCE_NOT_FOUND", "Book not found with id: " + id);
    }
}