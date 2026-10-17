package com.adventure.book.application.command;

import java.util.UUID;

public record DeleteSectionCommand(Integer sectionId, UUID bookId){
}
