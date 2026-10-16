package com.adventure.book.application.command;

import com.adventure.book.domain.model.SectionType;

import java.util.UUID;

public record AddBookSectionCommand(UUID bookId, String text, SectionType type) { }
