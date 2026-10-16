package com.adventure.book.domain.exception;

public class SectionNotFoundException extends DomainException {
    public SectionNotFoundException(String id) {
        super("RESOURCE_NOT_FOUND", "Section not found with id: " + id);
    }
}