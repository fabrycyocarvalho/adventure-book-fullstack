package com.adventure.book.application.command;

import com.adventure.book.domain.model.Consequence;

import java.util.UUID;

public record AddSectionOptionCommand(UUID bookId,
                                      Integer sectionId,
                                      String description,
                                      Integer gotoId,
                                      String tip,
                                      Consequence consequence) {
}
